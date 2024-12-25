<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import Banner from "@/components/home/Banner.vue";
import store from "@/store";
import {getNotificationByUserId} from "@/api/notification";

interface Notification {
  id: string;
  title: string;
  content: string;
  isRead: boolean;
  NotificationType: string;
  userId: string;
  created: string;
}

const notifications = ref<Notification[]>([]);
const currentPage = ref(1);
const totalPages = ref(0);
const pageSize = 10;

const fetchNotifications = async (page: number) => {
  try {
    const userId = store.getters.getUserId;
    const response = await getNotificationByUserId(userId, page, pageSize);
    notifications.value = response.data;
    totalPages.value = response.totalPages;
    currentPage.value = response.currentPage;
  } catch (error) {
    console.error('Failed to fetch notifications:', error);
  }
}

onMounted(() => {
  fetchNotifications(currentPage.value);
});

const goToPage = (page: number) => {
  if (page >= 1 && page <= totalPages.value) {
    fetchNotifications(page);
  }
};

const showDropdown = ref(false);
const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};
</script>

<template>
  <div class="max-w-7xl mx-auto p-4 min-h-screen">
    <Banner/>
    <div class="flex justify-between items-center mb-4">
      <h1 class="text-2xl font-bold">Notifications</h1>
      <div class="relative">
        <button class="bg-gray-200 p-2 rounded-md" @click="toggleDropdown">
          Filter
        </button>
        <div v-if="showDropdown"
             class="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-md shadow-lg">
          <a class="block px-4 py-2 text-gray-800 hover:bg-gray-100" href="#">Read Notifications</a>
          <a class="block px-4 py-2 text-gray-800 hover:bg-gray-100" href="#">Unread Notifications</a>
        </div>
      </div>
    </div>
    <p class="text-gray-600 mb-6">Notifications will disappear after 60 days</p>
    <div class="space-y-4">
      <div v-for="notification in notifications" :key="notification.id"
           class="flex items-center p-4 bg-white shadow-md rounded-md">
        <div class="flex-shrink-0 w-12 h-12 rounded-full overflow-hidden">
          <img alt="Logo" class="w-full h-full object-cover" src="@/assets/logo.jpg">
        </div>
        <div class="ml-4 flex-1">
          <div class="font-medium text-gray-800">{{ notification.title }}</div>
          <div class="text-sm text-gray-500">{{ notification.content }}</div>
        </div>
        <div class="text-sm text-gray-500">{{ new Date(notification.createdDate).toLocaleDateString() }}</div>
      </div>
    </div>
    <!-- Pagination -->
    <div class="flex justify-center mt-4">
      <a-pagination
          :current="currentPage"
          :pageSize="pageSize"
          :total="totalPages * pageSize"
          @change="goToPage"
      />
    </div>
  </div>
</template>