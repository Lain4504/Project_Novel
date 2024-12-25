import axios from "@/utils/axiosInstance";

const POST_API = "/post/posts";

const createPost = (data: Record<string, any>) => {
    return axios.post(`${POST_API}/create`, data)
        .then((response) => response.data.result);
}
const getPosts = ({page, size, categoryId}: { page: number, size: number, categoryId?: string }) => {
    const params: any = {page, size};
    if (categoryId) {
        params.categoryId = categoryId;
    }
    return axios.get(`${POST_API}/get/all`, {
        params: params
    }).then((response) => response.data.result);
}
const getPost = (id: string) => {
    return axios.get(`${POST_API}/get/${id}`)
        .then((response) => response.data.result);
}
const deletePost = (id: string) => {
    return axios.delete(`${POST_API}/delete/${id}`)
        .then((response) => response.data.result);
}
const updatePost = (id: string, data = {}) => {
    return axios.put(`${POST_API}/update/${id}`, data)
        .then((response) => response.data.result);
}
const getMyPosts = (params: { page: number, size: number }) => {
    return axios.get(`${POST_API}/my-posts`, {
        params: {
            page: params.page,
            size: params.size
        }
    }).then((response) => response.data.result);
}
export {createPost, getPosts, getPost, deletePost, updatePost, getMyPosts};