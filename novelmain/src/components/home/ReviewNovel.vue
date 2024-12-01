<template>
    <section>
      <h3 class="text-xl font-bold mb-4">Chấm điểm nội dung truyện</h3>
      <div class="flex flex-col space-y-4">
        <label>
          <input type="checkbox" v-model="onlyScore" />
          Tôi chỉ muốn chấm điểm (không viết đánh giá)
        </label>
        
        <div v-if="!onlyScore">
          <textarea
            v-model="reviewContent"
            class="w-full p-4 border rounded"
            placeholder="Nội dung bài đánh giá (ít nhất 100 từ)"
          ></textarea>
        </div>
        
        <button
          @click="submitReview"
          class="px-4 py-2 bg-blue-500 text-white rounded"
        >
          Gửi đánh giá
        </button>
      </div>
    </section>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
  
  const onlyScore = ref(false);
  const reviewContent = ref('');
  
  const submitReview = () => {
    console.log('Đánh giá:', onlyScore.value ? 'Chỉ chấm điểm' : reviewContent.value);
  };
  </script>
  