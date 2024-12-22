<script setup lang="ts">
import { onMounted, ref } from 'vue';
import Ads from '@/components/home/Banner.vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';
import { getPost } from '@/api/post';
import { getAllComments, createComment, createReply, getAllRepliesByCommentId } from '@/api/postcomment';
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

const fetchPost = async () => {
  try {
    const result = await getPost(props.id);
    post.value = result;
  } catch (error) {
    console.error('Failed to fetch post:', error);
  }
};
const fetchComments = async () => {
  try {
    const result = await getAllComments(props.id);
    comments.value = result.data;
  } catch (error) {
    console.error('Failed to fetch comments:', error);
  }
};

const handleCommentAdded = () => {
  fetchComments();
};

onMounted(() => {
  fetchPost();
  fetchComments();
});
</script>

<template>
  <div class="max-w-7xl mx-auto">
    <Ads class="my-4"/>
    <Breadcrumb :breadcrumbs="[
      { label: 'Home', to: '/' },
      { label: 'Forum', to: '/forum' },
      { label: 'Post', to: '/post', isCurrent: true }
    ]"/>
    <div class="bg-white p-6 mt-6 rounded-lg shadow-md">
      <div class="flex items-center justify-between mb-6">
        <div class="flex items-center">
          <img src="https://via.placeholder.com/40" alt="Avatar" class="w-10 h-10 rounded-full mr-3">
          <div class="font-semibold text-lg">User <span class="text-gray-500 text-sm">Administrators</span></div>
        </div>
        <div class="text-sm text-gray-500">{{ post.created }}</div>
      </div>
      <h1 class="text-3xl font-bold text-gray-800 mb-4">{{ post.title }}</h1>
      <div class="content-body mb-6" v-html="post.content"></div>
      <CommentSection :itemId="props.id" itemType="post" :comments="comments" @commentAdded="handleCommentAdded"
                      :create-comment-api="createComment"
                      :create-reply-api="createReply"
                      :owner-id="post.userId"
                      :item-name="post.title"
                      :get-all-replies-api="getAllRepliesByCommentId"/>
    </div>
  </div>
</template>

<style scoped>
.content-body {
  font-size: 1rem;
  line-height: 1.75;
}
</style>