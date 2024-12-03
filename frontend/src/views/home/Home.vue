<script setup lang="ts">
import Ads from '@/components/home/Banner.vue';
import Banner from '@/components/home/Carousel.vue';
import CompletedNovel from '@/components/home/CompletedNovel.vue';
import NewNovel from '@/components/home/NewNovel.vue';
import PromtNovel from '@/components/home/PromtNovel.vue';
import Reviews from '@/components/home/ReviewsSection.vue';
import StoryList from '@/components/home/StoryList.vue';
import UpdatedList from '@/components/home/UpdatedList.vue';
</script>

<template>
  <!-- Wrapper to limit width and ensure padding on smaller screens -->
  <div class="max-w-[83rem] mx-auto px-4 sm:px-6 lg:px-8 my-5 ">
    <Banner />
    <StoryList />
    <PromtNovel/>
    <Ads/>
    <UpdatedList/>
    <NewNovel/>
    <Ads/>
    <CompletedNovel/>
    <Reviews/>
    </div>
</template>

