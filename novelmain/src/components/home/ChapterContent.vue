<template>
  <div class="min-h-screen bg-gray-50 text-gray-800 relative">
    <!-- Icon setting luôn nổi -->
    <div @click="toggleSidebar"
      class="fixed top-28 right-4 w-10 h-10 bg-[#98a77c] hover:bg-[#88976c] text-white p-3 rounded-full cursor-pointer shadow-lg z-5 flex items-center justify-center">
      <font-awesome-icon :icon="['fas', 'feather-pointed']" />
        </div>

    <!-- Sidebar menu -->
    <div :class="{ 'block': sidebarOpen, 'hidden': !sidebarOpen }" class="fixed inset-0 bg-gray-900 bg-opacity-50 z-50">
      <div class="absolute top-0 left-0 w-40 bg-white h-full shadow-lg p-4">
        <button @click="toggleSidebar" class="text-gray-700 text-xl font-bold mb-6">
         <font-awesome-icon :icon="['fas', 'xmark']" />
        </button>
        <h3 class="text-md font-bold mb-4">Utilities</h3>
        <ul class="text-sm">
          <li class="mb-2">
            <RouterLink to="#" class="text-gray-600 hover:text-blue-500">Settings</RouterLink>
          </li>
          <li class="mb-2">
            <RouterLink to="#" class="text-gray-600 hover:text-blue-500">Mark</RouterLink>
          </li>
          <li class="mb-2">
            <RouterLink to="#" class="text-gray-600 hover:text-blue-500">Chapter List</RouterLink>
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
      <Comment />
    </main>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import Comment from './Comment.vue';
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
