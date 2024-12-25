<script lang="ts" setup>
import {ref} from 'vue';
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
const chapterNumber = ref<number | null>(null);
const status = ref(ChapterStatusEnum.DRAFT);
const content = ref("");
const isPaid = ref(false);
const price = ref(0);

const emit = defineEmits(['chapter-added']);

const handleSubmit = async () => {
  try {
    await createChapter(props.volumeId, {
      chapterNumber: chapterNumber.value,
      chapterTitle: title.value,
      status: status.value,
      content: content.value,
      isPaid: isPaid.value,
      price: isPaid.value ? price.value : 0
    });
    showNotification('success', 'Chapter created successfully.');
    emit('chapter-added');

    // Reset form fields
    title.value = "";
    chapterNumber.value = null;
    status.value = ChapterStatusEnum.DRAFT;
    content.value = ""; // Reset Tiptap content
    isPaid.value = false;
    price.value = 0;
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
</script>

<template>
  <main class="flex-1 p-6 bg-[#F8F8F7] shadow-md">
    <h1 class="my-2">Add Chapter</h1>
    <form @submit.prevent="handleSubmit">
      <div>
        <label class="block text-sm font-medium text-gray-700" for="title">Tiêu đề</label>
        <a-input id="title" v-model:value="title" class="w-full text-sm p-2" placeholder="Nhập tiêu đề"/>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="chapterNumber">Số chương</label>
        <a-input-number id="chapterNumber" v-model:value="chapterNumber" class="p-[0.15rem] w-1/2 text-sm"
                        placeholder="Nhập số chương"/>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="status">Trạng thái</label>
        <a-select id="status" v-model:value="status" class="block w-1/2">
          <a-select-option :value="ChapterStatusEnum.COMPLETED">Đã hoàn thành</a-select-option>
          <a-select-option :value="ChapterStatusEnum.DRAFT">Chưa hoàn thành</a-select-option>
        </a-select>
      </div>
      <div class="mt-4">
        <span class="block text-sm font-medium text-gray-700">Chọn loại</span>
        <a-radio-group v-model:value="isPaid" class="mt-2">
          <a-radio :value="true">Trả phí</a-radio>
          <a-radio :value="false">Không trả phí</a-radio>
        </a-radio-group>
      </div>
      <div v-if="isPaid" class="mt-4 w-1/2">
        <label class="block text-sm font-medium text-gray-700" for="price">Giá tiền</label>
        <a-input-number id="price" v-model:value="price" placeholder="Nhập giá tiền"/>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="content">Nội dung</label>
        <Tiptap :content="content" class="mt-1" @update:content="content = $event"/>
      </div>
      <div class="flex justify-end mt-4">
        <a-button html-type="submit" type="primary">
          Submit
        </a-button>
      </div>
    </form>
  </main>
</template>