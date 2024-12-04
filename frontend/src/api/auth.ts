import axios from '@/utils/axiosInstance';

const IDENTITY_API = '/identity/auth';

interface LogoutRequest {
    refreshToken: string;
    accessToken: string;
}

interface AccountRequest {
    email: string;
    password: string;
}

const login = (data: AccountRequest) => {
    return axios.post(`${IDENTITY_API}/token`, data)
        .then(response => response.data.result);
}
const introspect = (data: string) => {
    return axios.post(`${IDENTITY_API}/introspect`, data)
        .then(response => response.data.result);

}
const logout = (data: LogoutRequest) => {
    return axios.post(`${IDENTITY_API}/logout`, data)
        .then(response => response.data.result);
}
const refreshToken = (data: string) => {
    return axios.post(`${IDENTITY_API}/refresh`, data)
        .then(response => response.data.result);

}
const activeAccount = (token: string) => {
    return axios.post(`${IDENTITY_API}/active-account`, token)
        .then(response => response.data.result);
};
const resetPassword = (data: string) => {
    return axios.post(`${IDENTITY_API}/reset-password`, data)
        .then(response => response.data.result);
}
const forgotPassword = (data: string) => {
    return axios.post(`${IDENTITY_API}/forgot-password`, data)
        .then(response => response.data.result);
}
const changePassword = async (data: string) => {
    return axios.post(`${IDENTITY_API}/change-password`, data)
        .then(response => response.data.result);
}
export {login, introspect, logout, refreshToken, activeAccount, resetPassword, forgotPassword, changePassword};