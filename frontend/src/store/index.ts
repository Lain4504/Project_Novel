// frontend/src/store/index.ts
import {createStore} from "vuex";

interface UserState {
    token: string | null;
    refreshToken: string | null;
    user: {
        id: string;
        email: string;
        image: string;
        roles: { name: string; description: string | null; permissions: any[] }[];
    } | null;
    isDarkMode: boolean;
}

const store = createStore({
    state: {
        token: localStorage.getItem('token') || null,
        refreshToken: localStorage.getItem('refreshToken') || null,
        user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') as string) : null,
        isDarkMode: localStorage.getItem('isDarkMode') === "true",
    } as UserState,
    mutations: {
        setUser(state, user: { id: string; email: string; image: string; roles: { name: string; description: string | null; permissions: any[] }[] }) {
            state.user = user;
            localStorage.setItem('user', JSON.stringify(user));
        },
        setToken(state, token: string) {
            state.token = token;
            localStorage.setItem('token', token);
        },
        setRefreshToken(state, refreshToken: string) {
            state.refreshToken = refreshToken;
            localStorage.setItem('refreshToken', refreshToken);
        },
        clearUser(state) {
            state.user = null;
            state.token = null;
            state.refreshToken = null;
            localStorage.removeItem('user');
            localStorage.removeItem('token');
            localStorage.removeItem('refreshToken');
        },
        toggleDarkMode(state) {
            state.isDarkMode = !state.isDarkMode;
            localStorage.setItem('isDarkMode', state.isDarkMode.toString());
        },
        setDarkMode(state, isDark: boolean) {
            state.isDarkMode = isDark;
            localStorage.setItem('isDarkMode', isDark.toString());
        },
    },
    actions: {
        toggleDarkMode({ commit }) {
            commit("toggleDarkMode");
        },
        initializeDarkMode({ commit }) {
            const savedDarkMode = localStorage.getItem("isDarkMode") === "true";
            commit("setDarkMode", savedDarkMode);
        },
    },
    getters: {
        isAuthenticated(state): boolean {
            return !!state.token;
        },
        getUserRole(state): string | null {
            return state.user ? state.user.roles.map(role => role.name).join(', ') : null;
        },
        getToken(state): string | null {
            return state.token;
        },
        getRefreshToken(state): string | null {
            return state.refreshToken;
        },
        getUserId(state): string | null {
            return state.user ? state.user.id : null;
        },
        getEmail(state): string | null {
            return state.user ? state.user.email : null;
        },
        isDarkMode(state): boolean {
            return state.isDarkMode;
        },
        getUserImage(state): string | null {
            return state.user ? state.user.image : null;
        },
    }
});

export default store;