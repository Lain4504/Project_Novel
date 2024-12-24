<script setup lang="ts">
import { inject, ref, watch } from 'vue';
import Tiptap from "@/components/common/Tiptap.vue";
import { updateChapter } from '@/api/chapter';
import { notification } from "ant-design-vue";

enum ChapterStatusEnum {
  DRAFT = 'DRAFT',
  COMPLETED = 'COMPLETED'
}

const showNotification = (type: string, message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3
  });
};

const props = defineProps({
  chapterData: {
    type: Object,
    required: true
  }
});
const emit = defineEmits(['chapter-updated']);

const title = ref(props.chapterData.chapterTitle || "");
const status = ref(props.chapterData.status || ChapterStatusEnum.DRAFT);
const content = ref(props.chapterData.content || "");
const chapterNumber = ref<number | null>(props.chapterData.chapterNumber || null);

const handleSubmit = async () => {
  try {
    await updateChapter(props.chapterData.id, {
      volumeId: props.chapterData.volumeId,
      chapterNumber: chapterNumber.value,
      chapterTitle: title.value,
      status: status.value,
      content: content.value
    });
    emit('chapter-updated');
    showNotification('success', 'Chapter updated successfully.');
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

watch(() => props.chapterData, (newData) => {
  title.value = newData.chapterTitle || "";
  status.value = newData.status || ChapterStatusEnum.DRAFT;
  content.value = newData.content || "";
  chapterNumber.value = newData.chapterNumber || null;
}, { immediate: true });
</script>

<template>
  <main class="flex-1 p-6 bg-[#F8F8F7] shadow-md">
    <h1 class="my-2">Edit Chapter</h1>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="title" class="block text-sm font-medium text-gray-700">Tiêu đề</label>
        <input type="text" id="title" v-model="title"
               class="block w-2/3 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"/>
      </div>
      <div class="mt-4">
        <label for="chapterNumber" class="block text-sm font-medium text-gray-700">Số chương</label>
        <input type="number" id="chapterNumber" v-model="chapterNumber"
               class="block w-full px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
      </div>
      <div class="mt-4">
        <label for="status" class="block text-sm font-medium text-gray-700">Trạng thái</label>
        <select id="status" v-model="status"
                class="block w-1/2 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <option :value="ChapterStatusEnum.COMPLETED">Đã hoàn thành</option>
          <option :value="ChapterStatusEnum.DRAFT">Chưa hoàn thành</option>
        </select>
      </div>
      <div class="mt-4">
        <label for="content" class="block text-sm font-medium text-gray-700">Nội dung</label>
        <Tiptap :content="content" @update:content="content = $event" class="mt-1" />
      </div>
      <div class="flex justify-end mt-4">
        <button type="submit" class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
          Submit
        </button>
      </div>
    </form>
  </main>
</template>