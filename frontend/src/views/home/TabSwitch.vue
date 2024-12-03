<template>
  <div class="max-w-7xl mx-auto">

    <Ads class="my-4" />
    <Breedcrumb :breadcrumbs="[
      { label: 'Home', href: '/' },
      { label: 'Reading List', href: '/list/readinglist', isCurrent: true }
    ]" />
    <div class="flex space-x-4 my-2">
      <router-link
        v-for="tab in tabs"
        :key="tab.name"
        :to="`/list/${tab.name}`"
        class="px-4 py-2 text-sm font-semibold cursor-pointer"
        :class="{
          'text-blue-500 border-b-2 border-blue-500': $route.name === tab.name,
          'text-gray-500': $route.name !== tab.name
        }"
      >
        {{ tab.label }}
      </router-link>
    </div>

    <div class="mt-4">
      <router-view />
    </div>
  </div>
</template>

<script setup lang="ts">
import Ads from '@/components/home/Banner.vue';
import Breedcrumb from '@/components/home/Breadcrumb.vue';
interface Tab {
  name: string;
  label: string;
}

const tabs: Tab[] = [
  { name: 'readinglist', label: 'TRUYỆN ĐANG ĐỌC' },
  { name: 'bookmark', label: 'BOOKMARK' }
];
</script>

<style scoped>
/* Add custom styles if needed */
</style>
