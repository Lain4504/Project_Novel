<script lang="ts" setup>
import Ads from '@/components/home/Banner.vue';

interface TabSwitch {
  name: string;
  label: string;
}

const tabs: TabSwitch[] = [
  {name: 'readinglist', label: 'TRUYỆN ĐANG ĐỌC'},
  {name: 'bookmark', label: 'BOOKMARK'}
];
</script>

<template>
  <section class="max-w-7xl mx-auto bg-white p-4 rounded">
    <Ads class="my-4"/>
    <nav class="flex space-x-4 my-2">
      <router-link
          v-for="tab in tabs"
          :key="tab.name"
          :class="{
          'text-[#18A058] border-b-2 border-[#18A058]': $route.name === tab.name,
          'text-gray-500': $route.name !== tab.name
        }"
          :to="`/list/${tab.name}`"
          class="px-4 py-2 text-sm font-semibold cursor-pointer"
      >
        {{ tab.label }}
      </router-link>
    </nav>

    <article class="mt-4 bg-white p-4 rounded">
      <router-view/>
    </article>
  </section>
</template>

<style scoped>
/* Add custom styles if needed */
</style>