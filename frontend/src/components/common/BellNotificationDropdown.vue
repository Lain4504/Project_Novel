<script lang="ts" setup>
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
  }[];
}>();
</script>

<style scoped>
/* Styles cho NotificationList */
</style>
<template>
  <div
      v-if="notifications.length > 0"
      id="dropdownNotification"
      class="z-20 w-full max-w-sm bg-white divide-y divide-gray-100 rounded-lg shadow dark:bg-gray-800 dark:divide-gray-700"
  >
    <!-- Header -->
    <div
        class="block px-4 py-2 font-medium text-center text-gray-700 rounded-t-lg bg-[#F8F8F7] dark:bg-gray-800 dark:text-white">
      Notifications
    </div>
    <!-- Notification Items -->
    <div class="divide-y divide-gray-100 dark:divide-gray-700">
      <a
          v-for="notification in notifications"
          :key="notification.id"
          class="flex px-4 py-3 hover:bg-gray-100 dark:hover:bg-gray-700"
          href="#"
      >
        <!-- Notification Icon -->
        <div class="relative flex-shrink-0">
          <img
              alt="Profile image"
              class="rounded-full w-11 h-11"
              src="/src/assets/logo.jpg"
          />
          <div
              :class="`absolute flex items-center justify-center w-5 h-5 ms-6 -mt-5 ${notification.iconColor} border border-white rounded-full dark:border-gray-800`"
          >
            <svg
                aria-hidden="true"
                class="w-2 h-2 text-white"
                fill="currentColor"
                viewBox="0 0 18 18"
                xmlns="http://www.w3.org/2000/svg"
            >
              <path
                  d="M1 18h16a1 1 0 0 0 1-1v-6h-4.439a.99.99 0 0 0-.908.6 3.978 3.978 0 0 1-7.306 0 .99.99 0 0 0-.908-.6H0v6a1 1 0 0 0 1 1Z"/>
              <path
                  d="M4.439 9a2.99 2.99 0 0 1 2.742 1.8 1.977 1.977 0 0 0 3.638 0A2.99 2.99 0 0 1 13.561 9H17.8L15.977.783A1 1 0 0 0 15 0H3a1 1 0 0 0-.977.783L.2 9h4.239Z"/>
            </svg>
          </div>
        </div>

        <!-- Notification Content -->
        <div class="w-full ps-3">
          <div class="text-gray-500 text-sm mb-1.5 dark:text-gray-400">
            {{ notification.content }}
          </div>
          <div class="text-xs text-blue-600 dark:text-blue-500">
            {{ notification.createdDate }}
          </div>
        </div>
      </a>
    </div>
    <a class="block py-2 text-sm font-medium text-center text-gray-900 rounded-b-lg bg-[#F8F8F7] hover:bg-gray-100 dark:bg-gray-800 dark:hover:bg-gray-700 dark:text-white"
       href="#">
      <router-link class="inline-flex items-center " to="/notification-list">
        <svg aria-hidden="true" class="w-4 h-4 me-2 text-gray-500 dark:text-gray-400" fill="currentColor"
             viewBox="0 0 20 14" xmlns="http://www.w3.org/2000/svg">
          <path
              d="M10 0C4.612 0 0 5.336 0 7c0 1.742 3.546 7 10 7 6.454 0 10-5.258 10-7 0-1.664-4.612-7-10-7Zm0 10a3 3 0 1 1 0-6 3 3 0 0 1 0 6Z"/>
        </svg>
        View all
      </router-link>
    </a>
  </div>
</template>
