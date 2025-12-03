import { useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';
import { Button } from '../components/ui/button';
import { Card, CardContent } from '../components/ui/card';
import { Home, ArrowLeft, Car } from 'lucide-react';

const NotFound = () => {
  const navigate = useNavigate();
  const { user } = useAuthStore();

  // Determine the home path based on user role
  const getHomePath = () => {
    if (!user) return '/login';
    return user.role === 'ADMIN' ? '/admin/dashboard' : '/user/dashboard';
  };

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center px-4">
      <Card className="max-w-md w-full">
        <CardContent className="pt-8 pb-8">
          <div className="text-center">
            {/* 404 Icon */}
            <div className="flex justify-center mb-6">
              <div className="relative">
                <Car className="h-24 w-24 text-teal-600 opacity-20" />
                <div className="absolute inset-0 flex items-center justify-center">
                  <span className="text-6xl font-bold text-teal-600">404</span>
                </div>
              </div>
            </div>

            {/* Title & Description */}
            <h1 className="text-3xl font-bold text-gray-900 mb-3">
              Page Not Found
            </h1>
            <p className="text-gray-600 mb-8">
              Oops! The page you're looking for doesn't exist or has been moved.
              {user && (
                <> Let's get you back to where you need to be.</>
              )}
            </p>

            {/* Action Buttons */}
            <div className="flex flex-col sm:flex-row gap-3 justify-center">
              <Button
                variant="outline"
                onClick={() => navigate(-1)}
                className="w-full sm:w-auto"
              >
                <ArrowLeft className="h-4 w-4 mr-2" />
                Go Back
              </Button>
              <Button
                onClick={() => navigate(getHomePath())}
                className="w-full sm:w-auto"
              >
                <Home className="h-4 w-4 mr-2" />
                {user ? 'Go to Dashboard' : 'Go to Login'}
              </Button>
            </div>

            {/* Help Text */}
            {user && (
              <div className="mt-8 pt-6 border-t border-gray-200">
                <p className="text-sm text-gray-500">
                  Need help? Here are some quick links:
                </p>
                <div className="flex flex-wrap justify-center gap-2 mt-3">
                  {user.role === 'USER' ? (
                    <>
                      <Button
                        variant="link"
                        size="sm"
                        onClick={() => navigate('/user/spots')}
                        className="text-teal-600"
                      >
                        Find Parking
                      </Button>
                      <span className="text-gray-300">•</span>
                      <Button
                        variant="link"
                        size="sm"
                        onClick={() => navigate('/user/reservations')}
                        className="text-teal-600"
                      >
                        My Reservations
                      </Button>
                      <span className="text-gray-300">•</span>
                      <Button
                        variant="link"
                        size="sm"
                        onClick={() => navigate('/user/parking-management')}
                        className="text-teal-600"
                      >
                        Active Sessions
                      </Button>
                    </>
                  ) : (
                    <>
                      <Button
                        variant="link"
                        size="sm"
                        onClick={() => navigate('/admin/buildings')}
                        className="text-teal-600"
                      >
                        Manage Buildings
                      </Button>
                      <span className="text-gray-300">•</span>
                      <Button
                        variant="link"
                        size="sm"
                        onClick={() => navigate('/admin/users')}
                        className="text-teal-600"
                      >
                        Manage Users
                      </Button>
                    </>
                  )}
                </div>
              </div>
            )}
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default NotFound;

