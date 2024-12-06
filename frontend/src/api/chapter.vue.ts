import axios from "@/utils/axiosInstance";
const CHAPTER_API = "/novel/novel-chapters";
const createChapter = (data = {}) => {
    return axios.post(`${CHAPTER_API}/create`, data)
        .then((response) => response.data.result);
}
const getChapter = (id: string) => {
    return axios.get(`${CHAPTER_API}/${id}`)
        .then((response) => response.data.result);
}
const updateChapter = (id: string, data: Record<any, any>) =>{
    return axios.put(`${CHAPTER_API}/update/${id}`, data)
        .then((response) => response.data.result);
}
const deleteChapter = (id: string) => {
    return axios.delete(`${CHAPTER_API}/delete/${id}`)
        .then((response) => response.data.result);
}
export { createChapter, getChapter, updateChapter, deleteChapter };

