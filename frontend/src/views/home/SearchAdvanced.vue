<script setup lang="ts">
import { ref } from 'vue';
import Banner from "@/components/home/Banner.vue";

const genres = ref([
  "Action",
  "Adapted to Anime",
  "Adapted to Drama",
  "Adapted to Manga",
  "Adult",
  "Adventure",
  "Age Gap",
  "Boys Love",
  "Character Growth",
  "Chinese Novel",
  "Comedy",
  "Cooking",
  "Different Social Stat",
  "Drama",
  "Ecchi",
  "English Novel",
  "Fantasy",
  "Female Protagonist",
  "Game",
  "Gender Bender",
  "Harem",
  "Historical",
  "Horror",
  "Incest",
  "Isekai",
  "Josei",
  "Korean Novel",
  "Magic",
  "Martial Arts",
  "Mature",
  "Mecha",
  "Military",
]);

const showAdvancedSearch = ref(false);
</script>

<template>
  <div class="p-6 min-h-screen max-w-7xl mx-auto">
    <Banner class="my-5" />
    <div class="bg-white p-6 rounded-md shadow-md">
      <h1 class="text-lg font-semibold mb-4 text-green-600">Tìm kiếm</h1>
      <div class="mb-4 relative">
        <input
          type="text"
          placeholder="Nhập từ khóa..."
          class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-500"
        />
        <button
          class="absolute right-2 top-2 bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-400"
        >
          Tìm kiếm
        </button>
      </div>
      <div v-if="showAdvancedSearch" class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium text-gray-700">Tác giả</label>
          <input
            type="text"
            placeholder="Có thể bỏ trống..."
            class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-500"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700">Họa sĩ</label>
          <input
            type="text"
            placeholder="Có thể bỏ trống..."
            class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-500"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700">Tình trạng</label>
          <select
            class="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-500"
          >
            <option value="all">Tất cả</option>
            <option value="ongoing">Đang tiến hành</option>
            <option value="completed">Hoàn thành</option>
          </select>
        </div>
      </div>
      <div v-if="showAdvancedSearch">
        <label class="block text-sm font-medium text-gray-700 mb-2">Thể loại</label>
        <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-2">
          <div v-for="genre in genres" :key="genre">
            <label class="inline-flex items-center">
              <input
                type="checkbox"
                class="form-checkbox h-4 w-4 text-green-600 border-gray-300 rounded focus:ring-0"
              />
              <span class="ml-2 text-sm text-gray-700">{{ genre }}</span>
            </label>
          </div>
        </div>
      </div>
      <div class="mt-6 flex justify-end">
        <button @click="showAdvancedSearch = !showAdvancedSearch" class="flex items-center text-gray-500 hover:text-gray-700">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-5 w-5"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M3 10h2M5 6h14M5 6h14M5 6v2M3 14h14M21 10h-2M5 10h14M5 10h14M5 10v2M5 14h14M21 14h-2"
            />
          </svg>
          <span class="ml-1">Tìm kiếm nâng cao</span>
        </button>
      </div>
    </div>
  </div>
</template>