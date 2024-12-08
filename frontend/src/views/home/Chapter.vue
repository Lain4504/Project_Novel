<script setup lang="ts">
import ChapterContent from '@/components/home/ChapterContent.vue';
import Comment from '@/components/home/CommentSection.vue';
import { useRoute } from 'vue-router';
const route = useRoute();
const chapterId = route.params.id as string;
</script>
<template>
  <ChapterContent :chapterId="chapterId" />
<Comment/>
</template>
