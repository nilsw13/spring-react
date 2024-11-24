import React from 'react';
import { useAuth } from '../../../(context)/AuthContext';


const Login = () => {
  const { loginWithGoogle } = useAuth();

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="max-w-md w-full space-y-8 p-8 bg-white rounded-lg shadow-lg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
            Sign in to your account
          </h2>
        </div>
        
        <button
          onClick={loginWithGoogle}
          className="w-full flex justify-center items-center px-4 py-2 border border-transparent 
                     rounded-md shadow-sm text-base font-medium text-white bg-indigo-600 
                     hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 
                     focus:ring-indigo-500"
        >
          <svg className="w-5 h-5 mr-2" viewBox="0 0 24 24">
            {/* Google icon SVG */}
          </svg>
          Sign in with Google
        </button>
      </div>
    </div>
  );
};

export default Login;