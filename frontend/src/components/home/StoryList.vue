
<script setup lang="ts">
import { computed, ref } from 'vue';

// Data
const readings = ref([
  {
    hinhanh: "https://via.placeholder.com/56x80",
    tentruyen: "Tựa truyện 1",
    dadoc: 5,
    total: 20,
  },
  {
    hinhanh: "https://via.placeholder.com/56x80",
    tentruyen: "Tựa truyện 2",
    dadoc: 10,
    total: 30,
  },
  {
    hinhanh: "https://via.placeholder.com/56x80",
    tentruyen: "Tựa truyện 3",
    dadoc: 3,
    total: 15,
  },
  {
    hinhanh: "https://via.placeholder.com/56x80",
    tentruyen: "Tựa truyện 4",
    dadoc: 3,
    total: 15,
  },
]);

const latestPosts = ref([
  {
    title: "Bài viết 1",
    date: "25/11/2024",
    author: "Tác giả 1",
  },
  {
    title: "Bài viết 2",
    date: "24/11/2024",
    author: "Tác giả 2",
  },
  {
    title: "Bài viết 3",
    date: "23/11/2024",
    author: "Tác giả 3",
  },
]);

// Computed property
const limitedReadings = computed(() => readings.value.slice(0, 4));
</script>
<template>
  <div class="flex flex-col md:flex-row md:space-x-4 space-y-4 md:space-y-0 mt-10">
    <!-- Bài viết mới nhất Section -->
    <div class="w-full md:w-3/5">
      <section class="p-4 border rounded">
        <div class="flex justify-between items-center">
          <h4 class="text-md font-bold">Bài viết mới nhất</h4>
          <RouterLink to="#" class="text-[#98a77c] text-sm">Xem tất cả</RouterLink>
        </div>
        <div class="mt-4 text-sm">
          <ul class="space-y-4">
            <li
                v-for="(post, index) in latestPosts"
                :key="index"
                class="border-b pb-2"
            >
              <h5 class="font-semibold truncate">{{ post.title }}</h5>
              <p class="text-gray-500 text-xs">
                {{ post.date }} - {{ post.author }}
              </p>
            </li>
          </ul>
        </div>
      </section>
    </div>

    <!-- Đang đọc Section -->
    <div class="w-full md:w-2/5">
      <section class="p-4 border rounded">
        <div class="flex justify-between items-center">
          <h4 class="text-md font-bold">Đang đọc</h4>
          <RouterLink to="/list/readinglist" class="text-[#98a77c] text-sm">Xem tất cả</RouterLink>
        </div>
        <div class="mt-4 text-sm">
          <div class="space-y-4">
            <div
                v-for="(item, index) in limitedReadings"
                :key="index"
                class="flex items-center space-x-4"
            >
              <img
                  :src="item.hinhanh"
                  alt="Reading Image"
                  class="w-14 h-20 object-cover"
              />
              <div class="flex justify-between items-center w-full">
                <h5 class="font-semibold truncate">{{ item.tentruyen }}</h5>
                <p class="text-gray-500 flex-shrink-0">
                  Chương {{ item.dadoc }} / {{ item.total }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>


<style scoped>
/* Thêm CSS nếu cần */
</style>
