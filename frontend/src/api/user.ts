import axios from '../utils/axiosInstance';

const USER_API = '/identity/users';
const IDENTITY_API = '/identity/auth';
interface AccountRequest {
    email: string;
    password: string;
}
interface LogoutRequest {
    refreshToken: string;
    accessToken: string;
}
const register = async (data: AccountRequest) => {
        const response = await axios.post(`${USER_API}/registration`, data);
        return response.data.result;
}

const login = async (data: AccountRequest) => {
    const response = await axios.post(`${IDENTITY_API}/token`, data);
    return response.data.result;

}
const introspect = async (data : string) => {
        const response = await axios.post(`${IDENTITY_API}/introspect`, data);
        return response.data.result;

}
const logout = async (data: LogoutRequest) => {
        const response = await axios.post(`http://localhost:8888/api/identity/auth/logout`, data);
        return response.data.result;
}
const getMyInfo = async () => {
      const response = await axios.get(`${USER_API}/my-info`);
      return response.data.result;
  };
  
const getUserProfile = async (id: string) => {
    const response = await axios.get(`${USER_API}/${id}`);
    return response.data.result;
    }
export {register, login, introspect, logout, getMyInfo, getUserProfile};