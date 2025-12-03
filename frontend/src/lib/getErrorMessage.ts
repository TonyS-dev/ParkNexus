export function isRecord(v: unknown): v is Record<string, unknown> {
  return typeof v === 'object' && v !== null;
}

export function getErrorMessage(err: unknown, fallback = 'An unexpected error occurred'): string {
  if (!err) return fallback;
  if (typeof err === 'string') return err;
  if (isRecord(err)) {
    const e = err as Record<string, unknown>;

    // PRIORITY 1: Check Axios response structure FIRST (before checking e.message)
    // This is because axios wraps errors with generic messages like "Request failed with status code 400"
    const resp = e.response;
    if (isRecord(resp)) {
      const data = resp.data;

      if (typeof data === 'string' && data) return data;
      if (isRecord(data)) {
        // Backend typically returns: { message: "...", timestamp: "...", status: 400 }
        // Or Spring Problem Details: { title: "...", detail: "...", status: 400 }
        // Or validation errors: { type: "/errors/validation", errors: [{field, message, rejectedValue}], ... }

        // Check for validation errors array first (Spring Boot validation)
        // Structure: { errors: [{field: "startTime", message: "Dates in the past...", rejectedValue: "..."}] }
        if (Array.isArray(data.errors) && data.errors.length > 0) {
          const firstError = data.errors[0];
          if (isRecord(firstError)) {
            // Spring Boot validation error format
            if (typeof firstError.message === 'string' && firstError.message) {
              return firstError.message;
            }
            // Alternative format: defaultMessage
            if (typeof firstError.defaultMessage === 'string' && firstError.defaultMessage) {
              return firstError.defaultMessage;
            }
          }
          if (typeof firstError === 'string') {
            return firstError;
          }
        }

        // Check standard fields
        if (typeof data.message === 'string' && data.message) return data.message;
        if (typeof data.detail === 'string' && data.detail) return data.detail;
        if (typeof data.title === 'string' && data.title) return data.title;
        if (typeof data.error === 'string' && data.error) return data.error;
      }
    }

    // PRIORITY 2: Check for message field (only if response extraction failed)
    if (typeof e.message === 'string' && e.message) return e.message;

    // PRIORITY 3: Other common error fields
    if (typeof e.error === 'string' && e.error) return e.error;
    if (typeof e.detail === 'string' && e.detail) return e.detail;

    // Fallback to JSON string
    try {
      return JSON.stringify(e);
    } catch (_err) {
      // swallow
    }
  }
  return String(err) || fallback;
}

export function getErrorMeta(err: unknown): { timestamp?: string; path?: string } {
  if (!isRecord(err)) return {};
  const resp = (err as Record<string, unknown>).response;
  if (!isRecord(resp)) return {};
  const data = resp.data;
  if (!isRecord(data)) return {};
  const timestamp = typeof data.timestamp === 'string' ? data.timestamp : undefined;
  const path = typeof data.path === 'string' ? data.path : undefined;
  return { timestamp, path };
}

/**
 * Converts technical error messages into user-friendly messages with emojis
 * @param err - The error object
 * @param context - Optional context for better error messages (e.g., 'checkin', 'checkout', 'reservation')
 * @returns User-friendly error message
 */
export function humanizeErrorMessage(err: unknown, context?: string): string {
  const rawMessage = getErrorMessage(err);
  const lowerMessage = rawMessage.toLowerCase();

  // Map common error patterns to user-friendly messages
  const errorMappings: Array<[string, string]> = [
    // HTTP Status Codes
    ['status code 400', '⚠️ Invalid request. Please check your information and try again.'],
    ['status code 401', '🔐 Invalid credentials. The email or password you entered is incorrect. Please try again.'],
    ['status code 403', '🚫 You don\'t have permission to access this resource.'],
    ['status code 404', '❓ The resource you\'re looking for doesn\'t exist.'],
    ['status code 500', '🔧 Server error. Our team has been notified and we\'re working to fix it.'],
    ['status code 503', '🔧 Service unavailable. Please try again later.'],

    // Registration errors (most specific patterns first)
    ['user already exists with this email', '👤 Este email ya está registrado. Por favor usa un email diferente o inicia sesión.'],
    ['already exists with this email', '📧 Este email ya está registrado. Por favor usa un email diferente.'],
    ['user already exists', '👤 A user with this email already exists. Please use a different email or try logging in.'],
    ['email already exists', '📧 This email is already registered with an account. Please use a different email or log in.'],
    ['already exists', '👤 This email is already registered. Please use a different email or try logging in.'],
    ['email already registered', '📧 This email is already registered. Please use a different email.'],
    ['email already in use', '📧 This email is already in use. Please use a different email or log in.'],
    ['duplicate email', '📧 This email is already registered. Please use a different email.'],
    ['email taken', '📧 This email is already taken. Please use a different email.'],

    // Active session conflicts
    ['already have an active', '🚗 You already have an active parking session. Please check out from your current spot before starting a new session.'],
    ['active parking session', '🚗 You currently have an active parking session. Please check out first before checking in to a new spot.'],
    ['user already has an active session', '🚗 You already have an active parking session. Please complete your current session before starting a new one.'],
    ['already checked in', '🚗 You\'re already checked in to a parking spot. Please check out first.'],

    // Reservation conflicts
    ['dates in the past', '📅 Dates in the past are not available. Please select a future date and time.'],
    ['past are not available', '📅 Dates in the past are not available. Please select a future date and time.'],
    ['spot is reserved', '📅 This spot is currently reserved by another user. Please choose a different spot or time.'],
    ['already reserved', '📅 You already have a reservation for this time. Please cancel your existing reservation first.'],
    ['overlapping reservation', '📅 There is already a reservation for this spot at that time. Please choose a different time.'],
    ['reservation not found', '📅 We couldn\'t find your reservation. It may have been cancelled or expired.'],
    ['reservation expired', '⏰ Your reservation has expired. Please create a new reservation.'],
    ['cannot check in yet', '⏰ It\'s too early to check in. You can check in starting 1 hour before your reservation time.'],
    ['too early', '⏰ It\'s too early to check in. Please wait until closer to your reservation time.'],

    // Spot availability
    ['spot not available', '🚫 This parking spot is currently not available. Please choose another spot.'],
    ['spot is occupied', '🚫 This spot is currently occupied. Please choose an available spot.'],
    ['spot not found', '🚫 The parking spot you\'re looking for doesn\'t exist or has been removed.'],
    ['no available spots', '🚫 No parking spots are currently available. Please try again later.'],

    // Validation errors
    ['vehicle number', '🚙 Please enter a valid vehicle number (e.g., ABC-1234).'],
    ['invalid email', '📧 Please enter a valid email address.'],
    ['invalid credentials', '🔐 The email or password you entered is incorrect. Please try again.'],
    ['password', '🔑 Your password must be at least 6 characters long.'],
    ['required', '⚠️ Please fill in all required fields.'],
    ['missing', '⚠️ Some required information is missing. Please check and try again.'],

    // Payment errors
    ['payment failed', '💳 Payment processing failed. Please check your payment details and try again.'],
    ['insufficient funds', '💰 Payment failed due to insufficient funds. Please use a different payment method.'],
    ['payment timeout', '⏱️ Payment timed out. Please try again.'],

    // Session errors
    ['session not found', '❓ We couldn\'t find your parking session. It may have already ended.'],
    ['session expired', '⏰ Your session has expired. Please start a new parking session.'],
    ['already checked out', '✅ You have already checked out from this session.'],
    ['no active session', '❓ You don\'t have an active parking session.'],

    // Network/timeout errors
    ['network error', '🌐 Network connection issue. Please check your internet connection and try again.'],
    ['timeout', '⏱️ The request took too long. Please try again.'],
    ['request failed', '⚠️ Something went wrong. Please try again in a moment.'],
    ['cannot reach', '🌐 Unable to connect to the server. Please check your connection.'],

    // Authorization errors
    ['unauthorized', '🔒 Your session has expired. Please log in again.'],
    ['forbidden', '🚫 You don\'t have permission to perform this action.'],
    ['access denied', '🚫 Access denied. Please contact support if you believe this is an error.'],
    ['token expired', '🔒 Your session has expired. Please log in again.'],

    // Server errors
    ['server error', '🔧 Our servers are experiencing issues. Please try again in a few moments.'],
    ['service unavailable', '🔧 The service is temporarily unavailable. Please try again later.'],
    ['internal server', '🔧 Something went wrong on our end. We\'re working to fix it.'],
  ];

  // Check each pattern and return humanized message
  for (const [pattern, friendlyMessage] of errorMappings) {
    if (lowerMessage.includes(pattern)) {
      return friendlyMessage;
    }
  }

  // If no pattern matched, add context and clean up the message
  const cleanedMessage = rawMessage
    .replace(/^Error:\s*/i, '')
    .replace(/^AxiosError:\s*/i, '')
    .trim();

  // Add emoji based on context
  const contextEmoji = context === 'checkin' ? '🚗' :
                      context === 'checkout' ? '✅' :
                      context === 'reservation' ? '📅' :
                      context === 'payment' ? '💳' :
                      context === 'login' ? '🔐' : '⚠️';

  return `${contextEmoji} ${cleanedMessage || 'Something went wrong. Please try again or contact support if the issue persists.'}`;
}
