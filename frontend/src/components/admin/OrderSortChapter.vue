<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import draggable from 'vue3-draggable-next';
import {getChaptersByVolumeId, reorderChapter} from '@/api/novelChapter';
import {notification} from "ant-design-vue";

const showNotification = (type: string, message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3
  });
};
// Define props to receive volumeId
const props = defineProps<{
  volumeId: string
}>();

// Initialize items with an empty array
const items = ref<Array<{ id: string, name: string }>>([]);
const emit = defineEmits(['chapter-order-updated']);
// Fetch chapters when the component is mounted
onMounted(async () => {
  try {
    const chapters = await getChaptersByVolumeId(props.volumeId);
    items.value = chapters.map((chapter: any) => ({
      id: chapter.id,
      name: chapter.chapterTitle,
      chapterNumber: chapter.chapterNumber
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
    await reorderChapter(props.volumeId, list);
    emit('chapter-order-updated');
    showNotification('success', 'Chapter order updated successfully.');
  } catch (error: any) {
    console.error('Failed to update chapter:', error);
    if (error.response) {
      showNotification('error', error.response.data.message || 'Chapter update failed. Please try again.');
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
      <draggable v-model="items" class="list" item-key="id">
        <template #item="{ element }">
          <div class="bg-green-300 p-2 mb-2 rounded border text-sm">
            {{ element.name }}
          </div>
        </template>
      </draggable>
    </div>
    <div class="flex justify-between">
      <a-button type="primary" @click="saveOrder">
        Lưu thứ tự
      </a-button>
    </div>
  </div>
</template>