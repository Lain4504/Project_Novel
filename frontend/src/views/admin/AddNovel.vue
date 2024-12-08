<script setup lang="ts">
import {ref, onMounted, inject} from 'vue';
import { getNovelCategoriesWithoutPagination } from "@/api/novelcategory";
import { createNovel } from "@/api/novel";
import Tiptap from "@/components/common/Tiptap.vue";
import Rules from "@/components/admin/Rules.vue";
import store from "@/store";
const showAlert = inject('showAlert') as ((type: string, message: string) => void);
const showNotification = (type: string, message: string) => {
  if (showAlert) {
    showAlert(type, message);
  } else {
    console.error('showAlert is not available in this context');
  }
};

const selectedCategories = ref<string[]>([]);
const categories = ref<{ value: string; label: string }[]>([]);
const isDropdownOpen = ref(false);

const loadCategories = async () => {
  const data = await getNovelCategoriesWithoutPagination();
  categories.value = data.map((category: { id: string; name: string }) => ({
    value: category.id,
    label: category.name,
  }));
};

const toggleCategory = (category: string) => {
  const selectedCategory = categories.value.find(cat => cat.value === category)?.label;
  const index = selectedCategories.value.indexOf(selectedCategory || '');
  if (index === -1) {
    selectedCategories.value.push(selectedCategory || '');
  } else {
    selectedCategories.value.splice(index, 1);
  }
};

const form = ref({
  title: '',
  author: '',
  content: '',
});

const getCategoryIds = () => {
  return selectedCategories.value.map(categoryName => {
    const category = categories.value.find(cat => cat.label === categoryName);
    return category?.value;
  });
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
    console.error('Failed to update novel:', error);
    if (error.response) {
      showNotification('danger', error.response.data.message || 'Novel update failed. Please try again.');
    } else if (error.request) {
      showNotification('danger', 'No response from server. Please try again.');
    } else {
      showNotification('danger', 'An unexpected error occurred. Please try again.');
    }
  }
};

const isSelected = (category: string) => {
  const selectedCategory = categories.value.find(cat => cat.value === category)?.label;
  return selectedCategories.value.includes(selectedCategory || '');
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
          <label for="floating_title" class="block text-sm font-medium text-gray-700">Tiêu đề tiểu thuyết</label>
          <input
              type="text"
              id="floating_title"
              v-model="form.title"
              class="bg-white mt-1 block w-full p-2 border rounded-md text-sm text-gray-900 bg-transparent border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
          />
        </div>

        <div class="md:col-span-1 my-4">
          <label for="floating_author" class="block text-sm font-medium text-gray-700">Tác giả</label>
          <input
              type="text"
              id="floating_author"
              v-model="form.author"
              class="bg-white mt-1 block w-full p-2 border rounded-md text-sm text-gray-900 bg-transparent border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
          />
        </div>

        <div class="space-y-4">
          <label for="file_input" class="block text-sm font-medium text-gray-800">Upload File</label>
          <div class="relative">
            <input
                type="file"
                id="file_input"
                @change="handleImageChange"
                class="bg-white block w-full text-sm text-gray-900 border rounded-lg cursor-pointer bg-gray-50 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:text-gray-400 dark:bg-gray-800 dark:border-gray-600 dark:focus:ring-blue-400 dark:focus:border-blue-400 file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:text-sm file:font-medium file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100" />
          </div>
          <p class="text-sm text-gray-500">SVG, PNG, JPG, or GIF (MAX. 800x400px)</p>
          <div v-if="selectedImage" class="mt-4">
            <div
                class="relative w-32 h-32 overflow-hidden rounded-lg border border-gray-300 shadow-sm">
              <img :src="imageUrl" alt="Selected image" class="object-cover w-full h-full" />
            </div>
          </div>
        </div>

        <div class="md:col-span-1 my-4 relative">
          <label for="floating_category" class="block text-sm font-medium text-gray-700">Chọn thể loại</label>
          <input
              type="text"
              readonly
              :value="selectedCategories.join(', ')"
              class="bg-white mt-1 block w-full p-2 border rounded-md text-sm text-gray-900 bg-transparent border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
              @click="isDropdownOpen = !isDropdownOpen"
          />
          <div
              v-if="isDropdownOpen"
              class="absolute z-10 bg-white border border-gray-300 rounded-md mt-1 shadow-md max-h-60 overflow-y-auto w-full">
            <div
                v-for="category in categories"
                :key="category.value"
                class="p-2 cursor-pointer hover:bg-gray-100 text-sm"
                @click="toggleCategory(category.value)">
              <input
                  type="checkbox"
                  :id="category.value"
                  :value="category.value"
                  :checked="isSelected(category.value)"
                  class="mr-2"
              />
              {{ category.label }}
            </div>
          </div>
        </div>

        <div class="my-4">
          <label for="novel_description" class="block text-sm font-medium text-gray-700">Mô tả</label>
          <Tiptap :content="form.content" @update:content="form.content = $event" />
        </div>

        <div class="flex justify-end my-4">
          <button
              type="submit"
              class="text-sm bg-blue-500 text-white hover:bg-blue-700 font-medium py-2 px-4 rounded transition-all duration-300">
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
        <Rules />
      </div>
    </div>
  </div>
</template>