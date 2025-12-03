import { api } from './api';
};
  register: (username: string, password: string) => api.post('/auth/register', { username, password }),
  login: (username: string, password: string) => api.post('/auth/login', { username, password }),
export const authService = {


