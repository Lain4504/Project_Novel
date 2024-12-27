import {createStore} from "vuex";

interface UserState {
    token: string | null;
    refreshToken: string | null;
    user: {
        id: string;
        email: string;
        roles: { name: string; description: string | null; permissions: any[] }[];
    } | null;
}

const store = createStore({
    state: {
        token: localStorage.getItem('token') || null,
        refreshToken: localStorage.getItem('refreshToken') || null,
        user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') as string) : null,
    } as UserState,
    mutations: {
        setUser(state, user: { id: string; email: string; roles: { name: string; description: string | null; permissions: any[] }[] }) {
            state.user = user;
            localStorage.setItem('user', JSON.stringify(user));
        },
        setToken(state, token: string) {
            state.token = token;
            localStorage.setItem('token', token);  // Save token to localStorage
        },
        setRefreshToken(state, refreshToken: string) {
            state.refreshToken = refreshToken;
            localStorage.setItem('refreshToken', refreshToken);  // Save refreshToken to localStorage
        },
        clearUser(state) {
            state.user = null;
            state.token = null;
            localStorage.removeItem('user');  // Remove user from localStorage
            localStorage.removeItem('token');  // Remove token from localStorage
            localStorage.removeItem('refreshToken');  // Remove refreshToken from localStorage
        }
    },
    actions: {},
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
        }
    }
});

export default store;