<script lang="ts" setup>
import {reactive} from 'vue';
import {createVolume} from "../../../../api/novelVolume";
import Tiptap from "../../../../components/admin/Tiptap.vue";
import {notification} from "ant-design-vue";

const showNotification = (type: 'success' | 'error', message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3,
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
      showNotification('error', error.response.data.message || 'Volume creation failed. Please try again.');
    } else if (error.request) {
      showNotification('error', 'No response from server. Please try again.');
    } else {
      showNotification('error', 'An unexpected error occurred. Please try again.');
    }
  }
};
</script>

<template>
  <a-layout class="flex-1 p-6 bg-white shadow-md">
    <a-typography-title level="3" class="my-2" :style="{ color: '#18A058', fontSize: '20px' }">Add Novel Volume</a-typography-title>
    <a-form @submit.prevent="handleSubmit">
      <a-form-item label="Title" required class="form-item">
        <a-input v-model:value="state.title" placeholder="Enter title"/>
      </a-form-item>
      <a-form-item label="Status" required class="form-item">
        <a-select v-model:value="state.status" class="block w-1/2">
          <a-select-option value="ongoing">Ongoing</a-select-option>
          <a-select-option value="completed">Completed</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="Description" class="form-item">
        <Tiptap :content="state.description" @update:content="state.description = $event"/>
      </a-form-item>
      <a-form-item class="form-item submit-button">
        <a-button type="primary" html-type="submit">Submit</a-button>
      </a-form-item>
    </a-form>
  </a-layout>
</template>

<style scoped>
.form-item {
  display: flex;
  flex-direction: column;
}

.submit-button {
  align-items: flex-end;
}
</style>