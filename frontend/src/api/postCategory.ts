import axios from "../utils/axiosInstance";

const POST_CATEGORY_API = "/post/post-categories";

const createPostCategory = (data = {}) => {
    return axios.post(`${POST_CATEGORY_API}/create`, data)
        .then((response) => response.data.result);
}
const getPostCategories = (page: number, size: number) => {
    return axios
        .get(`${POST_CATEGORY_API}/get/all`, {params: {page, size}})
        .then((response) => response.data.result);
};
const getPostCategoriesWithoutPagination = () => {
    return axios.get(`${POST_CATEGORY_API}/get/all-without-pagination`)
        .then((response) => response.data.result);
}
const getPostCategory = (id: string) => {
    return axios.get(`${POST_CATEGORY_API}/get/${id}`)
        .then((response) => response.data.result);
}
const deletePostCategory = (id: string) => {
    return axios.delete(`${POST_CATEGORY_API}/delete/${id}`)
        .then((response) => response.data.result);
}
const updatePostCategory = (id: string, data = {}) => {
    return axios.put(`${POST_CATEGORY_API}/update/${id}`, data)
        .then((response) => response.data.result);
}
export {
    createPostCategory,
    getPostCategories,
    getPostCategory,
    deletePostCategory,
    updatePostCategory,
    getPostCategoriesWithoutPagination
};