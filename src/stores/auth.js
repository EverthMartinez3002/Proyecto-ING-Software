import { defineStore } from 'pinia';

export const useAuthStore = defineStore({
    id: 'auth',
    state: () => ({
        token: null,
    }),
    actions: {
        setToken(token){
            this.token = token;
            localStorage.setItem('token', token);
        },
        clearToken(){
            this.token = null
            localStorage.removeItem('token');
        },
    },
});