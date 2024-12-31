<script lang="ts" setup>
import Ads from '@/components/home/Banner.vue';
import Banner from '@/components/home/Searchbar.vue';
import CompletedNovel from '@/components/home/CompletedNovel.vue';
import NewNovel from '@/components/home/NewNovel.vue';
import PromptNovel from '@/components/home/PromptNovel.vue';
import Reviews from '@/components/home/ReviewsSection.vue';
import StoryList from '@/components/home/StoryList.vue';
import UpdatedList from '@/components/home/UpdatedList.vue';
</script>

<template>
  <Banner/>
  <div class="max-w-[83rem] mx-auto px-4 sm:px-6 lg:px-8 my-5">
    <StoryList/>
    <PromptNovel/>
    <UpdatedList/>
    <NewNovel/>
    <Ads/>
    <CompletedNovel/>
    <Reviews/>
  </div>
</template>