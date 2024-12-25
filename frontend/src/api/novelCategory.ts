import axios from '@/utils/axiosInstance';

const NOVEL_CATEGORY_API = "/novel/novel-categories";

const createNovelCategory = (data: Record<any, any>) => {
    return axios.post(`${NOVEL_CATEGORY_API}/create`, data)
        .then((response) => response.data.result);
}
const updateNovelCategory = (id: string, data: Record<string, any>) => {
    return axios.put(`${NOVEL_CATEGORY_API}/update/${id}`, data)
        .then((response) => response.data.result);
}
const deleteNovelCategory = (id: string) => {
    return axios.delete(`${NOVEL_CATEGORY_API}/delete/${id}`)
        .then((response) => response.data.result);
}
const getNovelCategory = (id: string) => {
    return axios.get(`${NOVEL_CATEGORY_API}/get/${id}`)
        .then((response) => response.data.result);
}
const getNovelCategoriesWithoutPagination = () => {
    return axios.get(`${NOVEL_CATEGORY_API}/get/all-without-pagination`)
        .then((response) => response.data.result);
}
const getNovelCategories = (page: number, size: number) => {
    return axios.get(`${NOVEL_CATEGORY_API}/get/all?page=${page}&size=${size}`)
        .then((response) => response.data.result);
}
export {
    createNovelCategory,
    updateNovelCategory,
    deleteNovelCategory,
    getNovelCategory,
    getNovelCategoriesWithoutPagination,
    getNovelCategories
};
