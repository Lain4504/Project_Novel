<template>
    <div class="max-w-screen-xl mx-auto p-6">
      <div class="flex flex-col lg:flex-row gap-6">
        <!-- Left: Latest Books Section -->
        <div class="p-6 flex-1">
          <div
            class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-2 gap-6 transition-transform duration-300"
            :style="{ transform: `translateX(-${currentSlide * 100}%)` }"
          >
            <div
              v-for="book in paginatedBooks[currentSlide]"
              :key="book.id"
              class="flex space-x-4 border p-4 rounded-lg shadow-sm hover:shadow-md transition-shadow duration-200"
            >
              <img
                :src="book.image"
                alt="book cover"
                class="w-24 h-36 object-cover rounded-lg"
              />
              <div class="flex-1">
                <h3 class="text-lg font-semibold line-clamp-2">{{ book.title }}</h3>
                <p class="text-sm text-gray-500 line-clamp-3">{{ book.description }}</p>
                <div class="flex items-center text-sm text-gray-700 mt-2">
                  <i class="fas fa-user mr-2"></i>
                  {{ book.author }}dad
                </div>
                <span
                  class="inline-block bg-yellow-100 text-yellow-600 text-xs font-semibold mt-3 px-3 py-1 rounded-full"
                >
                  {{ book.genre }}dâd
                </span>
              </div>
            </div>
          </div>
  
          <!-- See More Button -->
          <div class="flex justify-center mt-4">
            <button
              v-if="currentSlide < paginatedBooks.length - 1"
              @click="nextPage"
              class="bg-yellow-600 text-white py-2 px-4 rounded-full hover:bg-yellow-700 transition"
            >
              Xem Thêm
            </button>
          </div>
        </div>
    
        <!-- Right: Trending Books Sidebar -->
        <div class="w-full lg:w-1/4 flex flex-col gap-6">
          <div class="bg-gray-50 p-4 rounded-lg shadow-lg">
            <h3 class="text-xl font-semibold">Theo Dõi Nhiều</h3>
            <div class="mt-4">
              <div v-for="book in trendingBooks" :key="book.id" class="flex items-center mb-4">
                <img :src="book.image" alt="book cover" class="w-16 h-24 object-cover rounded-md"/>
                <div class="ml-4">
                  <h4 class="text-sm font-semibold text-gray-800">{{ book.title }}</h4>
                  <p class="text-xs text-gray-500">{{ book.followers }} theo dõi</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
<script setup lang="ts">
import { ref } from 'vue';

// Mock Data for Books (Latest Books & Trending Books)
const latestBooks = ref([
  {
    id: 1,
    title: 'Mesugaki Tank Enters The...',
    description: 'Đó Orc thồ thiện...',
    image: 'https://via.placeholder.com/300x450',
  },
  {
    id: 2,
    title: 'Kimi wa Hontouni Boku no...',
    description: 'Có một mối quan hệ...',
    image: 'https://via.placeholder.com/300x450',
  },
  {
    id: 3,
    title: 'The Mellow and Mysterious...',
    description: 'Clo Heartfield là một...',
    image: 'https://via.placeholder.com/300x450',
  },
]);

const trendingBooks = ref([
  {
    id: 1,
    title: 'Ryoushin no Shakkin w...',
    followers: 12539,
    image: 'https://via.placeholder.com/100x150',
  },
  {
    id: 2,
    title: 'In no jitsury okusha ni...',
    followers: 12032,
    image: 'https://via.placeholder.com/100x150',
  },
  {
    id: 3,
    title: 'Kết hôn với dua con gái...',
    followers: 10827,
    image: 'https://via.placeholder.com/100x150',
  },
  {
    id: 4,
    title: 'Kết hôn với dua con gái...',
    followers: 10827,
    image: 'https://via.placeholder.com/100x150',
  },
]);

const currentSlide = ref(0);
const paginatedBooks = ref([
  latestBooks.value.slice(0,4), // Example for 2 books per page
  latestBooks.value.slice(2),    // Next page (if exists)
]);

// Method to move to the next page
const nextPage = () => {
  if (currentSlide.value < paginatedBooks.value.length - 1) {
    currentSlide.value++;
  }
};
</script>
  