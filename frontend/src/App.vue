<script lang="ts" setup>
import {useRoute} from 'vue-router';
import Header from '@/components/home/Header.vue';
import {computed} from 'vue';
import ScrollToTop from '@/components/common/ScrollToTop.vue';
import Footer from '@/components/common/Footer.vue';

const route = useRoute();

const routesIncludeHome = ['home', 'chapter', 'noveldetail', 'userprofile', 'account', 'list', 'bookmark', 'readinglist', 'library', 'history', 'faq', 'postforum',
  'postdetail', 'postcreateform', 'activation/:token', "postupdateform", 'filterbycategory', "searchadvanced", "notificationlist"
];
const showAdmin = computed(() => {
  return routesIncludeHome.includes(route.name as string);
});
</script>

<template>
  <Header v-if="showAdmin"/>
  <RouterView class=" min-h-screen"/>
  <ScrollToTop/>
  <Footer v-if="showAdmin"/>
</template>
