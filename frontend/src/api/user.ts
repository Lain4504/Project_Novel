import axios from '../utils/axiosInstance';
import store from '../store';

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
    try{
        const response = await axios.post(`${USER_API}/registration`, data);
        return response.data.result;
    }
    catch(error){
        throw error;
    }
}

const login = async (data: AccountRequest) => {
    try{
        const response = await axios.post(`${IDENTITY_API}/token`, data);
        return response.data.result;
    }
    catch(error){
        throw error;
    }
}
const introspect = async (data : string) => {
    try{
        const response = await axios.post(`${IDENTITY_API}/introspect`, data);
        return response.data.result;
    }
    catch(error){
        throw error;
    }
}
const logout = async (data: LogoutRequest) => {
    try{
        const response = await axios.post(`http://localhost:8888/api/identity/auth/logout`, data,
            {
                headers: {
                    Authorization: `Bearer ${store.getters.getToken}`
                }
            }
        );
        return response.data.result;
    }
    catch(error){
        throw error;
    }
}
const getMyInfo = async () => {
    try {
      const response = await axios.get(`${USER_API}/my-info`);
      return response.data.result;
    } catch (error) {
      console.error("Error fetching user info:", error);
      throw error;
    }
  };
  
const getUserProfile = async (id: string) => {
    const response = await axios.get(`${USER_API}/${id}`, {
    });
    return response.data.result;
    }
export {register, login, introspect, logout, getMyInfo, getUserProfile};