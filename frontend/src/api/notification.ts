import axios from "@/utils/axiosInstance";
const NOTIFICATION_API = "notification/notifications/";
const getNotificationByUserId = async (userId: string) => {
    try {
        const response = await axios.get(`${NOTIFICATION_API}${userId}`);
        return response.data.result;
    } catch (error) {
        throw error;
    }
}
export { getNotificationByUserId };
