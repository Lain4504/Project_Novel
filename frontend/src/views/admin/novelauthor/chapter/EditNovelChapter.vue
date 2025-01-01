<script lang="ts" setup>
import {onMounted, ref, watch} from 'vue';
import Tiptap from "../../../../components/admin/Tiptap.vue";
import {getChapter, updateChapter} from '../../../../api/novelChapter';
import {notification} from "ant-design-vue";

enum ChapterStatusEnum {
  DRAFT = 'DRAFT',
  COMPLETED = 'COMPLETED'
}

const showNotification = (type: 'success' | 'error', message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3,
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

watch(() => props.chapterData, () => {
  fetchChapterData();
}, {immediate: true});
</script>

<template>
  <a-layout class="flex-1 p-6 bg-white shadow-md">
    <a-typography-title level="3" class="my-2" :style="{ color: '#18A058', fontSize: '20px' }">Edit Chapter</a-typography-title>
    <a-form @submit.prevent="handleSubmit">
      <a-form-item label="Tiêu đề" required class="form-item">
        <a-input v-model:value="title" placeholder="Nhập tiêu đề"/>
      </a-form-item>
      <a-form-item label="Trạng thái" required class="form-item">
        <a-radio-group v-model:value="status">
          <a-radio :value="ChapterStatusEnum.COMPLETED">Đã hoàn thành</a-radio>
          <a-radio :value="ChapterStatusEnum.DRAFT">Chưa hoàn thành</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="Nội dung" required class="form-item">
        <Tiptap :content="content" @update:content="content = $event" width="100%" height="24rem"/>
        <a-typography-text class="absolute top-0 right-0 mt-1 mr-2 text-sm text-gray-500">Số từ: {{ wordCount }}</a-typography-text>
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