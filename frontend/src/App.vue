<script setup lang="ts">
import { useRoute } from 'vue-router'; // Import useRoute từ vue-router
import Header from '@/components/home/Header.vue'; // Import Header component
import Notification from '@/components/common/Notification.vue';
import { computed, ref, provide } from 'vue';
import ScrollToTop from '@/components/common/ScrollToTop.vue';
import Footer from '@/components/common/Footer.vue';
const route = useRoute(); // Lấy route hiện tại

const routesIncludeHome = ['home', 'chapter', 'noveldetail', 'userprofile', 'member', 'list', 'bookmark', 'readinglist', 'library', 'history', 'faq', 'postforum',
  'postdetail', 'postcreateform', 'activation/:token'
]; // Thêm các route bạn muốn hiển thị Header
const showAdmin = computed(() => {
  // Kiểm tra xem route hiện tại có nằm trong danh sách các route cần hiển thị Header hay không
  return routesIncludeHome.includes(route.name as string);
});
const alertNotification = ref();
provide('showAlert', (type: string, message: string) => {
  alertNotification.value.showAlert(type, message);
});
</script>

<template>
  <!-- Wrap the entire content with a div to apply the background color -->
  <div class="bg-[#F5F4EF]">
    <Notification ref="alertNotification" />
    <Header v-if="showAdmin" />
    <RouterView/>
    <ScrollToTop />
    <Footer v-if="showAdmin" />
  </div>
</template>
