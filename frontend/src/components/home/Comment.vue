<template>
  <div class=" bg-gray-50 "> 
    <section class="bg-gray-50  py-4 px-6 max-w-5xl mx-auto">
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
                  👍 Like
                </button>
  
                <button @click="toggleReplyBox(index)" class="text-gray-500 hover:text-blue-500 text-sm">
                  💬 Reply
                </button>
              </div>
  
              <!-- Reply Box -->
              <div v-if="replyBoxes[index]" class="mt-2">
                <textarea v-model="replyText[index]" 
                class="w-full p-4 text-lg border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm"                  placeholder="Write your reply..."></textarea>
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
    </section></div>
  </template>
  
  <script setup lang="ts">
  import { ref, reactive } from 'vue';
  
  // Comments logic
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
  
  const likeComment = (index: number) => {
    console.log(`Liked comment ${index + 1}`);
  };
  
  // Reply logic
  const replyBoxes = ref<boolean[]>([]);
  const replyText = ref<string[]>([]);
  const toggleReplyBox = (index: number): void => {
    replyBoxes.value[index] = !replyBoxes.value[index];
  };
  const submitReply = (index: number): void => {
    console.log(`Reply to comment ${index + 1}: ${replyText.value[index]}`);
    replyText.value[index] = '';
  };
  </script>
  
  <style scoped>
  ul {
    padding-left: 1.5rem;
  }
  
  li {
    border-bottom: 1px solid #ddd;
    padding-bottom: 1rem;
  }
  </style>
  