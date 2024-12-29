<script lang="ts" setup>
import {reactive, watch} from 'vue';
import Tiptap from "@/components/common/Tiptap.vue";
import {updateVolume} from '@/api/novelVolume';
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
      showNotification('error', error.response.data.message || 'Volume update failed. Please try again.');
    } else if (error.request) {
      showNotification('error', 'No response from server. Please try again.');
    } else {
      showNotification('error', 'An unexpected error occurred. Please try again.');
    }
  }
};

watch(() => props.volumeData, (newData) => {
  state.title = newData.volumeName || "";
  state.description = newData.description || "";
  state.image = newData.image || "";
  state.status = newData.status || "ongoing";
}, {immediate: true});
</script>

<template>
  <a-layout class="flex-1 p-6 bg-white shadow-md">
    <a-typography-title level="3" class="my-2" :style="{ color: '#18A058', fontSize: '20px' }">Edit Novel Volume</a-typography-title>
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