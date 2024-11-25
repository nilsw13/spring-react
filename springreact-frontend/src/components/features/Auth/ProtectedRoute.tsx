import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../../../(context)/AuthContext';

const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { isAuthenticated, isLoading, user } = useAuth();

  // Affichage du loader pendant la vérification
  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900" />
      </div>
    );
  }

  // Vérification de l'authentification ET du tenant
  if (!isAuthenticated || !user?.tenantId) {
    console.warn('Access denied:', { 
      isAuthenticated, 
      hasTenant: !!user?.tenantId 
    });
    return <Navigate to="/login" replace />;
  }

  // Si tout est OK, on affiche le contenu protégé
  return (
    <div className="min-h-screen bg-gray-100">
      {children}
    </div>
  );
};

export default ProtectedRoute;