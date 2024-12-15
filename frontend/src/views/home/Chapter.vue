<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getChapter, getPreviousChapter, getNextChapter } from '@/api/chapter';
import ChapterContent from '@/components/home/ChapterContent.vue';
import {
  createChapterComment,
  createChapterReply,
  getAllChapterComments,
  getAllRepliesByChapterCommentId
} from "@/api/novelcomment";
import CommentSection from "@/components/home/CommentSection.vue";

const route = useRoute();
const router = useRouter();
const chapterId = route.params.id as string;

const chapter = reactive({
  title: '',
  content: '',
  created: '',
  chapterNumber: '',
  volumeId: '',
});

const fetchChapter = async (id: string) => {
  const response = await getChapter(id);
  chapter.title = response.chapterTitle;
  chapter.content = response.content;
  chapter.created = response.created;
  chapter.chapterNumber = response.chapterNumber;
  chapter.volumeId = response.volumeId;
};

const fetchPreviousChapter = async () => {
  const response = await getPreviousChapter(chapter.volumeId, parseInt(chapter.chapterNumber));
  await fetchChapter(response.id);
  router.push({ name: 'chapter', params: { id: response.id } });
};

const fetchNextChapter = async () => {
  const response = await getNextChapter(chapter.volumeId, parseInt(chapter.chapterNumber));
  await fetchChapter(response.id);
  router.push({ name: 'chapter', params: { id: response.id } });
};


const comments = ref([]);
const fetchComments = async () => {
  try {
    const result = await getAllChapterComments(chapterId);
    comments.value = result;
  } catch (error) {
    console.error('Failed to fetch comments:', error);
  }
}
const handleCommentAdded = () => {
  fetchComments();
};
onMounted(() =>{
  fetchChapter(chapterId);
  fetchComments();
});
</script>

<template>
  <ChapterContent :chapter="chapter" @previous-chapter="fetchPreviousChapter" @next-chapter="fetchNextChapter" />
  <CommentSection :itemId="chapterId" itemType="chapter" :comments="comments" @commentAdded="handleCommentAdded"
                  :create-comment-api="createChapterComment"
                  :create-reply-api="createChapterReply"
                  :get-all-replies-api="getAllRepliesByChapterCommentId"/>
</template>