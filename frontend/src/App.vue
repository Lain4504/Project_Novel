<script lang="ts" setup>
import {useRoute} from 'vue-router';
import Header from '@/components/home/Header.vue';
import {computed, onMounted} from 'vue';
import ScrollToTop from '@/components/common/ScrollToTop.vue';
import Footer from '@/components/common/Footer.vue';
import {useStore} from 'vuex';

const route = useRoute();
const store = useStore();

const routesIncludeHome = ['home', 'chapter', 'noveldetail', 'userprofile', 'account', 'list', 'bookmark', 'readinglist', 'library', 'history', 'faq', 'postforum',
  'postdetail', 'postcreateform', 'activation/:token', "postupdateform", 'filterbycategory', "searchadvanced", "notificationlist"
];
const showAdmin = computed(() => {
  return routesIncludeHome.includes(route.name as string);
});
const lightTheme = {};
const darkTheme = {};
const isDarkMode = computed(() => store.getters.isDarkMode);

const theme = computed(() => isDarkMode.value ? darkTheme : lightTheme);

onMounted(() => {
  console.log('Current mode:', store.getters.isDarkMode);
  console.log('Current theme:', theme.value);
});
</script>

<template>
  <a-config-provider :theme="{
    token: {
      colorPrimary: '#18A058',
      colorLink: '#18A058',
      colorLinkHover: '#1dc26b',
  } }">
      <Header v-if="showAdmin"/>
      <RouterView class="min-h-screen"/>
      <ScrollToTop/>
      <Footer v-if="showAdmin"/>
  </a-config-provider>
</template>