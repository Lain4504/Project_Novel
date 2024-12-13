import axios from '../utils/axiosInstance';

const USER_API = '/identity/users';
const PROFILE_API = '/profile/users';
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

const getUserProfile = (userId: string) => {
    return axios.get(`${PROFILE_API}/${userId}`)
        .then(response => response.data.result);
};
const updateUserProfile = (id: string, data: any) => {
    return axios.put(`${PROFILE_API}/${id}`, data)
        .then(response => response.data.result);
}
export {register, getMyInfo, getUserProfile, updateUserProfile};
