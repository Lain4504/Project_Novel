import axios from "@/utils/axiosInstance";
const POST_API = "/posts/post";

const createPost = (data = {}) => {
  return axios.post(`POST_API/create`, data)
      .then((response) => response.data.result);
}
const getPosts = (params) => {
    return axios.get('POST_API/all', {
        params: {
            page: params.page,
            size: params.size
        }
    }).then((response) => response.data.result)
}
const getPost = (id) => {
    return axios.get(`POST_API/${id}`)
        .then((response) => response.data.result);
}
const deletePost = (id) => {
    return axios.delete(`POST_API/delete/${id}`)
        .then((response) => response.data.result);
}
const updatePost = (id, data = {}) => {
    return axios.put(`POST_API/update/${id}`, data)
        .then((response) => response.data.result);
}
const getMyPosts = (params) => {
    return axios.get(`POST_API/my-posts`, {
        params: {
            page: params.page,
            size: params.size
        }
    }).then((response) => response.data.result)
}
export { createPost, getPosts, getPost, deletePost, updatePost, getMyPosts };