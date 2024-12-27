<script lang="ts" setup>
import {ref, watch} from 'vue';
import Tiptap from '@/components/common/Tiptap.vue';
import {createChapter} from '@/api/novelChapter';
import {notification} from 'ant-design-vue';

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
  volumeId: {
    type: String,
    required: true
  }
});

const title = ref("");
const status = ref(ChapterStatusEnum.DRAFT);
const content = ref("");
const wordCount = ref(0);

const emit = defineEmits(['chapter-added']);

const handleSubmit = async () => {
  try {
    await createChapter(props.volumeId, {
      chapterTitle: title.value,
      status: status.value,
      content: content.value,
      wordCount: wordCount.value
    });
    showNotification('success', 'Chapter created successfully.');
    emit('chapter-added');
    // Reset form fields
    title.value = "";
    status.value = ChapterStatusEnum.DRAFT;
    content.value = ""; // Reset Tiptap content
  } catch (error: any) {
    console.error('Failed to create chapter:', error);
    if (error.response) {
      showNotification('error', error.response.data.message || 'Chapter creation failed. Please try again.');
    } else if (error.request) {
      showNotification('error', 'No response from server. Please try again.');
    } else {
      showNotification('error', 'An unexpected error occurred. Please try again.');
    }
  }
};

const countWords = (text: string) => {
  return text.trim().split(/\s+/).length;
};
watch(content, (newContent) => {
  wordCount.value = countWords(newContent);
});
</script>

<template>
  <main class="flex-1 p-6 bg-[#F8F8F7] shadow-md">
    <h1 class="my-2">Add Chapter</h1>
    <form @submit.prevent="handleSubmit">
      <div>
        <label class="block text-sm font-medium text-gray-700" for="title">Tiêu đề <span
            class="text-red-500">*</span></label>

        <a-input id="title" v-model:value="title" class="w-full text-sm p-2" placeholder="Nhập tiêu đề"/>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="status">Trạng thái <span
            class="text-red-500">*</span></label>
        <a-radio-group id="status" v-model:value="status" class="block w-1/2">
          <a-radio :value="ChapterStatusEnum.COMPLETED">Đã hoàn thành</a-radio>
          <a-radio :value="ChapterStatusEnum.DRAFT">Chưa hoàn thành</a-radio>
        </a-radio-group>
      </div>
      <div class="mt-4 relative">
        <label class="block text-sm font-medium text-gray-700" for="content">Nội dung <span
            class="text-red-500">*</span></label>
        <Tiptap :content="content" class="mt-1" @update:content="content = $event" width="100%" height="24rem"/>
        <p class="absolute top-0 right-0 mt-1 mr-2 text-sm text-gray-500">Số từ: {{ wordCount }}</p>
      </div>
      <div class="flex justify-end mt-4">
        <a-button html-type="submit" type="primary">
          Submit
        </a-button>
      </div>
    </form>
  </main>
</template>