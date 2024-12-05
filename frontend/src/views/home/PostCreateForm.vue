<script setup lang="ts">
import Ads from '@/components/home/Banner.vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';
import Tiptap from "@/components/common/Tiptap.vue";
import {createPost} from "@/api/post";
import {reactive, ref, onMounted} from "vue";
import store from "@/store";
import {getPostCategories} from "@/api/postcategory";

const state = reactive({
  title: "",
  content: '<>',
  categoryId: "",
  userId: "",
});
const categories = ref<any[]>([]);  // To store categories

// Fetch categories
const fetchCategories = async () => {
  try {
    const result = await getPostCategories();
    categories.value = result;
  } catch (error) {
    console.error("Failed to fetch post categories:", error);
  }
};

// Call fetchCategories on component mounted
onMounted(() => {
  fetchCategories();
});

// Handle form submission
const handleSubmit = async () => {
  const data = {
    title: state.title,
    content: state.content,
    categoryId: state.categoryId,  // Use the category selected by the user
    userId: store.getters.getUserId,
  };
  try {
    await createPost(data);
    alert("Bài viết đã được tạo thành công");
  } catch (error) {
    alert("Có lỗi xảy ra khi tạo bài viết");
  }
};
</script>
<template>
  <div class="max-w-7xl mx-auto p-8">
    <Ads class="my-4"/>
    <Breadcrumb :breadcrumbs="[ { label: 'Home', href: '/' }, { label: 'Forum', href: '/forum' }, { label: 'Post', href: '/post', isCurrent: true } ]" />
    <form class="max-w-7xl mx-auto my-4" @submit.prevent="handleSubmit">
      <!-- Tiêu đề -->
      <div class="mb-6 ">
        <label for="title" class="block text-md font-semibold text-gray-800 mb-2">Tiêu đề *</label>
        <input v-model="state.title" type="text" id="title" class="w-full border border-gray-300 rounded-lg py-1 px-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm" placeholder="Nhập tiêu đề bài viết" />
      </div>

      <!-- Nội dung -->
      <div class="mb-6">
        <div class="mt-4">
          <label for="content" class="block text-md font-bold text-gray-700">Nội dung</label>
          <Tiptap v-model="state.content" class="mt-1"/>
        </div>
      </div>

      <!-- Chuyên mục -->
      <div class="mb-6 grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label for="category" class="block text-md font-semibold text-gray-800 mb-2">Chọn chuyên mục *</label>
          <select v-model="state.categoryId"
                  id="category" class="w-full border border-gray-300 rounded-lg py-1 px-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500">
            <!-- Lặp qua các chuyên mục từ categories -->
            <option v-for="category in categories" :key="category.id" :value="category.id">
              {{ category.name }}
            </option>
          </select>
        </div>
      </div>

      <!-- Nút gửi -->
      <div class="text-right ">
        <button type="submit"
                class="cursor-pointer text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
          Submit
        </button>
      </div>
    </form>
  </div>
</template>

  

  
  