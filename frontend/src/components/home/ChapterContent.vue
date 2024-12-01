<template>
  <div class="bg-gray-50 text-gray-800 relative">
    <!-- Nút mở menu -->
    <div @click="toggleSidebar"
      class="fixed top-28 right-4 w-10 h-10 bg-[#98a77c] hover:bg-[#88976c] text-white p-3 rounded-full cursor-pointer shadow-lg z-50 flex items-center justify-center">
      <font-awesome-icon :icon="sidebarOpen ? ['fas', 'xmark'] : ['fas', 'feather-pointed']" />
    </div>

    <!-- Menu dạng tròn xổ dọc -->
    <div v-if="sidebarOpen" class="fixed top-40 right-4 flex flex-col justify-center items-center space-y-4 z-50">
      <!-- Icons stacked vertically when sidebar is open -->
      <button class="w-10 h-10 bg-[#88976c] hover:bg-[#7a8c60] text-white rounded-full flex items-center justify-center shadow-lg">
        <font-awesome-icon :icon="['fas', 'cog']" />
      </button>
      <button class="w-10 h-10 bg-[#88976c] hover:bg-[#7a8c60] text-white rounded-full flex items-center justify-center shadow-lg">
        <font-awesome-icon :icon="['fas', 'bookmark']" />
      </button>
      <button class="w-10 h-10 bg-[#88976c] hover:bg-[#7a8c60] text-white rounded-full flex items-center justify-center shadow-lg">
        <font-awesome-icon :icon="['fas', 'list']" />
      </button>
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
  </div>
</template>

<script setup lang="ts">
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
const navigateToChapter = (chapterId: string | null) => {
  if (chapterId) {
    console.log(`Chuyển tới chương ${chapterId}`);
  }
};

const sidebarOpen = ref(false);

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
  console.log(sidebarOpen.value);  // Debugging log
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

/* Thêm hiệu ứng mượt mà */
ul li a {
  transition: all 0.2s ease;
}
</style>
