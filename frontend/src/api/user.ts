import axios from '../utils/axiosInstance';

const USER_API = '/identity/users';
const PROFILE_API = '/profile/users';
const USER_NOVEL_FOLLOW_API = '/profile/user-novel-follow';
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
interface UserNovelFollowRequest {
    userId: string;
    id: string;
}
const followNovel = (data: UserNovelFollowRequest) => {
    return axios.post(`${USER_NOVEL_FOLLOW_API}/follow`, data)
        .then(response => response.data.result);
}

const unfollowNovel = (data: UserNovelFollowRequest) => {
    return axios.post(`${USER_NOVEL_FOLLOW_API}/unfollow`, data)
        .then(response => response.data.result);
}

const isFollowingNovel = (data: UserNovelFollowRequest) => {
    return axios.post(`${USER_NOVEL_FOLLOW_API}/is-following`, data)
        .then(response => response.data.result);
}
export {register, getMyInfo, getUserProfile, updateUserProfile, followNovel, unfollowNovel, isFollowingNovel};
