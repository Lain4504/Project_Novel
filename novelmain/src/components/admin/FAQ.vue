<template>
    <div id="accordion-collapse" data-accordion="collapse" class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <!-- Loop through FAQ sections -->
      <div v-for="(item, index) in faqItems" :key="index">
        <h2 :id="'accordion-collapse-heading-' + index">
          <div>{{ item.title }}</div>
        </h2>
  
        <!-- Loop through each question within a section -->
        <div v-for="(qa, qaIndex) in item.questions" :key="qaIndex">
          <button
            type="button"
            class="flex items-center justify-between w-full p-5 font-medium text-gray-500 border border-b-0 border-gray-200 rounded-t-xl focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-800 dark:border-gray-700 dark:text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-800 gap-3"
            @click="toggleAccordion(index, qaIndex)"
            :aria-expanded="isExpanded(index, qaIndex).toString()"
            :aria-controls="'accordion-collapse-body-' + index + '-' + qaIndex"
          >
            <span>{{ qa.question }}</span>
            <font-awesome-icon :icon="['fas', 'chevron-down']" />
          </button>
  
          <!-- Answer section that toggles on click -->
          <div
            v-show="isExpanded(index, qaIndex)"
            :id="'accordion-collapse-body-' + index + '-' + qaIndex"
            class="p-5 border border-b-0 border-gray-200 dark:border-gray-700 dark:bg-gray-900"
          >
            <div class="text-gray-500 dark:text-gray-400">
              {{ qa.answer }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
  
  // Fake data for the FAQ with multiple questions per section
  const faqItems = ref([
    {
      title: 'Cài đặt giao diện sáng - tối',
      questions: [
        {
          question: 'Hướng dẫn cài đặt',
          answer: 'Để cài đặt giao diện sáng - tối, bạn có thể sử dụng tùy chọn trong phần cài đặt của website.',
        },
        {
          question: 'Cài đặt tự động chuyển giao diện',
          answer: 'Bạn có thể bật chế độ tự động chuyển giao diện theo thời gian trong phần cài đặt.',
        }
      ],
    },
    {
      title: 'Thay đổi màu nền, cỡ chữ, font chữ khi đọc truyện',
      questions: [
        {
          question: 'Cách thay đổi giao diện đọc truyện',
          answer: 'Bạn có thể thay đổi cỡ chữ và font chữ trong phần cài đặt để có trải nghiệm đọc tốt hơn.',
        },
        {
          question: 'Cách thay đổi màu nền khi đọc',
          answer: 'Bạn có thể chọn màu nền sáng hoặc tối tùy vào sở thích.',
        }
      ],
    },
  ]);
  
  const expanded = ref<{ [key: string]: boolean }>({});
  
  // Toggle the accordion for a specific question in a specific section
  const toggleAccordion = (sectionId: number, questionId: number) => {
    const key = `${sectionId}-${questionId}`;
    expanded.value[key] = !expanded.value[key];
  };
  
  // Check if a specific question in a section is expanded
  const isExpanded = (sectionId: number, questionId: number) => {
    const key = `${sectionId}-${questionId}`;
    return !!expanded.value[key];
  };
  </script>
  
  <style scoped>
  /* Add any custom styles here */
  </style>
  