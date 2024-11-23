<script setup lang="ts">
import { useRoute } from 'vue-router'; // Import useRoute từ vue-router
import Header from './components/home/Header.vue'; // Import Header component

const route = useRoute(); // Lấy route hiện tại

// Mảng chứa tên các route mà bạn muốn hiển thị Header
const routesWithoutAdmin = ['home', 'chapter', 'noveldetail']; // Thêm các route bạn muốn hiển thị Header

// Dùng computed để theo dõi sự thay đổi của route
import { computed } from 'vue';
import ScrollToTop from './components/common/ScrollToTop.vue';
import Footer from './components/common/Footer.vue';

const showAdmin = computed(() => {
  // Kiểm tra xem route hiện tại có nằm trong danh sách các route cần hiển thị Header hay không
  return routesWithoutAdmin.includes(route.name as string);
});
</script>

<template>
  <!-- Hiển thị Header chỉ khi showAdmin là true -->
  <Header v-if="showAdmin" />

  <RouterView />
  <ScrollToTop />
  <Footer  v-if="showAdmin" />
</template>
