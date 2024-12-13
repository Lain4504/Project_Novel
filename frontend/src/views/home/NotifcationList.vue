<script setup lang="ts">
import { ref } from 'vue';
import Banner from "@/components/home/Banner.vue";

const showDropdown = ref(false);
const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};
</script>

<template>
  <div class="max-w-7xl mx-auto p-4 min-h-screen">
    <Banner />
    <div class="flex justify-between items-center mb-4">
      <h1 class="text-2xl font-bold">Notifications</h1>
      <div class="relative">
        <button @click="toggleDropdown" class="bg-gray-200 p-2 rounded-md">
          Filter
        </button>
        <div v-if="showDropdown" class="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-md shadow-lg">
          <a href="#" class="block px-4 py-2 text-gray-800 hover:bg-gray-100">Read Notifications</a>
          <a href="#" class="block px-4 py-2 text-gray-800 hover:bg-gray-100">Unread Notifications</a>
        </div>
      </div>
    </div>
    <p class="text-gray-600 mb-6">Notifications will disappear after 60 days</p>
    <div class="space-y-4">
      <div class="flex items-center p-4 bg-white shadow-md rounded-md">
        <div class="flex-shrink-0 w-12 h-12 rounded-full overflow-hidden">
          <img src="@/assets/logo.jpg" alt="Logo" class="w-full h-full object-cover">
        </div>
        <div class="ml-4 flex-1">
          <div class="font-medium text-gray-800">ak-47fake</div>
          <div class="text-sm text-gray-500">commented on your story Urasekai Picnic</div>
        </div>
        <div class="text-sm text-gray-500">14 days ago</div>
      </div>
      <!-- Add more notification items here -->
    </div>
  </div>
</template>