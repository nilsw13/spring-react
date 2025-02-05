import axios from 'axios';


// Create an axios instance with a custom config
const api = axios.create({
    baseURL: import.meta.env.BASE_URL  || 'http://localhost:8080/api', // change this in production
    headers: {
      'Content-Type': 'application/json'
    },
    withCredentials: true  // ! CORS cookies
  });
// Intercepteur pour les requêtes
api.interceptors.request.use(
    (config) => {

    console.log('Request:', {
        url: config.url,
        method: config.method,
        headers: config.headers
      });
        // Add JWT token to headers if available
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response interceptor
api.interceptors.response.use(
    (response) => response,
    async (error) => {
        
        // handle 401 errors (No auth)
        if (error.response?.status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        
        // handle 403 errors (No authorization)
        if (error.response?.status === 403) {
            // Redirect to forbidden page or show a message
            console.error('Access forbidden');
        }

        return Promise.reject(error);
    }
);

export default api;