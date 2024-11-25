import React, { useEffect, useState } from 'react';
import { useAuth } from '../../../(context)/AuthContext';


const Login = () => {
  const [backendStatus, setBackendStatus] = useState<string>('');
  
  const [error, setError] = useState<string>('');

  useEffect(() => {
    // Test connexion backend
    console.log('Testing backend connection...');
    fetch('http://localhost:8080/api/test')
      .then(response => {
        console.log('Response status:', response.status);
        return response.text();
      })
      .then(data => {
        console.log('Backend response:', data);
        setBackendStatus('Connected: ' + data);
      })
      .catch(err => {
        console.error('Backend connection error:', err);
        setError(err.message);
      });
  }, []);

  const handleGoogleLogin = () => {
    window.location.href = 'http://localhost:8080/api/oauth2/authorize/google';
  };


  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="max-w-md w-full space-y-8 p-8 bg-white rounded-lg shadow-lg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
            Sign in to your account
          </h2>
        </div>
        
        <button
          onClick={handleGoogleLogin}
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