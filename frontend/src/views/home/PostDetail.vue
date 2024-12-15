<script setup lang="ts">
import { onMounted, ref } from 'vue';
import Ads from '@/components/home/Banner.vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';
import { getPost } from '@/api/post';
import { getAllComments, createComment, createReply, getAllRepliesByCommentId } from '@/api/postcomment';
import { getUserProfile } from "@/api/user";
import store from "@/store";

const props = defineProps({
  id: {
    type: String,
    required: true
  }
});

type Post = {
  title: string;
  content: string;
};

type Comment = {
  id: string;
  postId: string;
  userId: string;
  content: string;
  replyCount: number;
  createdDate: string;
  updateDateTime: string;
  username: string;
  userAvatar: string;
  replies?: Reply[];
};

type Reply = {
  id: string;
  commentId: string;
  userId: string;
  replyContent: string;
  replyTo: string;
  createdDate: string;
  updateDateTime: string;
  username: string;
  userAvatar: string;
};

const currentUser = ref({
  username: '',
  image: ''
});
const fetchCurrentUser = async () => {
  try {
    const result = await getUserProfile(store.getters.getUserId);
    currentUser.value.username = result.username;
    currentUser.value.image = result.image.path;
  } catch (error) {
    console.error('Failed to fetch current user:', error);
  }
};
onMounted(() => {
  fetchCurrentUser();
});
const post = ref<Post>({ title: '', content: '' });
const comments = ref<Comment[]>([]);
const newComment = ref('');
const replyText = ref<{ [key: string]: string }>({});
const replyBoxes = ref<{ [key: string]: boolean }>({});
const showReplies = ref<{ [key: string]: boolean }>({});
const isLoading = ref(false);

const fetchPost = async () => {
  isLoading.value = true;
  try {
    const result = await getPost(props.id);
    post.value = result;
  } catch (error) {
    console.error('Failed to fetch post:', error);
  } finally {
    isLoading.value = false;
  }
};

const fetchComments = async () => {
  try {
    const result = await getAllComments(props.id);
    comments.value = result;
  } catch (error) {
    console.error('Failed to fetch comments:', error);
  }
};

const fetchReplies = async (commentId: string) => {
  try {
    const replies = await getAllRepliesByCommentId(commentId);
    const comment = comments.value.find(comment => comment.id === commentId);
    if (comment) {
      comment.replies = replies;
    }
    showReplies.value[commentId] = true;
  } catch (error) {
    console.error('Failed to fetch replies:', error);
  }
};

const addComment = async () => {
  if (newComment.value.trim()) {
    try {
      const commentData = {
        postId: props.id,
        content: newComment.value,
        userId: store.getters.getUserId
      };
      await createComment(commentData);
      newComment.value = '';
      fetchComments();
    } catch (error) {
      console.error('Failed to add comment:', error);
    }
  }
};

const toggleReplyBox = (commentId: string) => {
  replyBoxes.value[commentId] = !replyBoxes.value[commentId];
};

const submitReply = async (commentId: string) => {
  if (replyText.value[commentId]?.trim()) {
    try {
      const comment = comments.value.find(comment => comment.id === commentId);
      const replyData = {
        commentId,
        replyContent: replyText.value[commentId],
        userId: store.getters.getUserId,
        replyTo: comment ? comment.username : ''
      };
      await createReply(replyData);
      replyText.value[commentId] = '';
      fetchReplies(commentId); // Fetch replies again to update the list
    } catch (error) {
      console.error('Failed to submit reply:', error);
    }
  }
};

const toggleReplyBoxForReply = (replyId: string) => {
  replyBoxes.value[replyId] = !replyBoxes.value[replyId];
};

const submitReplyForReply = async (replyId: string, commentId: string) => {
  if (replyText.value[replyId]?.trim()) {
    try {
      const comment = comments.value.find(comment => comment.id === commentId);
      const reply = comment?.replies?.find(reply => reply.id === replyId);
      const replyData = {
        commentId,
        parentReplyId: replyId,
        replyContent: replyText.value[replyId],
        userId: store.getters.getUserId,
        replyTo: reply ? reply.username : ''
      };
      await createReply(replyData);
      replyText.value[replyId] = '';
      fetchReplies(commentId); // Fetch replies again to update the list
    } catch (error) {
      console.error('Failed to submit reply:', error);
    }
  }
};
const toggleShowReplies = (commentId: string) => {
  if (showReplies.value[commentId]) {
    showReplies.value[commentId] = false;
  } else {
    fetchReplies(commentId);
  }
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
        <div class="text-sm text-gray-500">3 years ago</div>
      </div>
      <h1 class="text-3xl font-bold text-gray-800 mb-4">{{ post.title }}</h1>
      <div class="content-body mb-6" v-html="post.content"></div>
      <div class="mt-6">
        <div class="flex items-start space-x-4">
          <!-- Avatar của người dùng -->
          <img :src="currentUser.image" alt="User Avatar" class="w-10 h-10 bg-gray-300 rounded-full">
          <!-- Khung nhập text và nút gửi -->
          <div class="flex-1">
    <textarea
        class="w-full p-3 mt-1 border rounded-lg shadow-sm"
        v-model="newComment"
        placeholder="Add a comment..."
    ></textarea>
            <button
                @click="addComment"
                class="px-5 py-2 bg-[#98a77c] hover:bg-[#88976c] text-white rounded text-sm mt-2"
            >
              Send
            </button>
          </div>
        </div>
        <div class="mt-6">
          <h2 class="text-xl font-semibold text-gray-800">{{ comments.length }} Comments</h2>
        </div>
        <ul class="mt-4 space-y-6">
          <li v-for="comment in comments" :key="comment.id" class="border-b pb-4">
            <div class="flex items-start space-x-4">
                <img :src="comment.userAvatar" alt="" class="w-10 h-10 bg-gray-300 rounded-full">
              <div class="w-full">
                <p class="text-gray-700 font-semibold">{{ comment.username }}</p>
                <p class="text-gray-600">{{ comment.content }}</p>
                <div class="flex items-center space-x-4 mt-2">
                  <button @click="toggleReplyBox(comment.id)" class="text-gray-500 hover:text-blue-500 text-sm">💬
                    Reply
                  </button>
                  <button @click="toggleShowReplies(comment.id)" class="text-gray-500 hover:text-blue-500 text-sm">
                    {{ showReplies[comment.id] ? 'Hide Replies' : 'Show More' }}
                  </button>
                </div>
                <div v-if="replyBoxes[comment.id]" class="mt-2">
                  <textarea v-model="replyText[comment.id]"
                            class="w-full p-4 text-lg border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm"
                            placeholder="Write your reply..."></textarea>
                  <button @click="submitReply(comment.id)"
                          class="px-4 py-2 bg-[#98a77c] hover:bg-[#88976c] text-white rounded text-sm mt-2">Reply
                  </button>
                </div>
                <ul v-if="showReplies[comment.id] && comment.replies" class="mt-4 space-y-4">
                  <li v-for="reply in comment.replies" :key="reply.id" class="pl-4 border-l">
                      <img :src="reply.userAvatar" alt="" class="w-10 h-10 bg-gray-300 rounded-full">
                    <p class="text-gray-700 font-semibold">{{ reply.username }}</p>
                    <p class="text-gray-600">@{{ reply.replyTo}}: {{ reply.replyContent }}</p>
                    <div class="flex items-center space-x-4 mt-2">
                      <button @click="toggleReplyBoxForReply(reply.id)"
                              class="text-gray-500 hover:text-blue-500 text-sm">💬 Reply
                      </button>
                    </div>
                    <div v-if="replyBoxes[reply.id]" class="mt-2">
                      <textarea v-model="replyText[reply.id]"
                                class="w-full p-4 text-lg border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm"
                                placeholder="Write your reply..."></textarea>
                      <button @click="submitReplyForReply(reply.id, comment.id)"
                              class="px-4 py-2 bg-[#98a77c] hover:bg-[#88976c] text-white rounded text-sm mt-2">Reply
                      </button>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.content-body {
  font-size: 1rem;
  line-height: 1.75;
}
</style>