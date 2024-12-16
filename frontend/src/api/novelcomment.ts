import axios from "@/utils/axiosInstance";
const NOVEL_COMMENT_API = "/comment/novel-comments";
const NOVEL_CHAPTER_API = "/comment/novel-chapter-comments";
interface NovelComment {
    id?: string;
    novelId: string;
    content: string;
    userId: string;
    replyCount?: number;
}
interface NovelCommentReply {
    id?: string;
    commentId: string;
    parentReplyId?: string;
    userId: string;
    replyTo: string;
    content: string;
}
interface NovelChapterComment{
    id?: string;
    chapterId: string;
    content: string;
    userId: string;
    replyCount?: number;
}
interface NovelChapterCommentReply{
    id?: string;
    commentId: string;
    parentReplyId?: string;
    userId: string;
    replyTo: string;
    content: string;
}
const getAllNovelComments = (novelId: string) => {
    return axios.get(`${NOVEL_COMMENT_API}/${novelId}`)
        .then((response) => response.data.result);
};
const createNovelComment = (data: NovelComment) => {
    return axios.post(NOVEL_COMMENT_API, data)
        .then((response) => response.data.result);
};
const updateNovelComment = (id: string, data: NovelComment) => {
    return axios.put(`${NOVEL_COMMENT_API}/${id}`, data)
        .then((response) => response.data.result);
};
const deleteNovelComment = (id: string) => {
    return axios.delete(`${NOVEL_COMMENT_API}/${id}`)
        .then((response) => response.data.result);
};
const getAllNovelRepliesByCommentId = (commentId: string) => {
    return axios.get(`${NOVEL_COMMENT_API}/replies/${commentId}`)
        .then((response) => response.data.result);
};
const createNovelReply = (data: NovelCommentReply) => {
    return axios.post(`${NOVEL_COMMENT_API}/replies`, data)
        .then((response) => response.data.result);
};
const updateNovelReply = (id: string, data: NovelCommentReply) => {
    return axios.put(`${NOVEL_COMMENT_API}/replies/${id}`, data)
        .then((response) => response.data.result);
};
const deleteNovelReply = (id: string) => {
    return axios.delete(`${NOVEL_COMMENT_API}/replies/${id}`)
        .then((response) => response.data.result);
};
const getAllChapterComments = (chapterId: string) => {
    return axios.get(`${NOVEL_CHAPTER_API}/${chapterId}`)
        .then((response) => response.data.result);
}
const createChapterComment = (data: NovelChapterComment) => {
    return axios.post(NOVEL_CHAPTER_API, data)
        .then((response) => response.data.result);
}
const updateChapterComment = (id: string, data: NovelChapterComment) => {
    return axios.put(`${NOVEL_CHAPTER_API}/${id}`, data)
        .then((response) => response.data.result);
}
const deleteChapterComment = (id: string) => {
    return axios.delete(`${NOVEL_CHAPTER_API}/${id}`)
        .then((response) => response.data.result);
}
const getAllRepliesByChapterCommentId = (commentId: string) => {
    return axios.get(`${NOVEL_CHAPTER_API}/replies/${commentId}`)
        .then((response) => response.data.result);
}
const createChapterReply = (data: NovelChapterCommentReply) => {
    return axios.post(`${NOVEL_CHAPTER_API}/replies`, data)
        .then((response) => response.data.result);
}
const updateChapterReply = (id: string, data: NovelChapterCommentReply) => {
    return axios.put(`${NOVEL_CHAPTER_API}/replies/${id}`, data)
        .then((response) => response.data.result);
}
const deleteChapterReply = (id: string) => {
    return axios.delete(`${NOVEL_CHAPTER_API}/replies/${id}`)
        .then((response) => response.data.result);
}
export { getAllNovelComments, createNovelComment, updateNovelComment, deleteNovelComment, getAllNovelRepliesByCommentId, createNovelReply, updateNovelReply, deleteNovelReply, getAllChapterComments, createChapterComment, updateChapterComment, deleteChapterComment, getAllRepliesByChapterCommentId, createChapterReply, updateChapterReply, deleteChapterReply };