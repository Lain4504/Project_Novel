<script lang="ts" setup>
import {ref, watch, onMounted} from 'vue';
import Tiptap from "@/components/common/Tiptap.vue";
import {updateChapter, getChapter} from '@/api/novelChapter';
import {notification} from "ant-design-vue";

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

const title = ref("");
const status = ref(ChapterStatusEnum.DRAFT);
const content = ref("");
const wordCount = ref(0);
const fetchChapterData = async () => {
  try {
    const data = await getChapter(props.chapterData.id);
    title.value = data.chapterTitle || "";
    status.value = data.status || ChapterStatusEnum.DRAFT;
    content.value = data.content || "";
    wordCount.value = countWords(data.content || "");
  } catch (error) {
    console.error('Failed to fetch chapter data:', error);
    showNotification('error', 'Failed to fetch chapter data. Please try again.');
  }
};
const countWords = (text: string) => {
  return text.trim().split(/\s+/).length;
};
const handleSubmit = async () => {
  try {
    await updateChapter(props.chapterData.id, {
      volumeId: props.chapterData.volumeId,
      chapterNumber: props.chapterData.chapterNumber,
      chapterTitle: title.value,
      status: status.value,
      content: content.value,
      wordCount: wordCount.value
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
watch(content, (newContent) => {
  wordCount.value = countWords(newContent);
});
onMounted(fetchChapterData);

watch(() => props.chapterData, (newData) => {
  fetchChapterData();
}, {immediate: true});
</script>

<template>
  <main class="flex-1 p-6 bg-[#F8F8F7] shadow-md">
    <h1 class="my-2">Edit Chapter</h1>
    <form @submit.prevent="handleSubmit">
      <div>
        <label class="block text-sm font-medium text-gray-700" for="title">Tiêu đề</label>
        <input id="title" v-model="title" class="block w-2/3 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
               type="text"/>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="status">Trạng thái</label>
        <select id="status" v-model="status"
                class="block w-1/2 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <option :value="ChapterStatusEnum.COMPLETED">Đã hoàn thành</option>
          <option :value="ChapterStatusEnum.DRAFT">Chưa hoàn thành</option>
        </select>
      </div>
      <div class="mt-4 relative">
        <label class="block text-sm font-medium text-gray-700" for="content">Nội dung</label>
        <Tiptap :content="content" class="mt-1" @update:content="content = $event"/>
        <p class="absolute top-0 right-0 mt-1 mr-2 text-sm text-gray-500">Số từ: {{ wordCount }}</p>
      </div>
      <div class="flex justify-end mt-4">
        <button class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300"
                type="submit">
          Submit
        </button>
      </div>
    </form>
  </main>
</template>