<script lang="ts" setup>
import Ads from '@/components/home/Banner.vue';

// Fake data for the FAQ with multiple questions per section
const faqItems = [
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
      },
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
      },
    ],
  },
];
</script>

<template>
  <section class="container mx-auto p-6">
    <Ads class="my-4"/>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <div v-for="(item, index) in faqItems" :key="index" class="faq-column">
        <h2 class="font-bold text-lg mb-2">{{ item.title }}</h2>
        <a-collapse accordion class="faq-accordion">
          <a-collapse-panel
              v-for="(qa, qaIndex) in item.questions"
              :key="qaIndex"
              :header="qa.question"
          >
            <p>{{ qa.answer }}</p>
          </a-collapse-panel>
        </a-collapse>
      </div>
    </div>
  </section>
</template>

<style scoped>
.faq-accordion .ant-collapse-header {
  font-weight: bold;
}
</style>
