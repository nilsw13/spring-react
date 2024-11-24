import React from 'react';
import { useAuth } from '../(context)/AuthContext';
import { Navigate } from 'react-router-dom';
import { FcGoogle } from "react-icons/fc";


const Login = () => {
  const { isAuthenticated, loginWithGoogle } = useAuth();

  // Si déjà authentifié, rediriger vers le dashboard
  if (isAuthenticated) {
    return <Navigate to="/dashboard" replace />;
  }

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <h2 className="text-center text-3xl font-extrabold text-gray-900">
          Welcome to Your App
        </h2>
        <p className="mt-2 text-center text-sm text-gray-600">
          Sign in or Sign up with your Google account
        </p>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
          <button
            onClick={loginWithGoogle}
            className="w-full flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            <FcGoogle className="h-5 w-5 mr-2" />
            Continue with Google
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;