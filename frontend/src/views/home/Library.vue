<script setup lang="ts">
import {ref} from 'vue';
import Ads from '@/components/home/Banner.vue';

// Define the data structure for the books
interface Book {
  hinhanh: string;
  tentruyen: string;
  dadoc: number;
  total: number;
}

// Sample data for books
const limitedBooks = ref<Book[]>([
  {
    hinhanh: '/path/to/image1.jpg', // Replace with actual image paths
    tentruyen: 'Làm Sao Rồi, Tình Dịch Liên Không Thể Biến Thành Lão Bà Sao?',
    dadoc: 321,
    total: 400
  },
  {
    hinhanh: '/path/to/image2.jpg', // Replace with actual image paths
    tentruyen: 'Chí Quái Thư',
    dadoc: 280,
    total: 306
  },
  {
    hinhanh: '/path/to/image3.jpg', // Replace with actual image paths
    tentruyen: 'Trận Hồi Trường Sinh',
    dadoc: 900,
    total: 1368
  },
  {
    hinhanh: '/path/to/image4.jpg', // Replace with actual image paths
    tentruyen: 'Hoàng Hôn Phân Giới',
    dadoc: 813,
    total: 896
  }
]);
</script>
<template>
  <div class="max-w-7xl mx-auto">

    <Ads class="my-4"/>
    <div class=" max-w-7xl mx-auto ">
      <div class="flex justify-between items-center my-2">
        <h4 class="text-lg font-semibold">TIỂU THUYẾT ĐÁNH DẤU CỦA BẠN</h4>
      </div>
      <section class="p-4 border rounded my-2">

        <div class="mt-4 text-sm">
          <div class="space-y-4">
            <div
                v-for="(item, index) in limitedBooks"
                :key="index"
                class="flex items-center space-x-4"
            >
              <img
                  :src="item.hinhanh"
                  alt="Book Image"
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
/* Add any custom styles if needed */
</style>
  