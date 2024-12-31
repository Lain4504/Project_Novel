<template>
  <a-carousel :autoplay="true" :dots="false" class="my-3">
    <div v-for="ad in ads" :key="ad.id" class="relative w-full h-[200px] overflow-hidden rounded-lg cursor-pointer group">
      <img :src="ad.imageUrl" :alt="ad.title" class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-105" />
      <div class="absolute inset-0 flex flex-col justify-center items-center">
        <div class="text-center transform">
          <h1 class="text-2xl md:text-4xl font-bold text-white tracking-wider" style="text-shadow: 2px 2px 0 #000, -2px -2px 0 #000, 2px -2px 0 #000, -2px 2px 0 #000, 0 2px 0 #000, 2px 0 0 #000, 0 -2px 0 #000, -2px 0 0 #000;">
            {{ ad.title }}
          </h1>
          <h2 v-html="ad.description" class="text-xl md:text-3xl font-bold text-white tracking-wide" style="text-shadow: 2px 2px 0 #000, -2px -2px 0 #000, 2px -2px 0 #000, -2px 2px 0 #000;"/>
        </div>
        <div class="absolute inset-0 bg-gradient-to-t from-black/30 to-transparent pointer-events-none"></div>
        <div class="absolute inset-0 overflow-hidden pointer-events-none">
          <div v-for="i in 5" :key="i" :style="{ left: `${Math.random() * 100}%`, top: `${Math.random() * 100}%`, animationDelay: `${i * 0.5}s` }" class="absolute w-1 h-1 bg-white rounded-full animate-ping"></div>
        </div>
      </div>
    </div>
  </a-carousel>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { getAllAds } from '@/api/resource';

interface Ad {
  id: string;
  imageUrl: string;
  title: string;
  description: string;
  createdAt: string;
}
const ads = ref<Ad[]>([]);

const fetchAds = async () => {
  try {
    const adsData = await getAllAds(1, 10); // Adjust page and size as needed
    ads.value = adsData.data;
  } catch (error) {
    console.error('Error fetching ads:', error);
  }
};

onMounted(() => {
  fetchAds();
});
</script>

<style scoped>
@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

@keyframes sparkle {
  0%, 100% {
    opacity: 0;
    transform: scale(0);
  }
  50% {
    opacity: 1;
    transform: scale(1);
  }
}

.animate-sparkle {
  animation: sparkle 2s ease-in-out infinite;
}
</style>