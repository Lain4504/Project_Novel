<script setup lang="ts">
import { ref, onMounted } from 'vue';
import store from "@/store";
import { getUserProfile } from "@/api/user";

interface Comment {
  id: string;
  userId: string;
  username: string;
  userAvatar: string;
  content: string;
  replies?: Reply[];
}

interface Reply {
  id: string;
  username: string;
  userAvatar: string;
  replyContent: string;
  replyTo: string;
}

interface PostCommentReply {
  commentId: string;
  parentReplyId?: string;
  replyContent: string;
  userId: string;
  replyTo: string;
  postName?: string;
  userIdOfReplyTo?: string;
}

const props = defineProps<{
  itemId: string;
  itemType: 'post' | 'novel' | 'chapter';
  comments: Comment[];
  ownerId?: string;
  postName?: string;
  createCommentApi: (data: any) => Promise<any>;
  createReplyApi: (data: any) => Promise<any>;
  getAllRepliesApi: (commentId: string) => Promise<any>;
}>();

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

const newComment = ref('');
const replyText = ref<Record<string, string>>({});
const replyBoxes = ref<Record<string, boolean>>({});
const showReplies = ref<Record<string, boolean>>({});

const fetchReplies = async (commentId: string) => {
  try {
    const replies = await props.getAllRepliesApi(commentId);
    const comment = props.comments.find(comment => comment.id === commentId);
    if (comment) {
      comment.replies = replies;
    }
    showReplies.value[commentId] = true;
  } catch (error) {
    console.error('Failed to fetch replies:', error);
  }
};
const emit = defineEmits(['commentAdded']);
const addComment = async () => {
  if (newComment.value.trim()) {
    try {
      const commentData: any = {
        content: newComment.value,
        userId: store.getters.getUserId,
        ownerId: props.ownerId,
        postName: props.postName,
        replies: []
      };
      if (props.itemType === 'post') {
        commentData.postId = props.itemId;
      } else if (props.itemType === 'novel') {
        commentData.novelId = props.itemId;
      } else if (props.itemType === 'chapter') {
        commentData.chapterId = props.itemId;
      }
      await props.createCommentApi(commentData);
      newComment.value = '';
      emit('commentAdded');
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
      const comment = props.comments.find(comment => comment.id === commentId);
      const replyData: PostCommentReply = {
        commentId,
        replyContent: replyText.value[commentId],
        userId: store.getters.getUserId,
        replyTo: comment ? comment.username : '',
        userIdOfReplyTo: comment ? comment.userId : '',
        postName: props.postName
      };
      await props.createReplyApi(replyData);
      replyText.value[commentId] = '';
      fetchReplies(commentId);
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
      const comment = props.comments.find(comment => comment.id === commentId);
      const reply = comment?.replies?.find(reply => reply.id === replyId);
      const replyData: PostCommentReply = {
        commentId,
        parentReplyId: replyId,
        replyContent: replyText.value[replyId],
        userId: store.getters.getUserId,
        replyTo: reply ? reply.username : ''
      };
      await props.createReplyApi(replyData);
      replyText.value[replyId] = '';
      fetchReplies(commentId);
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
</script>
<template>
  <div class="bg-gray-50">
    <section class="bg-gray-50 py-4 px-6 max-w-5xl mx-auto">
      <h3 class="text-xl font-bold mb-4">Comment</h3>
      <div class="flex flex-col">
        <textarea v-model="newComment" placeholder="Để lại bình luận..."
          class="w-full p-4 text-lg border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm"></textarea>
        <div class="flex justify-end">
          <button @click="addComment"
            class="px-5 py-2 bg-[#98a77c] hover:bg-[#88976c] text-white rounded text-sm">
            Send
          </button>
        </div>
      </div>

      <ul class="mt-4 space-y-6">
        <li v-for="comment in comments" :key="comment.id" class="border-b pb-4">
          <div class="flex items-start space-x-4">
            <img :src="comment.userAvatar" alt="" class="w-10 h-10 bg-gray-300 rounded-full">
            <div class="w-full">
              <p class="text-gray-700 font-semibold">{{ comment.username }}</p>
              <p class="text-gray-600">{{ comment.content }}</p>
              <div class="flex items-center space-x-4 mt-2">
                <button @click="toggleReplyBox(comment.id)" class="text-gray-500 hover:text-blue-500 text-sm">💬 Reply</button>
                <button @click="toggleShowReplies(comment.id)" class="text-gray-500 hover:text-blue-500 text-sm">
                  {{ showReplies[comment.id] ? 'Hide Replies' : 'Show More' }}
                </button>
              </div>
              <div v-if="replyBoxes[comment.id]" class="mt-2">
                <textarea v-model="replyText[comment.id]"
                  class="w-full p-4 text-lg border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm"
                  placeholder="Write your reply..."></textarea>
                <div class="flex justify-end">
                  <button @click="submitReply(comment.id)"
                    class="px-4 py-2 bg-[#98a77c] hover:bg-[#88976c] text-white rounded text-sm mt-2">Reply</button>
                </div>
              </div>
              <ul v-if="showReplies[comment.id] && comment.replies" class="mt-4 space-y-4">
                <li v-for="reply in comment.replies" :key="reply.id" class="pl-4 border-l">
                  <img :src="reply.userAvatar" alt="" class="w-10 h-10 bg-gray-300 rounded-full">
                  <p class="text-gray-700 font-semibold">{{ reply.username }}</p>
                  <p class="text-gray-600">@{{ reply.replyTo }}: {{ reply.replyContent }}</p>
                  <div class="flex items-center space-x-4 mt-2">
                    <button @click="toggleReplyBoxForReply(reply.id)"
                      class="text-gray-500 hover:text-blue-500 text-sm">💬 Reply</button>
                  </div>
                  <div v-if="replyBoxes[reply.id]" class="mt-2">
                    <textarea v-model="replyText[reply.id]"
                      class="w-full p-4 text-lg border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm"
                      placeholder="Write your reply..."></textarea>
                    <div class="flex justify-end">
                      <button @click="submitReplyForReply(reply.id, comment.id)"
                        class="px-4 py-2 bg-[#98a77c] hover:bg-[#88976c] text-white rounded text-sm mt-2">Reply</button>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </li>
      </ul>
    </section>
  </div>
</template>