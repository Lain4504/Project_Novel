<script lang="ts" setup>
import {onMounted, ref} from 'vue';

interface BannerItem {
  id: number;
  title: string;
  subtitle: string;
  category: string;
  description: string;
  coverImage: string;
  status?: string;
}

const currentSlide = ref(0);
const banners = ref<BannerItem[]>([
  {
    id: 1,
    title: 'Vda À!',
    subtitle: 'Anh ĐasdĐây',
    category: 'tiểuad cam',
    description: 'Nghad badsbị kịch rồi',
    coverImage: '/src/assets/logo.jpg',
    status: 'Còn tiếp'
  },
  {
    id: 1,
    title: 'daaaaaaaaaaaaaaaaaaaaaaaa!',
    subtitle: 'adddddddddddd',
    category: 'tiểaddddddd cam',
    description: 'Ngdaddsch bệd kịch dsadss',
    coverImage: '/banner1.jpg',
    status: 'Còn tiếp'
  },
  // Add more banner items as needed
]);

const autoPlay = ref(true);
const slideInterval = ref<number | null>(null);

const nextSlide = () => {
  currentSlide.value = (currentSlide.value + 1) % banners.value.length;
};

const prevSlide = () => {
  currentSlide.value = currentSlide.value === 0
      ? banners.value.length - 1
      : currentSlide.value - 1;
};

const startAutoPlay = () => {
  if (autoPlay.value && !slideInterval.value) {
    slideInterval.value = window.setInterval(nextSlide, 5000);
  }
};

onMounted(() => {
  startAutoPlay();
});
</script>

<template>
  <div class="relative w-full h-[400px] overflow-hidden">
    <!-- Banner Slides -->
    <div class="relative w-full h-full">
      <div
          v-for="(banner, index) in banners"
          :key="banner.id"
          :class="{ 'opacity-0': currentSlide !== index }"
          class="absolute w-full h-full transition-opacity duration-500"
      >
        <!-- Banner Content -->
        <div class="relative w-full h-full">
          <!-- Background Image -->
          <img
              :alt="banner.title"
              :src="banner.coverImage"
              class="w-full h-full object-cover"
          />

          <!-- Gradient Overlay -->
          <div class="absolute inset-0 bg-gradient-to-r from-black/60 to-transparent"></div>

          <!-- Content -->
          <div class="absolute inset-0 flex items-center p-8 md:p-16">
            <div class="max-w-2xl text-white">
              <!-- Status Badge -->
              <div
                  v-if="banner.status"
                  class="inline-block px-4 py-1 mb-4 text-sm bg-amber-600 rounded-full"
              >
                {{ banner.status }}
              </div>

              <!-- Title -->
              <h2 class="text-3xl md:text-4xl font-bold mb-2">
                {{ banner.title }}
              </h2>

              <!-- Subtitle -->
              <h3 class="text-xl md:text-2xl font-semibold mb-2 text-green-400 italic">
                {{ banner.subtitle }}
              </h3>

              <!-- Category -->
              <div class="text-sm text-gray-300 mb-4">
                - {{ banner.category }} -
              </div>

              <!-- Description -->
              <p class="text-sm md:text-base text-gray-200">
                {{ banner.description }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Navigation Dots -->
    <div class="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2">
      <button
          v-for="(_, index) in banners"
          :key="index"
          :class="currentSlide === index ? 'bg-white w-4' : 'bg-white/50'"
          class="w-2 h-2 rounded-full transition-all duration-300"
          @click="currentSlide = index"
      ></button>
    </div>

    <!-- Navigation Arrows -->
    <button
        class="absolute left-4 top-1/2 -translate-y-1/2 p-4 h-10 w-10 rounded-full bg-black/30 text-white hover:bg-black/50 transition-colors flex items-center justify-center"
        @click="prevSlide"
    >
      <font-awesome-icon icon="fa-solid fa-chevron-left" size="lg"/>
    </button>

    <button
        class="absolute right-4 top-1/2 -translate-y-1/2 p-4 h-10 w-10 rounded-full bg-black/30 text-white hover:bg-black/50 transition-colors flex items-center justify-center"
        @click="nextSlide"
    >
      <font-awesome-icon icon="fa-solid fa-chevron-right" size="lg"/>
    </button>
  </div>
</template>