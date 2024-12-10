import { createStore } from "vuex";

interface UserState {
   token: string | null;
   refreshToken: string | null;
   user: {
    id: string;
    email: string;
    role: string;
 } | null;
}

const store = createStore({
    state: {
        token: localStorage.getItem('token') || null,
        refreshToken: localStorage.getItem('refreshToken') || null,
        user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') as string) : null,
    } as UserState,
    mutations: {
        setUser(state, user: { id: string; email: string; role: string; }) {
            state.user = user;
            localStorage.setItem('user', JSON.stringify(user));  // Lưu user vào localStorage
        },
        setToken(state, token: string) {
            state.token = token;
            localStorage.setItem('token', token);  // Lưu token vào localStorage
        },
        setRefreshToken(state, refreshToken: string) {
            state.refreshToken = refreshToken;
            localStorage.setItem('refreshToken', refreshToken);  // Lưu token vào localStorage
        },
        clearUser(state) {
            state.user = null;
            state.token = null;
            localStorage.removeItem('user');  // Xóa user khỏi localStorage
            localStorage.removeItem('token');  // Xóa token khỏi localStorage
            localStorage.removeItem('refreshToken');  // Xóa token khỏi localStorage
        }
    },
    actions: {},
    getters: {
        isAuthenticated(state): boolean {
            return !!state.token;
        },
        getUserRole(state): string | null {
            return state.user ? state.user.role : null;
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
