<script setup lang="ts">
import {inject, reactive, ref} from 'vue';
import {createVolume} from "@/api/volume";
import Tiptap from "@/components/common/Tiptap.vue";
const showAlert = inject('showAlert') as ((type: string, message: string) => void);
const showNotification = (type: string, message: string) => {
  if (showAlert) {
    showAlert(type, message); // Gọi hàm showAlert toàn cục
  } else {
    console.error('showAlert is not available in this context');
  }
};
const props = defineProps({
  novelId: {
    type: String,
    required: true
  }
});

const state = reactive({
  title : "",
  description: "",
  status: "ongoing"
});
const emit = defineEmits(['volume-added']);
const handleSubmit = async () => {
  try {
    await createVolume(props.novelId, {
      volumeName: state.title,
      description: state.description,
      status: state.status
    });
    emit('volume-added');
    showNotification('success', 'Volume created successfully.');
  } catch (error: any) {
    console.error('Failed to create volume:', error);
    if (error.response) {
      showNotification('danger', error.response.data.message || 'Volume creation failed. Please try again.');
    } else if (error.request) {
      showNotification('danger', 'No response from server. Please try again.');
    } else {
      showNotification('danger', 'An unexpected error occurred. Please try again.');
    }
  }
};
</script>
<template>
  <main class="flex-1 p-6 bg-[#f8f8f7] shadow-sm">
    <h1>Add Novel Volume</h1>
    <form @submit.prevent="handleSubmit">
      <!-- Title Input -->
      <div class="mt-4">
        <label for="title" class="block text-sm font-medium text-gray-700">Title</label>
        <input type="text" id="title" v-model="state.title"
               class="block w-2/3 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"/>
      </div>
      <div class="mt-4">
        <label for="status" class="block text-sm font-medium text-gray-700">Status</label>
        <select id="status" v-model="state.status"
                class="block w-1/2 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <option value="ongoing">Ongoing</option>
          <option value="completed">Completed</option>
        </select>
      </div>
      <div class="mt-4">
        <label for="description" class="block text-sm font-medium text-gray-700">Description</label>
        <Tiptap :content="state.description" @update:content="state.description = $event"/>
      </div>
      <div class="flex justify-end mt-4">
        <button
            type="submit"
            class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
          Submit
        </button>
      </div>
    </form>
  </main>
</template>