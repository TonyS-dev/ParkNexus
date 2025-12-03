import { errorHandler } from '../lib/errorHandler';

/**
 * Custom hook to access the global error handler
 * Replaces the need to import errorHandler in every component
 *
 * Usage:
 * const error = useErrorHandler();
 * const msg = error.humanize(err, 'login');
 */
export const useErrorHandler = () => {
  return errorHandler;
};

