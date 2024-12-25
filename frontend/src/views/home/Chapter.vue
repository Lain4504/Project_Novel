<script lang="ts" setup>
import {onMounted, reactive, ref} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import {getChapter, getNextChapter, getPreviousChapter} from '@/api/novelChapter';
import ChapterContent from '@/components/home/ChapterContent.vue';
import {
  createChapterComment,
  createChapterReply,
  getAllChapterComments,
  getAllRepliesByChapterCommentId
} from "@/api/novelComment";
import CommentSection from "@/components/home/CommentSection.vue";

const route = useRoute();
const router = useRouter();
const chapterId = route.params.id as string;

const chapter = reactive({
  id: '',
  title: '',
  content: '',
  created: '',
  chapterNumber: '',
  volumeId: '',
  userId: '',
});

const fetchChapter = async (id: string) => {
  try {
    const response = await getChapter(id);
    console.log('Chapter:', response);
    chapter.id = response.id;
    chapter.title = response.chapterTitle;
    chapter.content = response.content;
    chapter.created = response.created;
    chapter.chapterNumber = response.chapterNumber;
    chapter.volumeId = response.volumeId;
    chapter.userId = response.authorId;
  } catch (error) {
    console.error('Failed to fetch chapter:', error);
  }
};

const fetchPreviousChapter = async () => {
  try {
    const response = await getPreviousChapter(chapter.volumeId, parseInt(chapter.chapterNumber));
    await fetchChapter(response.id);
    router.push({name: 'chapter', params: {id: response.id}});
  } catch (error) {
    console.error('Failed to fetch previous chapter:', error);
  }
};

const fetchNextChapter = async () => {
  try {
    const response = await getNextChapter(chapter.volumeId, parseInt(chapter.chapterNumber));
    await fetchChapter(response.id);
    router.push({name: 'chapter', params: {id: response.id}});
  } catch (error) {
    console.error('Failed to fetch next chapter:', error);
  }
};

const comments = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const totalComments = ref(0);
const fetchComments = async (page: number, size: number) => {
  try {
    const result = await getAllChapterComments(chapterId, page, size);
    console.log('Comments:', result);
    comments.value = result.data;
    totalComments.value = result.totalElements; // Update to use totalElements from the API response
  } catch (error) {
    console.error('Failed to fetch comments:', error);
  }
};
const handleCommentAdded = () => {
  fetchComments(currentPage.value, pageSize.value);
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchComments(page, pageSize.value);
};
onMounted(() => {
  fetchChapter(chapterId);
  fetchComments(currentPage.value, pageSize.value);
});
</script>

<template>
  <ChapterContent :chapter="chapter" @previous-chapter="fetchPreviousChapter" @next-chapter="fetchNextChapter"/>
  <CommentSection :comments="comments" :create-comment-api="createChapterComment"
                  :create-reply-api="createChapterReply" :current-page="currentPage"
                  :fetch-comments="fetchComments"
                  :get-all-replies-api="getAllRepliesByChapterCommentId"
                  :item-name="chapter.title"
                  :itemId="chapterId"
                  :owner-id="chapter.userId"
                  :page-size="pageSize"
                  :total-comments="totalComments"
                  itemType="chapter"
                  @commentAdded="handleCommentAdded"
                  @pageChange="handlePageChange"
  />
</template>