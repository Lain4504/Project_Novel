<script setup lang="ts">
import Ads from '../../components/home/Ads.vue';
import Banner from '../../components/home/Banner.vue';
import CompletedNovel from '../../components/home/CompletedNovel.vue';
import NewNovel from '../../components/home/NewNovel.vue';
import PromtNovel from '../../components/home/PromtNovel.vue';
import Reviews from '../../components/home/Reviews.vue';
import StoryList from '../../components/home/StoryList.vue';
import UpdatedList from '../../components/home/UpdatedList.vue';
</script>

<template>
  <!-- Wrapper to limit width and ensure padding on smaller screens -->
  <div class="max-w-[83rem] mx-auto px-4 sm:px-6 lg:px-8 my-5">
    <Banner />
    <PromtNovel/>
    <StoryList />
    <Ads/>
    <UpdatedList/>
    <NewNovel/>
    <Ads/>
    <CompletedNovel/>
    <Reviews/>
      </div>
</template>

<style scoped>
/* Ensure the container doesn't exceed 1200px and is centered */
.max-w-screen-lg {
  max-width: 1350px;
  margin-left: auto;
  margin-right: auto;
}

/* Optional: if you want to add custom padding or further tweaks */
</style>
