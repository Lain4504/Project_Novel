import axios from '../utils/axiosInstance';

const USER_API = '/identity/users';
const PROFILE_API = '/profile/users';
const USER_NOVEL_FOLLOW_API = '/profile/user-novel-follow';
const USER_NOVEL_REVIEW_API = '/profile/user-novel-review';
interface AccountRequest {
    email: string;
    password: string;
}
interface UserNovelFollowRequest {
    userId: string;
    novelId: string;
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

const createReview = (data: any) => {
    return axios.post(`${USER_NOVEL_REVIEW_API}/create`, data)
        .then(response => response.data.result);
}
const updateReview = (id: string, data: any) => {
    return axios.put(`${USER_NOVEL_REVIEW_API}/${id}`, data)
        .then(response => response.data.result);
}
const deleteReview = (id: string) => {
    return axios.delete(`${USER_NOVEL_REVIEW_API}/${id}`)
        .then(response => response.data.result);
}
const getLatestReview = (id: string) => {
    return axios.get(`${USER_NOVEL_REVIEW_API}/get-latest`)
        .then(response => response.data.result);
}
const getReviewList = (novelId: string, page: number, size: number) => {
    return axios.get(`${USER_NOVEL_REVIEW_API}/novel/${novelId}?page=${page}&size=${size}`)
        .then(response => response.data.result);
}
const getMyFollowedNovels = (userId: string) => {
    return axios.get(`${USER_NOVEL_FOLLOW_API}/followed-novels/${userId}`)
        .then(response => response.data.result);
};
export {register, getMyInfo, getUserProfile, updateUserProfile, followNovel, unfollowNovel, isFollowingNovel, createReview, updateReview, deleteReview, getLatestReview, getReviewList,
    getMyFollowedNovels};
