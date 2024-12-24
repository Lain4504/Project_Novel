<script setup lang="ts">
import {inject, reactive, watch} from 'vue';
import Tiptap from "@/components/common/Tiptap.vue";
import { updateVolume } from '@/api/volume';
import {notification} from "ant-design-vue";
const showNotification = (type: string, message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3
  });
};
const props = defineProps({
  volumeData: {
    type: Object,
    required: true
  }
});
const emit = defineEmits(['volume-updated']);

const state = reactive({
  title: props.volumeData.volumeName || "",
  description: props.volumeData.description || "",
  image: props.volumeData.image || "",
  status: props.volumeData.status || "ongoing",
});

const handleSubmit = async () => {
  try {
    await updateVolume(props.volumeData.id, {
      volumeName: state.title,
      description: state.description,
      image: state.image,
      status: state.status
    });
    showNotification('success', 'Volume updated successfully.');
    emit('volume-updated');
  } catch (error: any) {
    console.error('Failed to update volume:', error);
    if (error.response) {
      showNotification('Error', error.response.data.message || 'Volume update failed. Please try again.');
    } else if (error.request) {
      showNotification('Error', 'No response from server. Please try again.');
    } else {
      showNotification('Error', 'An unexpected error occurred. Please try again.');
    }
  }
};

watch(() => props.volumeData, (newData) => {
  state.title = newData.volumeName || "";
  state.description = newData.description || "";
  state.image = newData.image || "";
  state.status = newData.status || "ongoing";
}, { immediate: true });
</script>

<template>
  <main class="flex-1 p-6 bg-[#f8f8f7] shadow-sm">
    <h1>Edit Novel Volume</h1>
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
      <button @click="handleSubmit"
              class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
        Submit
      </button>
    </div>
  </main>
</template>