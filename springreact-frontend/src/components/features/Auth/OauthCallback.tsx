import React, { useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../../../(context)/AuthContext';

const OAuthCallback = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const { login } = useAuth();

  useEffect(() => {
    const token = searchParams.get('token');
    const error = searchParams.get('error');

    if (token) {
      try {
        login(token);
        navigate('/dashboard');
      } catch (err) {
        console.error('Login error:', err);
        navigate('/login', { 
          state: { error: 'Authentication failed' }
        });
      }
    } else if (error) {
      console.error('OAuth error:', error);
      navigate('/login', { 
        state: { error }
      });
    } else {
      navigate('/login', { 
        state: { error: 'No token received' }
      });
    }
  }, [searchParams, login, navigate]);

  return (
    <div className="min-h-screen flex items-center justify-center">
      <div className="animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-indigo-500" />
    </div>
  );
};

export default OAuthCallback;
