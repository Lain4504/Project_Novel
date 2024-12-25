<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import Ads from '@/components/home/Banner.vue';
import {getPost} from '@/api/post';
import {createComment, createReply, getAllComments, getAllRepliesByCommentId} from '@/api/postComment';
import CommentSection from '@/components/home/CommentSection.vue';

const props = defineProps({
  id: {
    type: String,
    required: true
  }
});

interface Post {
  id: string;
  title: string;
  content: string;
  userId: string;
  created: string;
}

const post = ref<Post>({
  id: '',
  title: '',
  content: '',
  userId: '',
  created: ''
});
const comments = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const totalComments = ref(0);

const fetchPost = async () => {
  try {
    const result = await getPost(props.id);
    post.value = result;
  } catch (error) {
    console.error('Failed to fetch post:', error);
  }
};

const fetchComments = async (page: number, size: number) => {
  try {
    const result = await getAllComments(props.id, page, size);
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
  fetchPost();
  fetchComments(currentPage.value, pageSize.value);
});
</script>

<template>
  <div class="max-w-7xl mx-auto">
    <Ads class="my-4"/>
    <div class="bg-white p-6 mt-6 rounded-lg shadow-md">
      <div class="flex items-center justify-between mb-6">
        <div class="flex items-center">
          <img alt="Avatar" class="w-10 h-10 rounded-full mr-3" src="https://via.placeholder.com/40">
          <div class="font-semibold text-lg">User <span class="text-gray-500 text-sm">Administrators</span></div>
        </div>
        <div class="text-sm text-gray-500">{{ post.created }}</div>
      </div>
      <h1 class="text-3xl font-bold text-gray-800 mb-4">{{ post.title }}</h1>
      <div class="content-body mb-6" v-html="post.content"></div>
      <CommentSection :comments="comments" :create-comment-api="createComment"
                      :create-reply-api="createReply" :current-page="currentPage"
                      :fetch-comments="fetchComments"
                      :get-all-replies-api="getAllRepliesByCommentId"
                      :item-name="post.title"
                      :itemId="props.id"
                      :owner-id="post.userId"
                      :page-size="pageSize"
                      :total-comments="totalComments"
                      itemType="post"
                      @commentAdded="handleCommentAdded"
                      @pageChange="handlePageChange"
      />
    </div>
  </div>
</template>

<style scoped>
.content-body {
  font-size: 1rem;
  line-height: 1.75;
}
</style>