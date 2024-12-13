<script setup lang="ts">
import { defineProps, ref, defineEmits } from 'vue';

const props = defineProps<{
  chapter: {
    title: string;
    content: string;
    created: string;
    chapterNumber: string;
    volumeId: string;
  };
}>();

const emits = defineEmits(['previous-chapter', 'next-chapter']);

const author = 'Nguyễn Văn A';
const previousChapter = 'chapter-0';
const nextChapter = 'chapter-2';

const wordCount = props.chapter.content.replace(/<[^>]*>/g, '').split(/\s+/).length;

const comments = ref<string[]>([
  "This is a very interesting chapter! I can't wait to read more.",
  "I love how the story is developing. Keep up the great work!",
  "The world-building in this chapter is fantastic, I feel immersed!",
]);

const sidebarOpen = ref(false);
const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
};
</script>

<template>
  <div class="bg-gray-50 text-gray-800 relative">
    <div class="fixed top-28 right-4 z-50">
      <div
        @click="toggleSidebar"
        class="w-12 h-12 bg-gradient-to-br from-emerald-500 to-emerald-600 hover:from-emerald-600 hover:to-emerald-700 text-white rounded-full cursor-pointer shadow-xl transition-all duration-300 ease-in-out transform hover:scale-105 active:scale-95 flex items-center justify-center"
      >
        <font-awesome-icon
          :icon="sidebarOpen ? ['fas', 'xmark'] : ['fas', 'feather-pointed']"
          class="text-lg"
        />
      </div>
      <transition
        enter-active-class="transition ease-out duration-300"
        enter-from-class="opacity-0 translate-y-2"
        enter-to-class="opacity-100 translate-y-0"
        leave-active-class="transition ease-in duration-200"
        leave-from-class="opacity-100 translate-y-0"
        leave-to-class="opacity-0 translate-y-2"
      >
        <div
          v-if="sidebarOpen"
          class="absolute top-16 right-0 flex flex-col space-y-3 w-12"
        >
          <button
            class="w-12 h-12 bg-gradient-to-br from-sky-500 to-sky-600 hover:from-sky-600 hover:to-sky-700 text-white rounded-full shadow-lg transition-all duration-300 ease-in-out transform hover:scale-105 active:scale-95 flex items-center justify-center"
          >
            <font-awesome-icon :icon="['fas', 'cog']" class="text-base" />
          </button>
          <button
            class="w-12 h-12 bg-gradient-to-br from-purple-500 to-purple-600 hover:from-purple-600 hover:to-purple-700 text-white rounded-full shadow-lg transition-all duration-300 ease-in-out transform hover:scale-105 active:scale-95 flex items-center justify-center"
          >
            <font-awesome-icon :icon="['fas', 'bookmark']" class="text-base" />
          </button>
          <button
            class="w-12 h-12 bg-gradient-to-br from-pink-500 to-pink-600 hover:from-pink-600 hover:to-pink-700 text-white rounded-full shadow-lg transition-all duration-300 ease-in-out transform hover:scale-105 active:scale-95 flex items-center justify-center"
          >
            <font-awesome-icon :icon="['fas', 'list']" class="text-base" />
          </button>
        </div>
      </transition>
    </div>
    <main class="container mx-auto px-4 py-6 max-w-5xl">
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
          @click="$emit('previous-chapter')"
          :class="[
            'px-4 py-2 rounded-lg text-sm font-medium transition-all duration-300 ease-in-out',
            previousChapter
              ? 'bg-sky-500 text-white hover:bg-sky-600 shadow-md hover:shadow-lg active:scale-95 focus:outline-none focus:ring-2 focus:ring-sky-500 focus:ring-opacity-50'
              : 'bg-gray-300 text-gray-500 cursor-not-allowed opacity-60'
          ]"
          :disabled="!previousChapter"
        >
          Previous Chapter
        </button>
        <button
          @click="$emit('next-chapter')"
          :class="[
            'px-4 py-2 rounded-lg text-sm font-medium transition-all duration-300 ease-in-out',
            nextChapter
              ? 'bg-sky-500 text-white hover:bg-sky-600 shadow-md hover:shadow-lg active:scale-95 focus:outline-none focus:ring-2 focus:ring-sky-500 focus:ring-opacity-50'
              : 'bg-gray-300 text-gray-500 cursor-not-allowed opacity-60'
          ]"
          :disabled="!nextChapter"
        >
          Next Chapter
        </button>
      </div>
    </main>
  </div>
</template>