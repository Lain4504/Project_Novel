<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {getNovelCategories, getNovelCategoriesWithoutPagination} from "@/api/novelCategory";

const novelCategories = ref<any[]>([]);
const currentPage = ref(1);
const totalPages = ref(0);
const pageSize = 10;

const fetchNovelCategories = async (page: number) => {
  try {
    const response = await getNovelCategories(page, pageSize);
    novelCategories.value = response.data;
    totalPages.value = response.totalPages;
    currentPage.value = response.currentPage;
  } catch (error) {
    console.error('Error fetching novel categories:', error);
  }
}

onMounted(() => {
  fetchNovelCategories(currentPage.value);
});

const goToPage = (page: number) => {
  if (page >= 1 && page <= totalPages.value) {
    fetchNovelCategories(page);
  }
};
</script>
<template>
  <div class="p-6">
    <div class="max-w-4xl mx-auto bg-white rounded-md shadow-md">
      <h1 class="text-xl font-bold text-gray-700 bg-gray-200 px-4 py-2 border-b">Genres</h1>
      <div class="divide-y divide-gray-300">
        <div v-for="(category, index) in novelCategories" :key="category.id"
             :class="{'bg-blue-100': index % 2 === 0, 'bg-white': index % 2 !== 0}" class="px-4 py-3">
          <h2 class="font-bold text-gray-800">{{ category.name }}</h2>
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