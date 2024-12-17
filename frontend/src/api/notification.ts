import axios from "@/utils/axiosInstance";
const NOTIFICATION_API = "notification/notifications/";
const getNotificationByUserId = async (userId: string, page: number, size: number) => {
    try {
        const response = await axios.get(`${NOTIFICATION_API}${userId}?page=${page}&size=${size}`);
        return response.data.result;
    } catch (error) {
        throw error;
    }
}
export { getNotificationByUserId };
