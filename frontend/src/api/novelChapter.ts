import axios from "@/utils/axiosInstance";

const CHAPTER_API = "/novel/novel-chapters";
const createChapter = (volumeId: string, data = {}) => {
    return axios.post(`${CHAPTER_API}/create/${volumeId}`, data)
        .then((response) => response.data.result);
}
const getChapter = (id: string) => {
    return axios.get(`${CHAPTER_API}/get/${id}`)
        .then((response) => response.data.result);
}
const updateChapter = (id: string, data: Record<any, any>) => {
    return axios.put(`${CHAPTER_API}/update/${id}`, data)
        .then((response) => response.data.result);
}
const deleteChapter = (id: string) => {
    return axios.delete(`${CHAPTER_API}/delete/${id}`)
        .then((response) => response.data.result);
}
const getChaptersByVolumeId = (volumeId: string, status?: string) => {
    return axios.get(`${CHAPTER_API}/get/${volumeId}/chapters`, {
        params: {status}
    }).then((response) => response.data.result);
}
const getPreviousChapter = (volumeId: string, chapterNumber: number) => {
    return axios.get(`${CHAPTER_API}/get/${volumeId}/${chapterNumber}/previous`)
        .then((response) => response.data.result);
}
const getNextChapter = (volumeId: string, chapterNumber: number) => {
    return axios.get(`${CHAPTER_API}/get/${volumeId}/${chapterNumber}/next`)
        .then((response) => response.data.result);
}
const reorderChapter = (volumeId: string, data: string[]) => {
    return axios.put(`${CHAPTER_API}/reoder-chapter/${volumeId}`, data)
        .then((response) => response.data.result);
}
const incrementChapterView = (chapterId: string) => {
    return axios.put(`${CHAPTER_API}/increment-visit-count/${chapterId}`)
        .then((response) => response.data.result);
}
export {
    createChapter,
    getChapter,
    updateChapter,
    deleteChapter,
    getChaptersByVolumeId,
    getPreviousChapter,
    getNextChapter,
    reorderChapter,
    incrementChapterView
};

