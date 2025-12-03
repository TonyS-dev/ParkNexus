import { humanizeErrorMessage, getErrorMessage } from './getErrorMessage';

/**
 * Singleton Error Handler
 * Provides global access to error message utilities
 */
class ErrorHandler {
  private static instance: ErrorHandler;

  private constructor() {
    // Private constructor to prevent direct instantiation
  }

  /**
   * Get the singleton instance
   */
  static getInstance(): ErrorHandler {
    if (!ErrorHandler.instance) {
      ErrorHandler.instance = new ErrorHandler();
    }
    return ErrorHandler.instance;
  }

  /**
   * Get a humanized error message
   * @param err - The error object
   * @param context - Optional context (e.g., 'checkin', 'checkout', 'login', 'reservation')
   * @returns User-friendly error message with emoji
   */
  humanize(err: unknown, context?: string): string {
    return humanizeErrorMessage(err, context);
  }

  /**
   * Get the raw error message
   * @param err - The error object
   * @param fallback - Fallback message if error can't be extracted
   * @returns The error message
   */
  getRaw(err: unknown, fallback = 'An unexpected error occurred'): string {
    return getErrorMessage(err, fallback);
  }

  /**
   * Format an error with context and return humanized message
   * Shorthand for humanize
   */
  format(err: unknown, context?: string): string {
    return this.humanize(err, context);
  }
}

// Export singleton instance
export const errorHandler = ErrorHandler.getInstance();

// Also export the class for type checking if needed
export default ErrorHandler;

