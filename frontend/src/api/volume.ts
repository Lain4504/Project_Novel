import axios from "@/utils/axiosInstance";
const VOLUME_API = "/novel/volumes";

const createVolume = (data: Record<string, any>) => {
    return axios.post(`${VOLUME_API}/create`, data)
        .then((response) => response.data.result);
}
const getVolumes = () => {
    return axios.get(`${VOLUME_API}/all`)
        .then((response) => response.data.result);
}
const getVolume = (id: string) => {
    return axios.get(`${VOLUME_API}/${id}`)
        .then((response) => response.data.result);
}
const updateVolume = (id: string, data = {}) => {
    return axios.put(`${VOLUME_API}/update/${id}`, data)
        .then((response) => response.data.result);
}
export { createVolume, getVolumes, getVolume, updateVolume };
