<script setup lang="ts">
import {inject, ref} from 'vue';
import Tiptap from '@/components/common/Tiptap.vue';
import { createChapter } from '@/api/chapter';
const showAlert = inject('showAlert') as ((type: string, message: string) => void);
const showNotification = (type: string, message: string) => {
  if (showAlert) {
    showAlert(type, message); // Gọi hàm showAlert toàn cục
  } else {
    console.error('showAlert is not available in this context');
  }
};
const props = defineProps({
  volumeId: {
    type: String,
    required: true
  }
});

const title = ref("");
const chapterNumber = ref<number | null>(null);
const status = ref("incomplete");
const content = ref("");
const isPaid = ref(false);
const price = ref(0);

const emit = defineEmits(['chapter-added']);

const handleSubmit = async () => {
  try {
    const chapterTitle = `Chapter ${chapterNumber.value} - ${title.value}`;
    await createChapter(props.volumeId, {
      chapterNumber: chapterNumber.value,
      chapterTitle,
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
    status.value = "incomplete";
    content.value = ""; // Reset Tiptap content
    isPaid.value = false;
    price.value = 0;
  } catch (error: any) {
    console.error('Failed to create chapter:', error);
    if (error.response) {
      showNotification('danger', error.response.data.message || 'Chapter creation failed. Please try again.');
    } else if (error.request) {
      showNotification('danger', 'No response from server. Please try again.');
    } else {
      showNotification('danger', 'An unexpected error occurred. Please try again.');
    }
  }
};
</script>

<template>
  <main class="flex-1 p-6 bg-[#F8F8F7] shadow-md">
    <h1 class="my-2">Add Chapter</h1>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="title" class="block text-sm font-medium text-gray-700">Tiêu đề</label>
        <input type="text" id="title" v-model="title"
               class="block w-full px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
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
          <option value="completed">Đã hoàn thành</option>
          <option value="incomplete">Chưa hoàn thành</option>
        </select>
      </div>
      <div class="mt-4">
        <span class="block text-sm font-medium text-gray-700">Chọn loại</span>
        <div class="mt-2">
          <label class="inline-flex items-center">
            <input type="radio" v-model="isPaid" :value="true" class="form-radio text-blue-500" />
            <span class="ml-2">Trả phí</span>
          </label>
          <label class="inline-flex items-center ml-6">
            <input type="radio" v-model="isPaid" :value="false" class="form-radio text-blue-500" />
            <span class="ml-2">Không trả phí</span>
          </label>
        </div>
      </div>
      <div v-if="isPaid" class="mt-4 w-1/2">
        <label for="price" class="block text-sm font-medium text-gray-700">Giá tiền</label>
        <input type="number" id="price" v-model="price"
               class="block w-full px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
               placeholder="Nhập giá tiền" />
      </div>
      <div class="mt-4">
        <label for="content" class="block text-sm font-medium text-gray-700">Nội dung</label>
        <Tiptap :content="content" @update:content="content = $event" class="mt-1" />
      </div>
      <div class="flex justify-end mt-4">
        <button type="submit"
                class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
          Submit
        </button>
      </div>
    </form>
  </main>
</template>