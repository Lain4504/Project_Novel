<script lang="ts" setup>
import {onMounted, reactive, ref} from 'vue';
import {updateNovel} from "@/api/novel";
import Tiptap from "@/components/common/Tiptap.vue";
import store from "@/store";
import {getNovelCategoriesWithoutPagination} from "@/api/novelCategory";
import {notification} from "ant-design-vue";

const showNotification = (type: 'success' | 'error', message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3
  });
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

const selectedCategories = ref<string[]>(state.categories.map((cat: { id: string }) => cat.id));
const categories = ref<{ value: string; label: string }[]>([]);

const emit = defineEmits(['novel-updated']);

const loadCategories = async () => {
  const data = await getNovelCategoriesWithoutPagination();
  categories.value = data.map((category: { id: string; name: string }) => ({
    value: category.id,
    label: category.name,
  }));
};

const getCategoryIds = () => {
  return selectedCategories.value;
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
  formData.append("novel", new Blob([JSON.stringify(novelData)], {type: "application/json"}));
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
      showNotification('error', error.response.data.message || 'Novel update failed. Please try again.');
    } else if (error.request) {
      showNotification('error', 'No response from server. Please try again.');
    } else {
      showNotification('error', 'An unexpected error occurred. Please try again.');
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
        <label class="block text-sm font-medium text-gray-700" for="title">Title<span
            class="text-red-500">*</span></label>
        <a-input id="title" v-model="state.title"
                 class="w-full text-sm p-2"
                 type="text"/>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="author">Author<span
            class="text-red-500">*</span></label>
        <a-input id="author" v-model="state.author"
                 class="w-full text-sm p-2"
                 type="text"/>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="status">Status<span
            class="text-red-500">*</span></label>
        <select id="status" v-model="state.status"
                class="block w-1/2 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
          <option value="COMPLETED">Completed</option>
          <option value="HIATUS">Hiatus</option>
          <option value="ON_GOING">On Going</option>
        </select>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700" for="description">Description<span
            class="text-red-500">*</span></label>
        <Tiptap :content="state.description" @update:content="state.description = $event" width="100%" height="20rem"/>
      </div>
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-800" for="file_input">Upload File<span
            class="text-red-500">*</span></label>
        <input id="file_input"
               class="bg-white block w-full text-sm text-gray-900 border rounded-lg cursor-pointer focus:ring-blue-500 focus:border-blue-500 dark:text-gray-400 dark:bg-gray-800 dark:border-gray-600 dark:focus:ring-blue-400 dark:focus:border-blue-400 file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:text-sm file:font-medium file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
               type="file"
               @change="handleImageChange"/>
        <p class="text-sm text-gray-500">SVG, PNG, JPG, or GIF (MAX. 800x400px)</p>
        <div v-if="state.imageUrl" class="mt-4">
          <img :src="state.imageUrl" alt="Selected image"
               class="object-cover w-48 h-60 rounded-lg border border-gray-300 shadow-sm"/>
        </div>
      </div>
      <div class="mt-4 relative">
        <label class="block text-sm font-medium text-gray-700" for="floating_category">Chọn thể loại<span
            class="text-red-500">*</span></label>
        <a-select
            v-model:value="selectedCategories"
            :filter-option="(input: string, option: { label: string }) => {
              return option.label.toLowerCase().includes(input.toLowerCase());
            }"
            allow-clear
            class="w-full"
            mode="multiple"
            placeholder="Chọn thể loại"
            show-search
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
      <div class="flex justify-end mt-4">
        <a-button
            type="primary"
            html-type="submit"
        >
          Submit
        </a-button>
      </div>
    </form>
  </main>
</template>