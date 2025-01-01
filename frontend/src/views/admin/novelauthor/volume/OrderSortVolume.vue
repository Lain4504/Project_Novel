<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import draggable from 'vue3-draggable-next';
import {notification} from "ant-design-vue";
import {getVolumesByNovelId, reorderVolume} from "../../../../api/novelVolume";

const showNotification = (type: 'success' | 'error', message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3,
  });
};

// Define props to receive volumeId
const props = defineProps<{
  novelId: string
}>();

// Initialize items with an empty array
const items = ref<Array<{ id: string, name: string }>>([]);
const emit = defineEmits(['volume-order-updated']);
// Fetch chapters when the component is mounted
onMounted(async () => {
  try {
    const volumes = await getVolumesByNovelId(props.novelId);
    items.value = volumes.map((volume: any) => ({
      id: volume.id,
      name: volume.volumeName,
      volumeNumber: volume.volumeNumber
    }));
  } catch (error) {
    console.error('Error fetching chapters:', error);
  }
});

// Function to handle saving the order
const saveOrder = async () => {
  try {
    // Call an API to save the order
    const list = items.value.map(item => item.id);
    await reorderVolume(props.novelId, list);
    emit('volume-order-updated');
    showNotification('success', 'Volume order updated successfully.');
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
</script>

<template>
  <a-card class="p-5 border border-gray-300 w-full bg-[#F8F8F7]">
    <a-typography-title level="3" class="mb-4" style="font-size: 25px">Sắp xếp</a-typography-title>
    <a-divider />
    <a-space direction="vertical" size="small" class="w-full">
      <draggable v-model="items" class="list" item-key="id">
        <template #item="{ element }">
          <a-card class="mb-2 rounded border text-sm bg-[#E7F5EE]" size="small">
            {{ element.name }}
          </a-card>
        </template>
      </draggable>
      <a-divider />
      <a-button type="primary" @click="saveOrder">
        Lưu thứ tự
      </a-button>
    </a-space>
  </a-card>
</template>