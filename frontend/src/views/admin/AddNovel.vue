<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getNovelCategoriesWithoutPagination} from "@/api/novelCategory";
import {createNovel} from "@/api/novel";
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
  formData.append("novel", new Blob([JSON.stringify(novelData)], {type: "application/json"}));
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
          <label class="block text-sm font-medium text-gray-700" for="floating_title">Tiêu đề tiểu thuyết<span
              class="text-red-500">*</span></label>
          <a-input
              id="floating_title"
              v-model="form.title"
              class="w-full text-sm p-2"
              required
              type="text"
          />
        </div>

        <div class="md:col-span-1 my-4">
          <label class="block text-sm font-medium text-gray-700" for="floating_author">Tác giả<span
              class="text-red-500">*</span></label>
          <a-input
              id="floating_author"
              v-model="form.author"
              class="w-full text-sm p-2"
              required
              type="text"
          />
        </div>

        <div class="space-y-4">
          <label class="block text-sm font-medium text-gray-800" for="file_input">Upload File<span
              class="text-red-500">*</span></label>
          <div class="relative">
            <input
                id="file_input"
                class="bg-white block w-full text-sm text-gray-900 border rounded-lg cursor-pointer focus:ring-blue-500 focus:border-blue-500 dark:text-gray-400 dark:bg-gray-800 dark:border-gray-600 dark:focus:ring-blue-400 dark:focus:border-blue-400 file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:text-sm file:font-medium file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
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
          <label class="block text-sm font-medium text-gray-700" for="floating_category">Chọn thể loại<span
              class="text-red-500">*</span></label>
          <a-select
              v-model:value="selectedCategories"
              :filter-option="(input, option) => {
    return option.label.toLowerCase().includes(input.toLowerCase());
  }"
              allow-clear
              class="w-full"
              mode="multiple"
              placeholder="Chọn thể loại"
              show-search
              @search="handleSearch"
          >
            <a-select-option
                v-for="category in categories"
                :key="category.value"
                :label="category.label"
                :value="category.value"
            >
              {{ category.label }}
            </a-select-option>
          </a-select>
        </div>
        <div class="my-4">
          <label class="block text-sm font-medium text-gray-700" for="novel_description">Mô tả<span
              class="text-red-500">*</span></label>
          <Tiptap :content="form.content" @update:content="form.content = $event" width="100%" height="20rem"/>
        </div>

        <div class="flex justify-end my-4">
          <a-button
              type="primary"
              html-type="submit"
          >
            Lưu tiểu thuyết
          </a-button>
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