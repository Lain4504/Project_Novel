<template>
  <a-layout class="max-w-7xl mx-auto min-h-screen bg-white">
    <Banner/>
    <a-layout-content class="p-4">
      <div class="flex justify-between items-center mb-4">
        <h1 class="text-2xl font-bold">Notifications</h1>
        <a-dropdown trigger="click">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1">Read Notifications</a-menu-item>
              <a-menu-item key="2">Unread Notifications</a-menu-item>
            </a-menu>
          </template>
          <a-button type="primary">Filter</a-button>
        </a-dropdown>
      </div>
      <p class="text-gray-600 mb-6">Notifications will disappear after 60 days</p>
      <div class="space-y-4">
        <div v-for="notification in notifications" :key="notification.id"
             class="flex items-center p-4 bg-white shadow-md rounded-md">
          <div class="flex-shrink-0 w-12 h-12 rounded-full overflow-hidden">
            <a-avatar alt="user avatar" class="w-full h-full object-cover" :src="notification.image"/>
          </div>
          <div class="ml-4 flex-1">
            <div class="font-medium text-gray-800">{{ notification.title }}</div>
            <div class="text-sm text-gray-500">{{ notification.content }}</div>
          </div>
          <div class="text-sm text-gray-500">{{ notification.created }}</div>
        </div>
      </div>
      <!-- Pagination -->
      <div class="flex justify-center mt-4">
        <Pagination
            :current="currentPage"
            :pageSize="pageSize"
            :total="totalPages * pageSize"
            @change="goToPage"
        />
      </div>
    </a-layout-content>
    <a-layout-footer class="text-center">
      ©2023 Created by Your Company
    </a-layout-footer>
  </a-layout>
</template>

<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {Pagination} from 'ant-design-vue';
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
  image: string
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