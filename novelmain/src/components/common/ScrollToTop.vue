<template>
<button v-if="isVisible" @click="scrollToTop" class="fixed bottom-4 right-4 ">
    <ToTopOutlined style="font-size: 30px;" />
</button>
  </template>
  
  <script setup lang="ts">
  import { ToTopOutlined } from '@ant-design/icons-vue';
import { ref, onMounted } from 'vue';
  
  // Quản lý hiển thị của nút
  const isVisible = ref(false);
  
  // Kiểm tra khi nào hiển thị nút ScrollToTop
  const checkScrollPosition = () => {
    // Nếu cuộn trang xuống hơn 200px, hiển thị nút
    isVisible.value = window.scrollY > 200;
  };
  
  // Cuộn về đầu trang
  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };
  
  // Lắng nghe sự kiện cuộn
  onMounted(() => {
    window.addEventListener('scroll', checkScrollPosition);
  });
  </script>
  
  <style scoped>
  /* Bạn có thể thêm style tùy chỉnh cho nút ScrollToTop */
  </style>
  