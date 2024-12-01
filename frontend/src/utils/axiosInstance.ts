import axios from 'axios';
import router from '@/router';
import store from '@/store';

axios.defaults.baseURL = import.meta.env.VITE_API_URL
axios.defaults.timeout = 10000;
axios.interceptors.request.use((config) => {
    if (store.getters.getToken) {
        config.headers.Authorization = `Bearer ${store.getters.getToken}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});
axios.interceptors.response.use((response) => {
    return response;
}, (error) => {
    if (error.response.status === 401) {
        store.commit('clearUser');
        router.push('/login');
    }
    return Promise.reject(error);
});
export default axios;