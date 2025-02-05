import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../../../(context)/AuthContext';

const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { isAuthenticated, isLoading, user } = useAuth();

  // Loader while loading
  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900" />
      </div>
    );
  }

  // Check Auth and Tenant
  if (!isAuthenticated || !user?.tenantId) {
    console.warn('Access denied:', { 
      isAuthenticated, 
      hasTenant: !!user?.tenantId 
    });
    return <Navigate to="/login" replace />;
  }

  // If authenticated and has tenant access show children
  return (
    <div className="min-h-screen bg-gray-100">
      {children}
    </div>
  );
};

export default ProtectedRoute;