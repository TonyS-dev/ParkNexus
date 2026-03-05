import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.2/index.js';

const BASE_URL = 'https://api.statio.tonys-dev.com/api';
const EMAIL = __ENV.EMAIL || 'john.doe@example.com';
const PASSWORD = __ENV.PASSWORD || 'password123';

export const options = {
    stages: [
        { duration: '10s', target: 50 },
        { duration: '30s', target: 50 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        // Global threshold (reference)
        'http_req_failed': ['rate<0.01'],

        // Thresholds per group — this is the actual diagnosis
        'http_req_duration{group:::1_Login_Phase}': ['p(95)<2000'], // BCrypt is slow by design
        'http_req_duration{group:::2_Database_Phase}': ['p(95)<500'],  // PostgreSQL should be fast
        'http_req_duration{group:::3_Authenticated_Only}': ['p(95)<500'],  // Only JWT, no BCrypt
    },
};

export default function () {
    let token = null;

    // ==========================================
    // GROUP 1: CPU PHASE (BCrypt + JWT)
    // ==========================================
    group('1_Login_Phase', function () {
        const loginRes = http.post(
            `${BASE_URL}/auth/login`,
            JSON.stringify({ email: EMAIL, password: PASSWORD }),
            { headers: { 'Content-Type': 'application/json' } }
        );

        check(loginRes, {
            'login status is 200': (r) => r.status === 200,
            'has jwt token': (r) => r.json('token') !== undefined,
        });

        if (loginRes.status === 200) {
            token = loginRes.json('token');
        }
    });

    // If login fails, abort this iteration
    if (!token) {
        sleep(1);
        return;
    }

    const authHeaders = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
    };

    // ==========================================
    // GROUP 2: I/O PHASE (PostgreSQL + Hibernate)
    // — uses the token just obtained from login
    // ==========================================
    group('2_Database_Phase', function () {
        const res = http.get(`${BASE_URL}/spots/available`, authHeaders);
        check(res, { 'spots status is 200': (r) => r.status === 200 });
    });

    // ==========================================
    // GROUP 3: JWT ONLY (no BCrypt)
    // — same endpoint, same token, to isolate pure DB latency
    // ==========================================
    group('3_Authenticated_Only', function () {
        const res = http.get(`${BASE_URL}/spots/available`, authHeaders);
        check(res, { 'status 200': (r) => r.status === 200 });
    });

    // Simulates user reading time
    sleep(1);
}

export function handleSummary(data) {
    return {
        stdout: textSummary(data, { indent: ' ', enableColors: true }),
        'summary.json': JSON.stringify(data, null, 2),
    };
}