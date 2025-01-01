<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import draggable from 'vue3-draggable-next';
import {getChaptersByVolumeId, reorderChapter} from '../../../../api/novelChapter';
import {notification} from "ant-design-vue";

const showNotification = (type: 'success' | 'error', message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3,
  });
};


const props = defineProps<{
  volumeId: string
}>();

const items = ref<Array<{ id: string, name: string }>>([]);
const emit = defineEmits(['chapter-order-updated']);

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

const saveOrder = async () => {
  try {
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