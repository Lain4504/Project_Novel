import { createStore } from "vuex";

interface UserState {
   token: string | null;
   user: {
    id: string;
    email: string;
    role: string;
 } | null;
}

const store = createStore({
    state: {
        token: localStorage.getItem('token') || null,
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
        clearUser(state) {
            state.user = null;
            state.token = null;
            localStorage.removeItem('user');  // Xóa user khỏi localStorage
            localStorage.removeItem('token');  // Xóa token khỏi localStorage
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
    }
});

export default store;
