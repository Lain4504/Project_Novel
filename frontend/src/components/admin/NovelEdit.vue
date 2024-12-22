<script setup lang="ts">
import { reactive, ref, onMounted, inject } from 'vue';
import { updateNovel } from "@/api/novel";
import Tiptap from "@/components/common/Tiptap.vue";
import store from "@/store";
import { getNovelCategoriesWithoutPagination } from "@/api/novelcategory";

const showAlert = inject('showAlert') as ((type: string, message: string) => void);
const showNotification = (type: string, message: string) => {
  if (showAlert) {
    showAlert(type, message);
  } else {
    console.error('showAlert is not available in this context');
  }
};

const props = defineProps({
  novel: {
    type: Object,
    required: true
  }
});

const state = reactive({
  id: props.novel.id || "",
  title: props.novel.title || "",
  author: props.novel.authorName || "",
  description: props.novel.description || "",
  status: props.novel.bookStatus || "ongoing",
  categories: props.novel.categories || [],
  imageUrl: props.novel.image.path || ""
});

const selectedCategories = ref<string[]>(state.categories.map((cat: { name: string }) => cat.name));
const categories = ref<{ value: string; label: string }[]>([]);
const isDropdownOpen = ref(false);

const emit = defineEmits(['novel-updated']);

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

const getCategoryIds = () => {
  return selectedCategories.value.map(categoryName => {
    const category = categories.value.find(cat => cat.label === categoryName);
    return category?.value;
  });
};

const handleSubmit = async () => {
  const categoryIds = getCategoryIds();
  const novelData = {
    id: state.id,
    title: state.title,
    authorId: store.getters.getUserId,
    authorName: state.author,
    description: state.description,
    categories: categoryIds,
    status: state.status,
    imageUrl: state.imageUrl // Include the old image URL
  };

  const formData = new FormData();
  formData.append("novel", new Blob([JSON.stringify(novelData)], { type: "application/json" }));
  if (selectedImage.value) {
    formData.append("image", selectedImage.value);
  }

  try {
    await updateNovel(state.id, formData);
    emit('novel-updated');
    showNotification('success', 'Novel updated successfully.');
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
const selectedImage = ref<File | null>(null);

const handleImageChange = (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    selectedImage.value = file;
    state.imageUrl = URL.createObjectURL(file);
  }
};

onMounted(() => {
  loadCategories();
});
</script>
<template>
  <main class="flex-1 p-6 bg-[#f8f8f7] shadow-sm">
    <h1>Edit Novel</h1>
    <form @submit.prevent="handleSubmit">
      <div class="mt-4">
        <label for="title" class="block text-sm font-medium text-gray-700">Title</label>
        <input type="text" id="title" v-model="state.title"
               class="block w-2/3 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"/>
      </div>
      <div class="mt-4">
        <label for="author" class="block text-sm font-medium text-gray-700">Author</label>
        <input type="text" id="author" v-model="state.author"
               class="block w-2/3 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"/>
      </div>
      <div class="mt-4">
        <label for="status" class="block text-sm font-medium text-gray-700">Status</label>
        <select id="status" v-model="state.status"
                class="block w-1/2 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <option value="COMPLETED">Completed</option>
          <option value="HIATUS">Hiatus</option>
          <option value="ON_GOING">On Going</option>
        </select>
      </div>
      <div class="mt-4">
        <label for="description" class="block text-sm font-medium text-gray-700">Description</label>
        <Tiptap :content="state.description" @update:content="state.description = $event"/>
      </div>
      <div class="mt-4">
        <label for="file_input" class="block text-sm font-medium text-gray-800">Upload File</label>
        <input type="file" id="file_input" @change="handleImageChange"
               class="bg-white block w-full text-sm text-gray-900 border rounded-lg cursor-pointer bg-gray-50 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:text-gray-400 dark:bg-gray-800 dark:border-gray-600 dark:focus:ring-blue-400 dark:focus:border-blue-400 file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:text-sm file:font-medium file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100" />
        <p class="text-sm text-gray-500">SVG, PNG, JPG, or GIF (MAX. 800x400px)</p>
        <div v-if="state.imageUrl" class="mt-4">
          <img :src="state.imageUrl" alt="Selected image" class="object-cover w-48 h-60 rounded-lg border border-gray-300 shadow-sm"/>
        </div>
      </div>
      <div class="mt-4 relative">
        <label for="floating_category" class="block text-sm font-medium text-gray-700">Chọn thể loại</label>
        <input type="text" readonly :value="selectedCategories.join(', ')"
               class="block w-full p-2 mt-1 text-sm text-gray-900 border rounded-md bg-transparent border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
               @click="isDropdownOpen = !isDropdownOpen"/>
        <div v-if="isDropdownOpen" class="absolute z-10 w-full mt-1 bg-white border border-gray-300 rounded-md shadow-md max-h-60 overflow-y-auto">
          <div v-for="category in categories" :key="category.value" class="p-2 text-sm cursor-pointer hover:bg-gray-100" @click="toggleCategory(category.value)">
            <input type="checkbox" :id="category.value" :value="category.value" :checked="selectedCategories.includes(category.label)" class="mr-2"/>
            {{ category.label }}
          </div>
        </div>
      </div>
      <div class="flex justify-end mt-4">
        <button type="submit" class="px-4 py-2 text-sm font-medium text-blue-500 transition-all duration-300 border-[1px] border-blue-500 rounded hover:border-blue-700 hover:scale-105">
          Submit
        </button>
      </div>
    </form>
  </main>
</template>