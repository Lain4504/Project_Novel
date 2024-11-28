<script setup lang="ts">
import { useRoute } from 'vue-router'; // Import useRoute từ vue-router
import Header from '@/components/home/Header.vue'; // Import Header component
import Alert from '@/components/common/Alert.vue';
import { computed, ref, provide } from 'vue';
import ScrollToTop from '@/components/common/ScrollToTop.vue';
import Footer from '@/components/common/Footer.vue';
const route = useRoute(); // Lấy route hiện tại

// Mảng chứa tên các route mà bạn muốn hiển thị Header
const routesWithoutAdmin = ['home', 'chapter', 'noveldetail', 'userprofile', 'member','list', 'bookmark', 'readinglist', 'library', 'history' ]; // Thêm các route bạn muốn hiển thị Header
const showAdmin = computed(() => {
  // Kiểm tra xem route hiện tại có nằm trong danh sách các route cần hiển thị Header hay không
  return routesWithoutAdmin.includes(route.name as string);
});
const alertRef = ref();
provide('showAlert', (type: string, message: string) => {
  alertRef.value.showAlert(type, message);
});
</script>

<template>
   <Alert ref="alertRef" />
  <Header v-if="showAdmin" />
  <RouterView />
  <ScrollToTop />
  <Footer  v-if="showAdmin" />
</template>
