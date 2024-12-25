<script lang="ts" setup>
import { inject, onMounted, ref } from 'vue';
import { getNovelCategoriesWithoutPagination } from "@/api/novelCategory";
import { createNovel } from "@/api/novel";
import Tiptap from "@/components/common/Tiptap.vue";
import Rules from "@/components/admin/Rules.vue";
import store from "@/store";
import {notification} from "ant-design-vue";

const showNotification = (type: string, message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3
  });
};

const selectedCategories = ref<string[]>([]);
const categories = ref<{ value: string; label: string }[]>([]);

const loadCategories = async () => {
  const data = await getNovelCategoriesWithoutPagination();
  categories.value = data.map((category: { id: string; name: string }) => ({
    value: category.id,
    label: category.name,
  }));
};

const form = ref({
  title: '',
  author: '',
  content: '',
});

const getCategoryIds = () => {
  return selectedCategories.value;
};

const selectedImage = ref<File | null>(null);
const imageUrl = ref('');

const saveNovel = async () => {
  const categoryIds = getCategoryIds();
  const novelData = {
    title: form.value.title,
    authorId: store.getters.getUserId,
    authorName: form.value.author,
    description: form.value.content,
    categories: categoryIds,
  };

  const formData = new FormData();
  formData.append("novel", new Blob([JSON.stringify(novelData)], { type: "application/json" }));
  if (selectedImage.value) {
    formData.append("image", selectedImage.value);
  }

  try {
    const novelResponse = await createNovel(formData);
    console.log("Novel saved successfully:", novelResponse);
    showNotification("success", "Novel saved successfully.");
  } catch (error: any) {
    console.error('Failed to save novel:', error);
    if (error.response) {
      showNotification('error', error.response.data.message || 'Novel save failed. Please try again.');
    } else if (error.request) {
      showNotification('error', 'No response from server. Please try again.');
    } else {
      showNotification('error', 'An unexpected error occurred. Please try again.');
    }
  }
};

const handleImageChange = (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    selectedImage.value = file;
    imageUrl.value = URL.createObjectURL(file);
  }
};
onMounted(() => {
  loadCategories();
});

const handleSearch = (value: string) => {
  if (!value) {
    console.log("Search input cleared.");
    selectedCategories.value = [];
  }
};
</script>

<template>
  <div class="flex flex-col lg:flex-row gap-4">
    <div class="bg-[#F8F8F7] border border-gray-300 rounded-lg p-4 shadow-md flex-1">
      <div class="font-semibold text-xl text-gray-800 mb-2">
        Thông báo
      </div>
      <div class="text-gray-700">
        <p><strong>Lưu ý:</strong> Để đăng truyện do bạn sáng tác, vui lòng đọc <a>Hướng dẫn tại đây</a>.</p>
      </div>
      <form class="my-5" @submit.prevent="saveNovel">
        <div class="md:col-span-1 my-4">
          <label class="block text-sm font-medium text-gray-700" for="floating_title">Tiêu đề tiểu thuyết</label>
          <input
              id="floating_title"
              v-model="form.title"
              class="bg-white mt-1 block w-full p-2 border rounded-md text-sm text-gray-900 bg-transparent border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
              type="text"
          />
        </div>

        <div class="md:col-span-1 my-4">
          <label class="block text-sm font-medium text-gray-700" for="floating_author">Tác giả</label>
          <input
              id="floating_author"
              v-model="form.author"
              class="bg-white mt-1 block w-full p-2 border rounded-md text-sm text-gray-900 bg-transparent border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
              type="text"
          />
        </div>

        <div class="space-y-4">
          <label class="block text-sm font-medium text-gray-800" for="file_input">Upload File</label>
          <div class="relative">
            <input
                id="file_input"
                class="bg-white block w-full text-sm text-gray-900 border rounded-lg cursor-pointer bg-gray-50 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:text-gray-400 dark:bg-gray-800 dark:border-gray-600 dark:focus:ring-blue-400 dark:focus:border-blue-400 file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:text-sm file:font-medium file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
                type="file"
                @change="handleImageChange"/>
          </div>
          <p class="text-sm text-gray-500">SVG, PNG, JPG, or GIF (MAX. 800x400px)</p>
          <div v-if="selectedImage" class="mt-4">
            <div
                class="relative w-48 h-60 overflow-hidden rounded-lg border border-gray-300 shadow-sm">
              <img :src="imageUrl" alt="Selected image" class="object-cover w-full h-full"/>
            </div>
          </div>
        </div>

        <div class="md:col-span-1 my-4 relative">
          <label class="block text-sm font-medium text-gray-700" for="floating_category">Chọn thể loại</label>
          <a-select
              v-model:value="selectedCategories"
              mode="multiple"
              allow-clear
              show-search
              placeholder="Chọn thể loại"
              class="w-full"
              :filter-option="(input, option) => {
    return option.label.toLowerCase().includes(input.toLowerCase());
  }"
              @search="handleSearch"
          >
            <a-select-option
                v-for="category in categories"
                :key="category.value"
                :value="category.value"
                :label="category.label"
            >
              {{ category.label }}
            </a-select-option>
          </a-select>
        </div>
        <div class="my-4">
          <label class="block text-sm font-medium text-gray-700" for="novel_description">Mô tả</label>
          <Tiptap :content="form.content" @update:content="form.content = $event"/>
        </div>

        <div class="flex justify-end my-4">
          <button
              class="text-sm bg-blue-500 text-white hover:bg-blue-700 font-medium py-2 px-4 rounded transition-all duration-300"
              type="submit">
            Lưu tiểu thuyết
          </button>
        </div>
      </form>
    </div>

    <div class="bg-[#F8F8F7] border border-gray-300 rounded-lg p-4 shadow-md flex-1">
      <div class="font-semibold text-xl text-gray-800 mb-2">
        QUY ĐỊNH ĐĂNG TRUYỆN
      </div>
      <div class="text-gray-700">
        <Rules/>
      </div>
    </div>
  </div>
</template>