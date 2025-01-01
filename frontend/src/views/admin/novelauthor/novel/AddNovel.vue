<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getNovelCategoriesWithoutPagination} from "../../../../api/novelCategory";
import {createNovel} from "../../../../api/novel";
import Tiptap from "../../../../components/admin/Tiptap.vue";
import Rules from "../Rules.vue";
import store from "../../../../store";
import {notification} from "ant-design-vue";

const showNotification = (type: 'success' | 'error', message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3,
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
      <a-form @submit.prevent="saveNovel" layout="vertical">
        <a-form-item label="Title" required>
          <a-input v-model:value="form.title" placeholder="Enter title"/>
        </a-form-item>
        <a-form-item label="Author" required>
          <a-input v-model:value="form.author" placeholder="Enter author"/>
        </a-form-item>
        <a-form-item label="Upload File" required>
          <input type="file" @change="handleImageChange"
                 class="bg-white block w-full text-sm text-gray-900 border rounded-lg cursor-pointer focus:ring-[#18A058] focus:border-[#18A058] file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:text-sm file:font-medium file:bg-[#E7F5EE] file:text-[#18A058] hover:file:bg-[#E7F5EE]"/>
          <p class="text-sm text-gray-500">SVG, PNG, JPG, or GIF (MAX. 800x400px)</p>
          <div v-if="imageUrl" class="mt-4">
            <img :src="imageUrl" alt="Selected image"
                 class="object-cover w-48 h-60 rounded-lg border border-gray-300 shadow-sm"/>
          </div>
        </a-form-item>
        <a-form-item label="Categories" required>
          <a-select
              v-model:value="selectedCategories"
              :filter-option="(input : any, option :any ) => {
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
        </a-form-item>
        <a-form-item label="Description" required>
          <Tiptap :content="form.content" @update:content="form.content = $event" width="100%" height="20rem"/>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" html-type="submit">Submit</a-button>
        </a-form-item>
      </a-form>
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