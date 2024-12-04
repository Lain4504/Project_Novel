import axios from '../utils/axiosInstance';

const USER_API = '/identity/users';
const PROFILE_API = '/profile';
interface AccountRequest {
    email: string;
    password: string;
}

const register = (data: AccountRequest) => {
    return axios.post(`${USER_API}/registration`, data)
        .then(response => response.data.result);
}

const getMyInfo = () => {
    return axios.get(`${USER_API}/my-info`)
        .then(response => response.data.result);
};

const getUserProfile = (id: string) => {
    return axios.get(`${USER_API}/${id}`)
        .then(response => response.data.result);
};

export {register, getMyInfo, getUserProfile};
