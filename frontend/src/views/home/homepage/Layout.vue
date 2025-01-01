<script setup lang="ts">
import { defineAsyncComponent } from 'vue';

const CompletedNovel = defineAsyncComponent(() => import('./CompletedNovel.vue'));
const NewNovel = defineAsyncComponent(() => import('./NewNovel.vue'));
const PromptNovel = defineAsyncComponent(() => import('./PromptNovel.vue'));
const Reviews = defineAsyncComponent(() => import('./ReviewsSection.vue'));
const StoryList = defineAsyncComponent(() => import('./StoryList.vue'));
const UpdatedList = defineAsyncComponent(() => import('./UpdatedList.vue'));
</script>

<template>
    <StoryList/>
    <PromptNovel/>
    <UpdatedList/>
    <NewNovel/>
    <Banner/>
    <CompletedNovel/>
    <Reviews/>
</template>

<style scoped>

</style>