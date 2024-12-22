<script setup lang="ts">
import { useRoute } from 'vue-router'; // Import useRoute từ vue-router
import Header from '@/components/home/Header.vue'; // Import Header component
import { computed, ref, provide } from 'vue';
import ScrollToTop from '@/components/common/ScrollToTop.vue';
import Footer from '@/components/common/Footer.vue';
const route = useRoute(); // Lấy route hiện tại

const routesIncludeHome = ['home', 'chapter', 'noveldetail', 'userprofile', 'account', 'list', 'bookmark', 'readinglist', 'library', 'history', 'faq', 'postforum',
  'postdetail', 'postcreateform', 'activation/:token', "postupdateform", 'filterbycategory', "searchadvanced", "notificationlist"
];
const showAdmin = computed(() => {
  return routesIncludeHome.includes(route.name as string);
});
</script>

<template>
    <Header v-if="showAdmin" />
    <RouterView/>
    <ScrollToTop />
    <Footer v-if="showAdmin" />
</template>
