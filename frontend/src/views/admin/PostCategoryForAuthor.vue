<script setup lang="ts">
import { ref, onMounted } from "vue";
import {getPostCategoriesWithoutPagination} from "@/api/postcategory";

const postCategories = ref<any[]>([]);

const fetchPostCategories = async () => {
  try {
    const response = await getPostCategoriesWithoutPagination();
    postCategories.value = response;
  } catch (error) {
    console.error('Error fetching novel categories:', error);
  }
}

onMounted(() => {
  fetchPostCategories();
});
</script>

<template>
  <div class="bg-gray-100 p-6">
    <div class="max-w-4xl mx-auto bg-white rounded-md shadow-md">
      <h1 class="text-xl font-bold text-gray-700 bg-gray-200 px-4 py-2 border-b">Genres</h1>
      <div class="divide-y divide-gray-300">
        <div v-for="(category, index) in postCategories" :key="category.id" :class="{'bg-blue-100': index % 2 === 0, 'bg-white': index % 2 !== 0}" class="px-4 py-3">
          <h2 class="font-bold text-gray-800">{{ category.name }}</h2>
          <p v-html="category.description" class="text-sm text-gray-600"></p>
        </div>
      </div>
    </div>
  </div>
</template>