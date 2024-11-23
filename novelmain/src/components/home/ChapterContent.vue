<template>
  <div class="min-h-screen bg-gray-50 text-gray-800 relative">
    <!-- Icon setting luôn nổi -->
    <div @click="toggleSidebar"
      class="fixed top-28 right-4 bg-[#98a77c] hover:bg-[#88976c] text-white p-3 rounded-full cursor-pointer shadow-lg z-5">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" class="h-4 w-4">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
          d="M12 3v2m0 4v10m4-6h2m-4 4h2m-4 4h2m-4-4H6m-2 0h2m0-4H6m0-4h2" />
      </svg>
    </div>

    <!-- Sidebar menu -->
    <div :class="{ 'block': sidebarOpen, 'hidden': !sidebarOpen }" class="fixed inset-0 bg-gray-900 bg-opacity-50 z-50">
      <div class="absolute top-0 left-0 w-40 bg-white h-full shadow-lg p-4">
        <button @click="toggleSidebar" class="text-gray-700 text-xl font-bold mb-6">
          <CloseOutlined style="font-size: 16px;" />
        </button>
        <h3 class="text-md font-bold mb-4">Utilities</h3>
        <ul class="text-sm">
          <li class="mb-2">
            <a href="#" class="text-gray-600 hover:text-blue-500">Settings</a>
          </li>
          <li class="mb-2">
            <a href="#" class="text-gray-600 hover:text-blue-500">Mark</a>
          </li>
          <li class="mb-2">
            <a href="#" class="text-gray-600 hover:text-blue-500">Chapter List</a>
          </li>
        </ul>
      </div>
    </div>

    <!-- Nội dung chương -->
    <main class="container mx-auto px-4 py-6 max-w-5xl">
      <!-- Thông tin chương -->
      <div class="text-center mb-6">
        <h2 class="text-2xl font-semibold mb-2">{{ chapter.title }}</h2>
        <p class="text-gray-600 text-sm">Author: {{ author }}</p>
        <div class="flex justify-center space-x-4 mt-2">
          <p class="text-gray-600 text-sm">Comment: {{ comments.length }}</p>
          <p class="text-gray-600 text-sm">Word: {{ wordCount }}</p>
          <p class="text-gray-600 text-sm">Updated: {{ updatedAt }}</p>
        </div>
      </div>

      <div class="prose prose-blue mx-auto max-w-none" v-html="chapter.content"></div>

      <div class="flex justify-between mt-8">
        <button @click="navigateToChapter(previousChapter)" :class="{
          'w-[120px] h-[40px] bg-[#88976c] hover:bg-[#7a8c60] text-gray-700 rounded text-sm': previousChapter,
          'w-[120px] h-[40px] bg-[#d0d6b6] text-gray-400 cursor-not-allowed rounded text-sm': !previousChapter
        }" :disabled="!previousChapter">
          Previous Chapter
        </button>
        <button @click="navigateToChapter(nextChapter)" :class="{
          'w-[120px] h-[40px] bg-[#88976c] hover:bg-[#7a8c60] text-gray-700 rounded text-sm': nextChapter,
          'w-[120px] h-[40px] bg-[#d0d6b6] text-gray-400 cursor-not-allowed rounded text-sm': !nextChapter
        }" :disabled="!nextChapter">
          Next Chapter
        </button>
      </div>

    </main>

    <section class="bg-gray-50 mt-6 py-4 px-6 max-w-5xl mx-auto">
      <h3 class="text-xl font-bold mb-4">Comment</h3>
      <div class="flex flex-col">
  <textarea v-model="newComment" placeholder="Để lại bình luận..."
    class="w-full border border-gray-300 rounded p-2 mb-2"></textarea>
  <div class="flex justify-end">
    <button @click="addComment"
      class="px-5 py-2 bg-[#98a77c] hover:bg-[#88976c] text-white rounded text-sm">
      Send
    </button>
  </div>
</div>


<ul class="mt-4 space-y-6">
  <li v-for="(comment, index) in comments" :key="index" class="border-b pb-4">
    <div class="flex items-start space-x-4">
      <!-- Avatar -->
      <div class="w-10 h-10 bg-gray-300 rounded-full"></div>

      <div class="w-full">
        <p class="text-gray-700 font-semibold">User {{ index + 1 }}</p>
        <p class="text-gray-600">{{ comment }}</p>

        <!-- Like and Reply buttons -->
        <div class="flex items-center space-x-4 mt-2">
          <button @click="likeComment(index)"
            class="flex items-center text-gray-500 hover:text-blue-500 text-sm space-x-1">
            <LikeOutlined style="font-size: 14px;" />
            <span>Like</span>
          </button>

          <button @click="toggleReplyBox(index)" class="text-gray-500 hover:text-blue-500 text-sm">
            💬 Reply
          </button>
        </div>

        <!-- Reply Box -->
        <div v-if="replyBoxes[index]" class="mt-2">
          <textarea v-model="replyText[index]" 
            class="w-full border border-gray-300 rounded p-2 mb-2"
            placeholder="Write your reply..."></textarea>
            <div class="flex justify-end"> 
          <button @click="submitReply(index)" 
            class="px-4 py-2 bg-[#98a77c] hover:bg-[#88976c] text-white rounded text-sm">
             Reply
          </button>
        </div>
        </div>
      </div>
    </div>
  </li>
</ul>

    </section>

  </div>
</template>

<script setup lang="ts">
import { CloseOutlined, LikeOutlined } from '@ant-design/icons-vue';
import { reactive, ref } from 'vue';

const chapter = reactive({
  title: 'Chương 1: Khởi đầu',
  content: `
    <p>Hoàng gia tàng thư chỗ Văn Uyên Các bên trong dù người đến người đi, nhưng không có chút nào bất kỳ huyên náo cảm giác.</p>
    <p>Nơi hẻo lánh một trương trên thư án, một vị đầu đội lụa đen, thân mang màu xanh quan phục, trước ngực vá thêu lên chim uyên ương thanh niên quan viên chính đang cúi đầu múa bút thành văn.</p>
  `,
});

const author = 'Nguyễn Văn A';
const previousChapter = null;
const nextChapter = 'chapter-2';
const updatedAt = '23/11/2024';

const wordCount = chapter.content.replace(/<[^>]*>/g, '').split(/\s+/).length;

// Fake comments for testing
const comments = reactive<string[]>([
  "This is a very interesting chapter! I can't wait to read more.",
  "I love how the story is developing. Keep up the great work!",
  "The world-building in this chapter is fantastic, I feel immersed!",
]);

const newComment = ref('');

const addComment = () => {
  if (newComment.value.trim()) {
    comments.push(newComment.value);
    newComment.value = '';
  }
};

const navigateToChapter = (chapterId: string | null) => {
  if (chapterId) {
    console.log(`Chuyển tới chương ${chapterId}`);
  }
};

const sidebarOpen = ref(false);
const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
};
const likeComment = (index: number) => {
  console.log(`Liked comment ${index + 1}`);
};

// To track reply box visibility
const replyBoxes = ref<boolean[]>([]);

// To store reply text for each comment
const replyText = ref<string[]>([]);

// Toggle reply box visibility
const toggleReplyBox = (index: number): void => {
  replyBoxes.value[index] = !replyBoxes.value[index];
};

// Submit reply
const submitReply = (index: number): void => {
};

</script>

<style scoped>
.prose {
  line-height: 1.75;
}

ul {
  padding-left: 1.5rem;
}

li {
  border-bottom: 1px solid #ddd;
  padding-bottom: 1rem;
}
</style>
