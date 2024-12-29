<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {getPostCategories} from "@/api/postCategory";

const postCategories = ref<any[]>([]);
const currentPage = ref(1);
const totalPages = ref(0);
const pageSize = 10;

const fetchPostCategories = async (page: number) => {
  try {
    const response = await getPostCategories(page, pageSize);
    postCategories.value = response.data;
    totalPages.value = response.totalPages;
    currentPage.value = response.currentPage;
  } catch (error) {
    console.error('Error fetching post categories:', error);
  }
}

onMounted(() => {
  fetchPostCategories(currentPage.value);
});

const goToPage = (page: number) => {
  if (page >= 1 && page <= totalPages.value) {
    fetchPostCategories(page);
  }
};
</script>
<template>
  <div class="p-6">
    <div class="max-w-4xl mx-auto bg-white rounded-md shadow-md">
      <h1 class="text-xl font-bold text-gray-700 bg-[#E7F5EE] px-4 py-2 border-b">Thể loại bài viết</h1>
      <div class="divide-y divide-gray-300">
        <div v-for="(category, index) in postCategories" :key="category.id"
             :class="{'bg-[#E7F5EE]': index % 2 === 0, 'bg-white': index % 2 !== 0}" class="px-4 py-3">
          <h2 class="font-bold text-[#18A058]">{{ category.name }}</h2>
          <p class="text-sm text-gray-600" v-html="category.description"></p>
        </div>
      </div>
    </div>
    <!-- Pagination -->
    <div class="flex justify-center mt-4">
      <a-pagination
          :current="currentPage"
          :pageSize="pageSize"
          :total="totalPages * pageSize"
          @change="goToPage"
      />
    </div>
  </div>
</template>

<style scoped>
/* Custom styles for active items */
.active-item:hover {
  background-color: #E7F5EE !important;
  color: #18A058 !important;
}
</style>