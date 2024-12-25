<script lang="ts" setup>
import {reactive} from 'vue';
import {createVolume} from "@/api/novelVolume";
import Tiptap from "@/components/common/Tiptap.vue";
import {notification} from "ant-design-vue";

const showNotification = (type: string, message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3
  });
};

const props = defineProps({
  novelId: {
    type: String,
    required: true
  }
});

const state = reactive({
  title: "",
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
      showNotification('Error', error.response.data.message || 'Volume creation failed. Please try again.');
    } else if (error.request) {
      showNotification('Error', 'No response from server. Please try again.');
    } else {
      showNotification('Error', 'An unexpected error occurred. Please try again.');
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
        <label class="block text-sm font-medium text-gray-700" for="title">Title</label>
        <a-input id="title" v-model:value="state.title" class="w-full text-sm p-[0.4rem]" placeholder="Enter title"/>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="status">Status</label>
        <a-select id="status" v-model:value="state.status" class="block w-1/2">
          <a-select-option value="ongoing">Ongoing</a-select-option>
          <a-select-option value="completed">Completed</a-select-option>
        </a-select>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="description">Description</label>
        <Tiptap :content="state.description" @update:content="state.description = $event"/>
      </div>
      <div class="flex justify-end mt-4">
        <a-button html-type="submit" type="primary">
          Submit
        </a-button>
      </div>
    </form>
  </main>
</template>