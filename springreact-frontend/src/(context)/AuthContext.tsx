import React, { createContext, useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../(api)/axios';

interface User {
  id: number;
  email: string;
  name: string;
  tenantId: string;
  role: string;
}

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (token: string) => Promise<void>;
  logout: () => void;
  loginWithGoogle: () => void;
  setUser?: (user: User) => void;
}

export const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    checkAuth();
  }, []);

  const checkAuth = async () => {
    try {
      const token = localStorage.getItem('token');
      if (token) {
        api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        const response = await api.get('/auth/me');
        setUser(response.data);
      }
    } catch (error) {
      localStorage.removeItem('token');
      delete api.defaults.headers.common['Authorization'];
    } finally {
      setIsLoading(false);
    }
  };

  const login = async (token: string) => {
    try {
      // Stocker le token
      localStorage.setItem('token', token);
      
      // Configurer axios
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      
      // Charger les infos utilisateur
      const response = await api.get('/auth/me');
      setUser(response.data);
      
      // Rediriger
      navigate('/dashboard');
    } catch (error) {
      console.error('Login error:', error);
      localStorage.removeItem('token');
      navigate('/login');
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    delete api.defaults.headers.common['Authorization'];
    setUser(null);
    navigate('/login');
  };

  const loginWithGoogle = () => {
    window.location.href = `${import.meta.env.VITE_APP_API_URL}/oauth2/authorize/google`;
  };

  const value = {
    user,
    isAuthenticated: !!user,
    isLoading,
    login,
    logout,
    loginWithGoogle,
  };

  return (
    <AuthContext.Provider value={{ user, isAuthenticated: !!user, login, logout, loginWithGoogle, isLoading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};