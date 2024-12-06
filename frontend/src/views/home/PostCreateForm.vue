<script setup lang="ts">
import Ads from "@/components/home/Banner.vue";
import Breadcrumb from "@/components/home/Breadcrumb.vue";
import DynamicFormNew from "@/components/common/DynamicFormNew.vue"; // Import dynamic form component
import { reactive, ref, onMounted } from "vue";
import { createPost } from "@/api/post";
import { getPostCategoriesWithoutPagination } from "@/api/postcategory";
import store from "@/store";

// State for categories
const categories = ref<any[]>([]);

// Fetch categories
const fetchCategories = async () => {
  try {
    const result = await getPostCategoriesWithoutPagination();
    categories.value = result.map((category: any) => ({
      value: category.id,
      label: category.name,
    })); // Map lại format
  } catch (error) {
    console.error("Failed to fetch post categories:", error);
  }
};
import { watch } from "vue";

watch(categories, (newCategories) => {
  const categoryInput = inputs.value.find((input) => input.id === "categoryId");
  if (categoryInput) {
    categoryInput.options = newCategories;
  }});
onMounted(() => {
  fetchCategories();
});

// Handle form submission
const handleAddPost = async (formData: any) => {
  const postData = {
    ...formData,
    userId: store.getters.getUserId, // Lấy userId từ store
  };

  try {
    await createPost(postData);
    alert("Bài viết đã được tạo thành công!");
  } catch (error) {
    alert("Có lỗi xảy ra khi tạo bài viết");
  }
};

// Form inputs
const inputs = ref([
  {
    id: "title",
    label: "Tiêu đề",
    type: "text",
    placeholder: "Nhập tiêu đề bài viết",
  },
  {
    id: "content",
    label: "Nội dung",
    type: "tiptap", // Sử dụng Tiptap editor
  },
  {
    id: "categoryId",
    label: "Chọn chuyên mục",
    type: "select",
    options: categories.value, // Bind với categories
  },
]);
</script>

<template>
  <div class="max-w-7xl mx-auto p-8">
    <Ads class="my-4" />
    <Breadcrumb
        :breadcrumbs="[
        { label: 'Home', href: '/' },
        { label: 'Forum', href: '/forum' },
        { label: 'Post', href: '/post', isCurrent: true }
      ]"
    />
    <!-- Dynamic Form -->
    <DynamicFormNew
        :inputs="inputs"
        title="Tạo bài viết mới"
        :handle-add="handleAddPost"
        location="/forum"
    />
  </div>
</template>
