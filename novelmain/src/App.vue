<script setup lang="ts">
import { useRoute } from 'vue-router'; // Import useRoute từ vue-router
import Header from './components/home/Header.vue'; // Import Header component

const route = useRoute(); // Lấy route hiện tại

// Mảng chứa tên các route mà bạn muốn hiển thị Header
const routesWithHeader = ['home', 'anotherRoute']; // Thêm các route bạn muốn hiển thị Header

// Dùng computed để theo dõi sự thay đổi của route
import { computed } from 'vue';

const showHeader = computed(() => {
  // Kiểm tra xem route hiện tại có nằm trong danh sách các route cần hiển thị Header hay không
  return routesWithHeader.includes(route.name as string);
});
</script>

<template>
  <!-- Hiển thị Header chỉ khi showHeader là true -->
  <Header v-if="showHeader" />
  <RouterView />
</template>
