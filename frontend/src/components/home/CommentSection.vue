<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import store from "@/store";
import {getUserProfile} from "@/api/user";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

interface Comment {
  id: string;
  userId: string;
  username: string;
  userAvatar: string;
  content: string;
  replies?: Reply[];
  replyPage?: number;
  replyCount: number;
}

interface Reply {
  id: string;
  username: string;
  userAvatar: string;
  replyContent: string;
  replyTo: string;
  userIdOfReplyTo: string;
  userId: string;
}

const props = defineProps<{
  itemId: string;
  itemType: 'post' | 'novel' | 'chapter';
  comments: Comment[];
  ownerId?: string;
  itemName?: string;
  createCommentApi: (data: any) => Promise<any>;
  createReplyApi: (data: any) => Promise<any>;
  getAllRepliesApi: (commentId: string, page: number, size: number) => Promise<any>;
  fetchComments: (page: number, size: number) => Promise<void>;
  currentPage: number;
  pageSize: number;
  totalComments: number;
}>();

const currentUser = ref({
  username: '',
  image: ''
});
const isAuthenticated = store.getters.isAuthenticated;

const fetchCurrentUser = async () => {
  if (isAuthenticated) {
    try {
      const result = await getUserProfile(store.getters.getUserId);
      currentUser.value.username = result.username;
      currentUser.value.image = result.image.path;
    } catch (error) {
      console.error('Failed to fetch current user:', error);
    }
  }
};

onMounted(() => {
  fetchCurrentUser();
});

const newComment = ref<string>('');
const replyText = ref<Record<string, string>>({});
const replyBoxes = ref<Record<string, boolean>>({});
const showReplies = ref<Record<string, boolean>>({});
const replyPageSize = 10;

const fetchReplies = async (commentId: string, page: number) => {
  try {
    const result = await props.getAllRepliesApi(commentId, page, replyPageSize);
    const comment = props.comments.find(comment => comment.id === commentId);
    if (comment) {
      if (!comment.replies) {
        comment.replies = [];
      }
      comment.replies.push(...result.data);
      comment.replyPage = page;
    }
    showReplies.value[commentId] = true;
  } catch (error) {
    console.error('Failed to fetch replies:', error);
  }
};

const emit = defineEmits(['commentAdded', 'pageChange']);
const addComment = async () => {
  console.log('newComment:', newComment.value);
  if (newComment.value.trim()) {
    try {
      const commentData: any = {
        content: newComment.value,
        userId: store.getters.getUserId,
        ownerId: props.ownerId,
      };
      if (props.itemType === 'post') {
        commentData.postId = props.itemId;
        commentData.postName = props.itemName;
      } else if (props.itemType === 'novel') {
        commentData.novelId = props.itemId;
        commentData.novelName = props.itemName;
      } else if (props.itemType === 'chapter') {
        commentData.chapterId = props.itemId;
        commentData.chapterName = props.itemName;
      }
      await props.createCommentApi(commentData);
      newComment.value = '';
      emit('commentAdded');
    } catch (error) {
      console.error('Failed to add comment:', error);
    }
  }
};

const handlePageChange = (page: number) => {
  emit('pageChange', page);
};

const toggleReplyBox = (commentId: string) => {
  replyBoxes.value[commentId] = !replyBoxes.value[commentId];
};

const submitReply = async (commentId: string) => {
  if (replyText.value[commentId]?.trim()) {
    try {
      const comment = props.comments.find(comment => comment.id === commentId);
      const replyData: any = {
        commentId,
        replyContent: replyText.value[commentId],
        userId: store.getters.getUserId,
        replyTo: comment ? comment.username : '',
        userIdOfReplyTo: comment ? comment.userId : '',
      };
      if (props.itemType === 'post') {
        replyData.postId = props.itemId;
        replyData.postName = props.itemName;
      } else if (props.itemType === 'novel') {
        replyData.novelId = props.itemId;
        replyData.novelName = props.itemName;
      } else if (props.itemType === 'chapter') {
        replyData.chapterId = props.itemId;
        replyData.chapterName = props.itemName;
      }
      await props.createReplyApi(replyData);
      replyText.value[commentId] = '';
      fetchReplies(commentId, 1);
    } catch (error) {
      console.error('Failed to submit reply:', error);
    }
  }
};

const toggleShowReplies = (commentId: string) => {
  if (showReplies.value[commentId]) {
    showReplies.value[commentId] = false;
  } else {
    fetchReplies(commentId, 1);
  }
};

const loadMoreReplies = (commentId: string) => {
  const comment = props.comments.find(comment => comment.id === commentId);
  if (comment) {
    fetchReplies(commentId, (comment.replyPage || 1) + 1);
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
      const replyData: any = {
        commentId,
        replyContent: replyText.value[replyId],
        userId: store.getters.getUserId,
        replyTo: reply ? reply.username : '',
        userIdOfReplyTo: reply ? reply.userId : '',
      };
      if (props.itemType === 'post') {
        replyData.postId = props.itemId;
        replyData.postName = props.itemName;
      } else if (props.itemType === 'novel') {
        replyData.novelId = props.itemId;
        replyData.novelName = props.itemName;
      } else if (props.itemType === 'chapter') {
        replyData.chapterId = props.itemId;
        replyData.chapterName = props.itemName;
      }
      await props.createReplyApi(replyData);
      replyText.value[replyId] = '';
      await fetchReplies(commentId, 1);
    } catch (error) {
      console.error('Failed to submit reply:', error);
    }
  }
};
</script>

<template>
  <section class="bg-white py-4 px-6 mx-auto comment-section">
    <h3 class="text-xl font-bold mb-4">Comment</h3>
    <div v-if="isAuthenticated" class="flex flex-col">
      <a-textarea v-model:value="newComment"
                  class="w-full"
                  placeholder="Để lại bình luận..."
                  :autosize="{ minRows: 2, maxRows: 6 }"/>
      <div class="flex justify-end">
        <a-button class="mt-2" type="primary" @click="addComment">
          Gửi bình luận
        </a-button>
      </div>
    </div>
    <ul class="mt-4 space-y-6">
      <li v-for="comment in comments" :key="comment.id" class="border-b pb-4 comment-item">
        <div class="flex items-start space-x-4">
          <img :src="comment.userAvatar" alt="" class="w-10 h-10 bg-gray-300 rounded-full">
          <div class="w-full">
            <p class="text-gray-700 font-semibold">{{ comment.username }}</p>
            <p class="text-gray-600">{{ comment.content }}</p>
            <div class="flex items-center space-x-4 mt-2">
              <a-button class="p-0 text-[#18A058]" type="link" @click="toggleReplyBox(comment.id)">
                <font-awesome-icon :icon="['fas', 'reply']" class="mr-1"/> Trả lời
              </a-button>
              <a-button v-if="comment.replyCount > 0" class="p-0 text-[#18A058]" type="link" @click="toggleShowReplies(comment.id)">
                {{ showReplies[comment.id] ? 'Ẩn trả lời' : `Hiển thị thêm (${comment.replyCount} trả lời)` }}
              </a-button>
            </div>
            <div v-if="replyBoxes[comment.id]" class="mt-2">
                <a-textarea v-model:value="replyText[comment.id]"
                          class="w-full "
                          placeholder="Write your reply..." :autosize="{ minRows: 2, maxRows: 6 }"/>
              <div class="flex justify-end">
                <a-button class="mt-2" type="primary" @click="submitReply(comment.id)">
                  Trả lời
                </a-button>
              </div>
            </div>
            <ul v-if="showReplies[comment.id] && comment.replies" class="mt-4 space-y-4">
              <li v-for="reply in comment.replies" :key="reply.id" class="pl-4 border-l reply-item">
                <div class="flex items-center space-x-4 my-4">
                <img :src="reply.userAvatar" alt="" class="w-10 h-10 bg-gray-300 rounded-full">
                <p class="text-gray-700 font-semibold">{{ reply.username }}</p>
                </div>
                <p class="text-gray-600">
                  <span class="text-[#18A058] italic">@{{ reply.replyTo }}: </span>
                  {{ reply.replyContent }}</p>
                <div class="flex items-center space-x-4 mt-2">
                  <a-button class="p-0 text-[#18A058]" type="link" @click="toggleReplyBoxForReply(reply.id)">
                   <font-awesome-icon :icon="['fas', 'reply']" class="mr-1"/> Trả lời
                  </a-button>
                </div>
                <div v-if="replyBoxes[reply.id]" class="mt-2">
                    <a-textarea v-model:value="replyText[reply.id]"
                              class="w-full"
                                :autosize="{ minRows: 2, maxRows: 6 }"
                              placeholder="Write your reply..."
                    />
                  <div class="flex justify-end">
                    <a-button class="mt-2" type="primary" @click="submitReplyForReply(reply.id, comment.id)">
                      Trả lời
                    </a-button>
                  </div>
                </div>
              </li>
              <li v-if="comment.replies.length < comment.replyCount" class="pl-4 border-l">
                <a-button class="p-0" type="link" @click="loadMoreReplies(comment.id)">
                  Hiển thị thêm ({{ comment.replyCount - comment.replies.length }} )
                </a-button>
              </li>
            </ul>
          </div>
        </div>
      </li>
    </ul>
    <div class="flex justify-center mt-4">
      <a-pagination
          :current="props.currentPage"
          :pageSize="props.pageSize"
          :total="props.totalComments"
          @change="handlePageChange"
      />
    </div>
  </section>
</template>

<style scoped>
.comment-section {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.comment-item {
  border: 1px solid #d0d0d0;
  border-radius: 4px;
  padding: 16px;
  background-color: #ffffff;
}

.reply-item {
  border: 1px solid #c0c0c0;
  border-radius: 4px;
  padding: 12px;
  background-color: #f0f0f0;
}
</style>