<script setup lang="ts">
import {ref, onMounted} from 'vue';
import draggable from 'vue3-draggable-next';
import {getChaptersByVolumeId, reorderChapter} from '@/api/novelChapter';
import {notification} from "ant-design-vue";
import {getVolumesByNovelId, reorderVolume} from "@/api/novelVolume";
const showNotification = (type: string, message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3
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
  try{
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
  <div class="p-5 border border-gray-300 w-full bg-[#F8F8F7]">
    <h3 class="text-xl font-semibold mb-4">Sắp xếp</h3>
    <div class="p-2 mb-4">
      <!-- draggable component to enable drag-and-drop -->
      <draggable v-model="items" item-key="id" class="list">
        <template #item="{ element }">
          <div class="bg-green-300 p-2 mb-2 rounded border text-sm">
            {{ element.name }}
          </div>
        </template>
      </draggable>
    </div>
    <div class="flex justify-between">
      <a-button @click="saveOrder" type="primary">
        Lưu thứ tự
      </a-button>
    </div>
  </div>
</template>