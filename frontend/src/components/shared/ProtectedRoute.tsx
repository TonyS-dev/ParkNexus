import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuthStore } from '../../store/authStore';
import { Role } from '../../types';

interface ProtectedRouteProps {
  children: React.ReactNode;
  allowedRoles: Role[];
}

const ProtectedRoute = ({ children, allowedRoles }: ProtectedRouteProps) => {
  const { isAuthenticated, user, validateAuth, logout } = useAuthStore();

  // Validate JWT on mount and periodically
  useEffect(() => {
    // Initial validation
    if (!validateAuth()) {
      return;
    }

    // Set up periodic validation (every 30 seconds)
    const interval = setInterval(() => {
      if (!validateAuth()) {
        console.warn('Session expired or invalid token detected');
        logout();
      }
    }, 30000); // 30 seconds

    return () => clearInterval(interval);
  }, [validateAuth, logout]);

  // Check authentication
  if (!isAuthenticated || !user) {
    return <Navigate to="/login" replace />;
  }

  // Check role authorization
  if (!allowedRoles.includes(user.role)) {
    // Redirect to appropriate dashboard based on role
    const redirectPath = user.role === Role.ADMIN ? '/admin/dashboard' : '/user/dashboard';
    return <Navigate to={redirectPath} replace />;
  }

  return <>{children}</>;
};

export default ProtectedRoute;
