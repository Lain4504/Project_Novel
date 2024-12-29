<script lang="ts" setup>
import { defineProps, computed } from 'vue';
import { EyeOutlined } from '@ant-design/icons-vue';

defineProps<{
  notifications: {
    id: number;
    user: string;
    message: string;
    time: string;
    iconColor: string;
    createdDate: string;
    title: string;
    content: string;
    image: string;
  }[];
}>();

const truncateContent = (content: string) => {
  return content.length > 70 ? content.slice(0, 70) + '...' : content;
};
</script>

<template>
  <a-dropdown placement="bottomRight">
    <div class="bg-white p-4 border border-gray-200 rounded-lg shadow-md">
      <a-menu>
        <!-- Header -->
        <a-menu-item key="header" disabled>
          <span class="font-medium text-lg text-gray-700 text-center block">Thông báo của bạn</span>
        </a-menu-item>
        <a-menu-divider class="border-gray-200"/>

        <!-- Notifications -->
        <a-menu-item v-for="notification in notifications" :key="notification.id" class="hover:bg-gray-100">
          <router-link to="/" class="flex items-center space-x-3 py-2">
            <a-avatar :src="notification.image"/>
            <div>
              <div class="text-gray-800 font-medium text-sm">{{ truncateContent(notification.content) }}</div>
              <div class="text-xs text-gray-500">{{ notification.createdDate }}</div>
            </div>
          </router-link>
        </a-menu-item>
        <a-menu-divider class="border-gray-200"/>
        <!-- Footer -->
        <a-menu-item key="footer">
          <router-link to="/notification-list"
                       class="flex items-center text-[#18A058] justify-center font-medium hover:underline">
            <a-button type="link" class="flex items-center">
              <EyeOutlined class="mr-2"/>
              Xem tất cả
            </a-button>
          </router-link>
        </a-menu-item>
      </a-menu>
    </div>
  </a-dropdown>
</template>

<style scoped>
</style>