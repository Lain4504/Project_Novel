import axios from "@/utils/axiosInstance";
const NOVEL_API = "/novel/novels";
const createNovel = (data = {}) => {
    return axios.post(`${NOVEL_API}/create`, data)
        .then((response) => response.data.result);
}
const updateNovel = (id: string, data: Record<string, any>) => {
    return axios.put(`${NOVEL_API}/update/${id}`, data)
        .then((response) => response.data.result);
};
const deleteNovel = (id: string) => {
    return axios.delete(`${NOVEL_API}/delete/${id}`)
        .then((response) => response.data.result);
}
const getNovel = (id: string) => {
    return axios.get(`${NOVEL_API}/${id}`)
        .then((response) => response.data.result);
}
const getNovels = (page: number, size: number) => {
    return axios.get(`${NOVEL_API}/all?page=${page}&size=${size}`)
        .then((response) => response.data.result);
}
const getMyNovels = (page: number, size: number) => {
    return axios.get(`${NOVEL_API}/my-novels?page=${page}&size=${size}`)
        .then((response) => response.data.result);
}
export { createNovel, updateNovel, deleteNovel, getNovel, getNovels, getMyNovels };