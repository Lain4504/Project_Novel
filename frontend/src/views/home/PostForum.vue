<script setup lang="ts">
import Ads from "@/components/home/Banner.vue";
import {ref, computed, onMounted, watch} from 'vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';
import {getPosts} from "@/api/post";
import {getPostCategoriesWithoutPagination} from "@/api/postcategory";
// Logic for post list
const currentPage = ref(1);
const totalPages = ref(0);
const data = ref<any[]>([]);
const fetchPosts = async (page: number, categoryId: string) => {
  try {
    // Kiểm tra nếu không có categoryId thì không truyền nó
    const result = await getPosts({page, size: 10, categoryId: categoryId || undefined});
    data.value = result.data;
    totalPages.value = result.totalPages;
    currentPage.value = result.currentPage;
  }
  catch (error) {
    console.error("Failed to fetch posts:", error);
  }
};
const selectedCategory = ref<string>('');
onMounted(() => {
  fetchPosts(currentPage.value, selectedCategory.value);
});
watch(selectedCategory, (newCategoryId) => {
  currentPage.value = 1;
  fetchPosts(currentPage.value, newCategoryId);
});
const goToPage = (page: number) => {
  if (page >= 1 && page <= totalPages.value) {
    fetchPosts(page, selectedCategory.value);
  }
};
const visiblePages = computed(() => {
  const pages = [];
  const maxVisible = 5;
  let start = Math.max(1, currentPage.value - Math.floor(maxVisible / 2));
  let end = Math.min(totalPages.value, start + maxVisible - 1);

  if (end - start + 1 < maxVisible) {
    start = Math.max(1, end - maxVisible + 1);
  }

  for (let i = start; i <= end; i++) {
    pages.push(i);
  }
  return pages;
});
const categories = ref<any[]>([]);

// Hàm gọi API để lấy các chuyên mục
const fetchCategories = async () => {
  try {
    const result = await getPostCategoriesWithoutPagination();
    console.log(result);
    categories.value = result; // Gán kết quả vào categories
  } catch (error) {
    console.error("Failed to fetch post categories:", error);
  }
};
// Lấy danh sách chuyên mục khi component được mount
onMounted(() => {
  fetchCategories();
});
</script>
<template>
  <div class="max-w-7xl mx-auto">
    <Ads class="my-4"/>
    <Breadcrumb :breadcrumbs="[
            { label: 'Home', href: '/' },
            { label: 'Forum', href: '/post-forum', isCurrent: true },
        ]"/>
    <div class="p-4 min-h-screen">
      <!-- Header -->
      <div class="mb-6">
        <!-- Title and Add Button on the same line -->
        <div class="flex justify-between items-center mb-2">
          <h1 class="text-xl font-semibold text-gray-800">Thảo luận</h1>
          <router-link to="/post-create-form"
              class="flex items-center justify-center w-10 h-10 bg-[#C96442] text-white rounded-full hover:bg-[#BA5B38]">
            <font-awesome-icon :icon="['fas', 'plus']" class="text-lg"/>
          </router-link>
        </div>
        <!-- Dropdown -->
        <div class="flex flex-col md:flex-row justify-between items-center">
          <div class="w-full md:w-1/3 mb-4 md:mb-0">
            <select
                v-model="selectedCategory"
                class="block w-full px-3 py-2 text-gray-700 bg-white border border-gray-300 rounded shadow-sm focus:outline-none focus:ring-1 focus:ring-[#AB5235] focus:border-[#AB5235]">
              <option value="">Tất cả</option>
              <option v-for="category in categories" :key="category.id" :value="category.id">
                {{ category.name }}
              </option>
            </select>
          </div>
        </div>
      </div>
      <table class="min-w-full bg-white shadow rounded-lg overflow-hidden">
        <thead>
        <tr class="bg-[#F8F8F7] text-left text-sm font-semibold text-gray-600 uppercase">
          <th class="px-4 py-3">Chủ đề</th>
          <!-- Ẩn cột "Chuyên mục" trên màn hình nhỏ, hiển thị trên màn hình lớn -->
          <th class="px-4 py-3 hidden md:table-cell">Chuyên mục</th>
          <th class="px-4 py-3 hidden md:table-cell">Bình luận</th>
          <th class="px-4 py-3 hidden md:table-cell">Lượt xem</th>
          <th class="px-4 py-3">Gần nhất</th>
          <th class="px-4 py-3">Người đăng cuối</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in data" :key="index"
            class="border-t hover:bg-gray-100 transition text-sm">
          <td class="px-4 py-3 flex items-center">
            <router-link :to="{ name: 'postdetail', params: { id: item.id } }" class="text-black font-medium hover:underline">
              <i v-if="item.isImportant" class="fas fa-star text-yellow-400 mr-2"></i>
              {{ item.title }}
            </router-link>
          </td>
          <!-- Hiển thị "Chuyên mục" dưới "Chủ đề" khi màn hình nhỏ hơn md -->
          <td class="px-4 py-3 block md:hidden">
             <span :class="{
                  'text-green-500': item.categoryName === 'Thảo luận',
                    'text-red-500': item.categoryName === 'Thông báo'
               }" class="font-semibold">
               {{ item.categoryName }}
                </span>
          </td>
          <!-- Cột "Chuyên mục" hiển thị bình thường trên màn hình lớn -->
          <td class="px-4 py-3 text-gray-700 hidden md:table-cell">{{ item.categoryName }}</td>
          <td class="px-4 py-3 text-gray-700 hidden md:table-cell">{{ item.comments }}</td>
          <td class="px-4 py-3 text-gray-700 hidden md:table-cell">{{ item.views }}</td>
          <td class="px-4 py-3 text-gray-500">{{ item.created }}</td>
          <td class="px-4 py-3 flex items-center space-x-2">
<!--            <img :src="item.lastUser.avatar" alt="Avatar" class="w-8 h-8 rounded-full"/>-->
            <span class="text-gray-700">{{ item.userId }}</span>
          </td>
        </tr>
        </tbody>
      </table>
      <!-- Pagination -->
      <div class="flex justify-center mt-4">
        <button @click="goToPage(1)" :disabled="currentPage === 1" class="px-3 py-1 mx-1 text-sm text-gray-400 rounded hover:bg-[#BA5B38]">
          Đầu
        </button>
        <button v-for="page in visiblePages" :key="page" @click="goToPage(page)" class="px-3 py-1 mx-1 text-sm rounded hover:bg-[#BA5B38] hover:text-[#F8F8F7]" :class="{'bg-[#C96442] text-white': page === currentPage, 'text-[#BA5B38]': page !== currentPage}">
          {{ page }}
        </button>
        <button @click="goToPage(totalPages)" :disabled="currentPage === totalPages" class="px-3 py-1 mx-1 text-sm text-gray-400 rounded hover:bg-[#BA5B38]">
          Cuối
        </button>
      </div>
    </div>
  </div>
</template>
<style>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css');
</style>
