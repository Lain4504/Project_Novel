<template>
    <!-- Main Content -->
    <main class="flex-1 p-6 bg-gray-100 shadow-md">
      <h1 class="my-2">Edit Chapter</h1>
      <form @submit.prevent="handleSubmit">
        <!-- Title Input -->
        <div>
          <label for="title" class="block text-sm font-medium text-gray-700">Tiêu đề</label>
          <input type="text" id="title" v-model="title"
            class="block w-2/3 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
        </div>
  
        <!-- Status Selection -->
        <div class="mt-4">
          <label for="status" class="block text-sm font-medium text-gray-700">Trạng thái</label>
          <select id="status" v-model="status"
            class="block w-1/2 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
            <option value="completed">Đã hoàn thành</option>
            <option value="incomplete">Chưa hoàn thành</option>
          </select>
        </div>
        <div class="mt-4"> 
            <label for="content" class="block text-sm font-medium text-gray-700">Nội dung</label>
        <Tiptap v-model="content" class="mt-1" />
    </div>
    <div class="mt-4">
        <button
                class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
                View History
            </button>
        </div>
        <div class="flex justify-end mt-4">
            <button
                class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
                Submit
            </button>
        </div>
      </form>
    </main>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
import Tiptap from '../../components/Tiptap.vue';
  
  const title = ref("");
  const status = ref("incomplete");
  const content = ref("");

  // Handle form submission
  const handleSubmit = () => {
    console.log({ title: title.value, status: status.value, content: content.value });
  };
  </script>
  