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
  <a-layout class="flex-1 p-6 bg-white shadow-md">
    <a-typography-title level="3" class="my-2" :style="{ color: '#18A058', fontSize: '20px' }">Edit Novel</a-typography-title>
    <a-form @submit.prevent="handleSubmit">
      <a-form-item label="Title" required class="form-item">
        <a-input v-model:value="state.title" placeholder="Enter title"/>
      </a-form-item>
      <a-form-item label="Author" required class="form-item">
        <a-input v-model:value="state.author" placeholder="Enter author"/>
      </a-form-item>
      <a-form-item label="Status" required class="form-item">
        <a-select v-model:value="state.status" placeholder="Select status">
          <a-select-option value="COMPLETED">Completed</a-select-option>
          <a-select-option value="HIATUS">Hiatus</a-select-option>
          <a-select-option value="ON_GOING">On Going</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="Description" required class="form-item">
        <Tiptap :content="state.description" @update:content="state.description = $event" width="100%" height="20rem"/>
      </a-form-item>
      <a-form-item label="Upload File" required class="form-item">
        <input type="file" @change="handleImageChange" class="bg-white block w-full text-sm text-gray-900 border rounded-lg cursor-pointer focus:ring-[#18A058] focus:border-[#18A058] file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:text-sm file:font-medium file:bg-[#E7F5EE] file:text-[#18A058] hover:file:bg-[#E7F5EE]"/>
        <p class="text-sm text-gray-500">SVG, PNG, JPG, or GIF (MAX. 800x400px)</p>
        <div v-if="state.imageUrl" class="mt-4">
          <img :src="state.imageUrl" alt="Selected image" class="object-cover w-48 h-60 rounded-lg border border-gray-300 shadow-sm"/>
        </div>
      </a-form-item>
      <a-form-item label="Categories" required class="form-item">
        <a-select v-model:value="selectedCategories" mode="multiple" placeholder="Select categories" show-search>
          <a-select-option v-for="category in categories" :key="category.value" :value="category.value">{{ category.label }}</a-select-option>
        </a-select>
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