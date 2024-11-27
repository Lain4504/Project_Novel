import axios from '../utils/axiosInstance';

const USER_API = 'identity/users';
const IDENTITY_API = '/identity/auth';
interface AccountRequest {
    email: string;
    password: string;
}
interface LogoutRequest {
    refreshtoken: string;
    accessToken: string;
}
const register = async (data: AccountRequest) => {
    try{
        const response = await axios.post(`${USER_API}/registration`, data);
        return response.data;
    }
    catch(error){
        throw error;
    }
}

const login = async (data: AccountRequest) => {
    try{
        const response = await axios.post(`${IDENTITY_API}/token`, data);
        return response.data;
    }
    catch(error){
        throw error;
    }
}
const introspect = async (data : string) => {
    try{
        const response = await axios.post(`${IDENTITY_API}/introspect`, data);
        return response.data;
    }
    catch(error){
        throw error;
    }
}
const logout = async (data: LogoutRequest) => {
    try{
        const response = await axios.post(`${IDENTITY_API}/logout`, data);
        return response.data;
    }
    catch(error){
        throw error;
    }
}
const getMyInfo = async () => {
  const response = await axios.get(`/${USER_API}/my-info`, {
  });
  return response.data;
};
const getUserProfile = async (id: string) => {
    const response = await axios.get(`/${USER_API}/${id}`, {
    });
    return response.data;
    }
export {register, login, introspect, logout, getMyInfo, getUserProfile};