<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue';
import {getChapter} from "@/api/chapter";
const props = defineProps<{
  chapterId: string;
}>();

const chapter = reactive({
  title: '',
  content: ``,
  created: '',
});
const fetchChapter = async () => {
  const response = await getChapter(props.chapterId);
  console.log(response);
  chapter.title = response.chapterTitle;
  chapter.content = response.content;
  chapter.created = response.created;
};
onMounted(fetchChapter);

const author = 'Nguyễn Văn A';
const previousChapter = null;
const nextChapter = 'chapter-2';

const wordCount = chapter.content.replace(/<[^>]*>/g, '').split(/\s+/).length;

const comments = reactive<string[]>([
  "This is a very interesting chapter! I can't wait to read more.",
  "I love how the story is developing. Keep up the great work!",
  "The world-building in this chapter is fantastic, I feel immersed!",
]);

const sidebarOpen = ref(false);
const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
};
const navigateToChapter = (chapterId: string | null) => {
  if (chapterId) {
    console.log(`Chuyển tới chương ${chapterId}`);
  }
};
</script>
<template>
  <div class="bg-gray-50 text-gray-800 relative">
    <!-- Nút mở menu -->
    <div
        @click="toggleSidebar"
        class="fixed top-28 right-4 w-10 h-10 bg-green-500 hover:bg-green-600 text-white p-3 rounded-full cursor-pointer shadow-lg z-50 flex items-center justify-center"
    >
      <font-awesome-icon :icon="sidebarOpen ? ['fas', 'xmark'] : ['fas', 'feather-pointed']" />
    </div>

    <!-- Menu dạng tròn xổ dọc -->
    <div v-if="sidebarOpen" class="fixed top-40 right-4 flex flex-col justify-center items-center space-y-4 z-50">
      <button class="w-10 h-10 bg-green-600 hover:bg-green-700 text-white rounded-full flex items-center justify-center shadow-lg">
        <font-awesome-icon :icon="['fas', 'cog']" />
      </button>
      <button class="w-10 h-10 bg-green-600 hover:bg-green-700 text-white rounded-full flex items-center justify-center shadow-lg">
        <font-awesome-icon :icon="['fas', 'bookmark']" />
      </button>
      <button class="w-10 h-10 bg-green-600 hover:bg-green-700 text-white rounded-full flex items-center justify-center shadow-lg">
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
          <p class="text-gray-600 text-sm">Updated: {{ chapter.created }}</p>
        </div>
      </div>

      <div class="prose prose-blue mx-auto max-w-none" v-html="chapter.content"></div>

      <div class="flex justify-between mt-8">
        <button
            @click="navigateToChapter(previousChapter)"
            :class="[
            'w-28 h-10 rounded text-sm flex items-center justify-center',
            previousChapter ? 'bg-green-600 hover:bg-green-700 text-gray-700' : 'bg-gray-200 text-gray-400 cursor-not-allowed',
          ]"
            :disabled="!previousChapter"
        >
          Previous Chapter
        </button>
        <button
            @click="navigateToChapter(nextChapter)"
            :class="[
            'w-28 h-10 rounded text-sm flex items-center justify-center',
            nextChapter ? 'bg-green-600 hover:bg-green-700 text-gray-700' : 'bg-gray-200 text-gray-400 cursor-not-allowed',
          ]"
            :disabled="!nextChapter"
        >
          Next Chapter
        </button>
      </div>
    </main>
  </div>
</template>

