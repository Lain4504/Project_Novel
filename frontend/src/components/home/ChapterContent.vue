<script lang="ts" setup>
import { defineEmits, defineProps, ref, onMounted, onUnmounted } from 'vue';
import { addBookmark } from '@/api/user';
import store from "@/store";
import { Button } from 'ant-design-vue';

const props = defineProps<{
  chapter: {
    id: string;
    title: string;
    content: string;
    created: string;
    chapterNumber: string;
    volumeId: string;
    wordCount: number;
  };
  novel: {
    id: string;
    authorName: string;
    title: string;
  };
}>();

const emits = defineEmits(['previous-chapter', 'next-chapter']);
const bookmarkPosition = ref({ top: 0, left: 0 });
const bookmarkVisible = ref(false);
const selectedText = ref('');

const handleContentClick = (event: MouseEvent | TouchEvent) => {
  const target = event.target as HTMLElement;
  if (target.tagName === 'P') {
    const rect = target.getBoundingClientRect();
    bookmarkPosition.value = { top: rect.top + window.scrollY, left: rect.right + window.scrollX };
    bookmarkVisible.value = true;
    selectedText.value = target.textContent || '';
  } else {
    bookmarkVisible.value = false;
    selectedText.value = '';
  }
};

const handleAddBookmark = async () => {
  try {
    const data = {
      userId: store.getters.getUserId,
      novelId: props.novel.id,
      novelTitle: props.novel.title,
      novelChapterId: props.chapter.id,
      novelChapterTitle: props.chapter.title,
      contentNote: selectedText.value,
    };
    console.log('Bookmark added successfully', data);
    await addBookmark(data); // Save to backend
    bookmarkVisible.value = false;
  } catch (error) {
    console.error('Failed to add bookmark:', error);
  }
};

const handleResize = () => {
  bookmarkVisible.value = false;
};

onMounted(() => {
  const contentElement = document.querySelector('.content');
  if (contentElement) {
    contentElement.addEventListener('click', handleContentClick);
  }
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
});
</script>

<template>
  <div class="bg-gray-50 text-gray-800 relative content">
    <div v-if="bookmarkVisible" :style="{ top: `${bookmarkPosition.top}px`, left: `${bookmarkPosition.left}px` }" class="absolute z-50 bookmark-button">
      <button
        class="text-black"
        @click.stop="handleAddBookmark"
      >
        <font-awesome-icon :icon="['fas', 'bookmark']" size="lg" />
      </button>
    </div>
    <main class="container mx-auto px-4 py-6 max-w-5xl">
      <div class="text-center mb-6">
        <h2 class="text-2xl font-semibold mb-2">
          {{ chapter.title }}
        </h2>
        <div class="flex items-center space-x-2 justify-center">
          <p class="text-gray-600 text-sm font-bold">{{ novel.title }}</p>
          <p class="text-gray-600 text-sm">-</p>
          <p class="text-gray-600 text-sm font-light italic">{{ novel.authorName }}</p>
        </div>
        <div class="flex justify-center space-x-4">
          <p class="text-gray-600 text-sm">Word: {{ chapter.wordCount }}</p>
          <p class="text-gray-600 text-sm">Created: {{ chapter.created }}</p>
        </div>
      </div>
      <div class="prose prose-blue mx-auto max-w-none" v-html="chapter.content"></div>
      <div class="flex justify-between mt-8">
        <Button type="primary" @click="$emit('previous-chapter')">Previous Chapter</Button>
        <Button type="primary" @click="$emit('next-chapter')">Next Chapter</Button>
      </div>
    </main>
  </div>
</template>

<style scoped>
.bookmark-button {
  transform: translate(-50%, -300%);
  transition: transform 0.3s ease-in-out;
}

@media (max-width: 1024px) {
  .bookmark-button {
    transform: translate(-50%, -275%);
  }
}

@media (max-width: 768px) {
  .bookmark-button {
    transform: translate(-50%, -250%);
  }
}

@media (max-width: 480px) {
  .bookmark-button {
    transform: translate(-50%, -225%);
  }
}
</style>