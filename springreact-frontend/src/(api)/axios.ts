import axios from 'axios';


// Création d'une instance axios avec une configuration de base
const api = axios.create({
    baseURL: import.meta.env.VITE_APP_API_URL || 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json'
    }
});

// Intercepteur pour les requêtes
api.interceptors.request.use(
    (config) => {
        // Ajout du token JWT dans le header si disponible
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

// Intercepteur pour les réponses
api.interceptors.response.use(
    (response) => response,
    async (error) => {
        // Gérer les erreurs 401 (non authentifié)
        if (error.response?.status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        
        // Gérer les erreurs 403 (non autorisé)
        if (error.response?.status === 403) {
            // Rediriger vers une page d'erreur ou afficher un message
            console.error('Access forbidden');
        }

        return Promise.reject(error);
    }
);

export default api;