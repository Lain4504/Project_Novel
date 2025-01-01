<script lang="ts" setup>
import { computed, onMounted } from 'vue';
import ScrollToTop from "./components/common/ScrollToTop.vue";
import store from "./store";


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
        <router-view/>
      <ScrollToTop/>
  </a-config-provider>
</template>