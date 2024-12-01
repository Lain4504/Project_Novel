
<template>
  <div class="p-6">
    <!-- Tabs -->
    <ul class="hnt-tab flex justify-start space-x-4 text-start py-4">
      <li
        v-for="tab in tabs"
        :key="tab.value"
        class="item cursor-pointer transition-transform duration-300 ease-in-out"
        :class="[
          activeTab === tab.value
            ? 'text-blue-600 font-bold border-b-2 border-blue-600'
            : 'text-gray-600',
          'hover:scale-105',
        ]"
        @click="setActiveTab(tab.value)"
      >
        {{ tab.label }}
      </li>
    </ul>

    <!-- Hiển thị các sách -->
    <div
      class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 transition-transform duration-300"
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
            {{ book.author }}
          </div>
          <span
            class="inline-block bg-yellow-100 text-yellow-600 text-xs font-semibold mt-3 px-3 py-1 rounded-full"
          >
            {{ book.genre }}
          </span>
        </div>
      </div>
    </div>

    <!-- Nút dot -->
    <div class="flex justify-center mt-4 space-x-2">
      <button
        v-for="(page, index) in paginatedBooks"
        :key="index"
        @click="currentSlide = index"
        :class="[
          'w-3 h-3 rounded-full transition-all',
          currentSlide === index ? 'bg-yellow-600' : 'bg-gray-300',
        ]"
      ></button>
    </div>
  </div>
</template>
<script setup lang="ts">
import { computed, ref } from 'vue';

// Dữ liệu tab và sách
const tabs = [
{ label: 'SÁCH MỚI', value: 'new' },
{ label: 'SÁCH BÁN CHẠY', value: 'bestseller' },
{ label: 'HOT DEALS', value: 'hotdeals' },
];

const booksData = {
new: [
  { id: 1, title: 'Sách Mới 1', description: '...', author: 'Tác giả 1', genre: 'Tiểu thuyết', image: 'https://via.placeholder.com/150x225' },
  { id: 2, title: 'Sách Mới 2', description: '...', author: 'Tác giả 2', genre: 'Lịch sử', image: 'https://via.placeholder.com/150x225' },
  { id: 1, title: 'Sách Mới 1', description: '...', author: 'Tác giả 1', genre: 'Tiểu thuyết', image: 'https://via.placeholder.com/150x225' },
  { id: 2, title: 'Sách Mới 2', description: '...', author: 'Tác giả 2', genre: 'Lịch sử', image: 'https://via.placeholder.com/150x225' },
  { id: 1, title: 'Sách Mới 1', description: '...', author: 'Tác giả 1', genre: 'Tiểu thuyết', image: 'https://via.placeholder.com/150x225' },
  { id: 2, title: 'Sách Mới 2', description: '...', author: 'Tác giả 2', genre: 'Lịch sử', image: 'https://via.placeholder.com/150x225' },

  { id: 2, title: 'Sách Mới 2', description: '...', author: 'Tác giả 2', genre: 'Lịch sử', image: 'https://via.placeholder.com/150x225' },
],
bestseller: [
  { id: 3, title: 'Sách Bán Chạy 1', description: '...', author: 'Tác giả 3', genre: 'Truyện ngắn', image: 'https://via.placeholder.com/150x225' },
  { id: 4, title: 'Sách Bán Chạy 2', description: '...', author: 'Tác giả 4', genre: 'Tiểu sử', image: 'https://via.placeholder.com/150x225' },
],
hotdeals: [
  { id: 5, title: 'Hot Deals 1', description: '...', author: 'Tác giả 5', genre: 'Hài hước', image: 'https://via.placeholder.com/150x225' },
  { id: 6, title: 'Hot Deals 2', description: '...', author: 'Tác giả 6', genre: 'Kinh dị', image: 'https://via.placeholder.com/150x225' },
],
};

// Trạng thái
const activeTab = ref('new');
const currentSlide = ref(0);

// Sách theo tab hiện tại
const books = computed(() => booksData[activeTab.value] || []);
const itemsPerPage = 6;

const paginatedBooks = computed(() => {
const pages = [];
for (let i = 0; i < books.value.length; i += itemsPerPage) {
  pages.push(books.value.slice(i, i + itemsPerPage));
}
return pages;
});

// Hàm chọn tab
const setActiveTab = (tab) => {
activeTab.value = tab;
currentSlide.value = 0; // Reset slide khi đổi tab
};
</script>  