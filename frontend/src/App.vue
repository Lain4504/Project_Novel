<script lang="ts" setup>
import { useRoute } from 'vue-router';
import Header from '@/components/home/Header.vue';
import { computed, onMounted, watch } from 'vue';
import ScrollToTop from '@/components/common/ScrollToTop.vue';
import Footer from '@/components/common/Footer.vue';
import { useStore } from 'vuex';

const route = useRoute();
const store = useStore();

const routesIncludeHome = ['home', 'chapter', 'noveldetail', 'userprofile', 'account', 'list', 'bookmark', 'readinglist', 'library', 'history', 'faq', 'postforum',
  'postdetail', 'postcreateform', 'activation/:token', "postupdateform", 'filterbycategory', "searchadvanced", "notificationlist"
];
const showAdmin = computed(() => {
  return routesIncludeHome.includes(route.name as string);
});

const isDarkMode = computed(() => store.getters.isDarkMode);

onMounted(() => {
  if (isDarkMode.value) {
    document.documentElement.classList.add('dark');
  } else {
    document.documentElement.classList.remove('dark');
  }
});

watch(isDarkMode, (newValue) => {
  if (newValue) {
    document.documentElement.classList.add('dark');
  } else {
    document.documentElement.classList.remove('dark');
  }
});
</script>

<template>
  <a-config-provider
      :theme="{
      token: {
        colorPrimary: '#18A058',
        colorLink: '#18A058',
        colorLinkHover: '#1dc26b',
      },
    }">
  <Header v-if="showAdmin"/>
  <RouterView class=" min-h-screen"/>
  <ScrollToTop/>
  <Footer v-if="showAdmin"/>
  </a-config-provider>
</template>