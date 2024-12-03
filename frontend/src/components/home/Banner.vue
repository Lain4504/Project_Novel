<script setup lang="ts">
import { ref } from 'vue';

interface GameAd {
  id: number;
  title: string;
  subtitle: string;
  backgroundImage: string;
  logo?: string;
  className?: string;
}

// Sample data
const gameAds = ref<GameAd[]>([
  {
    id: 1,
    title: "TRỞ LẠI 1982",
    subtitle: "LÀNG CHÀI NHỎ",
    backgroundImage: "/path-to-banner-bg.jpg",
    logo: "/path-to-game-logo.png",
    className: "font-game" // Custom class for game-style font
  }
]);

// Optional: Add animation state
const isHovered = ref(false);
</script>

<template>
  <div class="w-full">
    <!-- Game Ad Banner Container -->
    <div 
      v-for="ad in gameAds" 
      :key="ad.id"
      class="relative w-full h-[300px] overflow-hidden rounded-lg cursor-pointer group"
      @mouseenter="isHovered = true"
      @mouseleave="isHovered = false"
    >
      <!-- Background Image -->
      <img 
        :src="ad.backgroundImage"
        :alt="ad.title"
        class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-105"
      />

      <!-- Overlay Content -->
      <div class="absolute inset-0 flex flex-col justify-center items-center">
        <!-- Game Logo if exists -->
        <img 
          v-if="ad.logo"
          :src="ad.logo"
          alt="Game Logo"
          class="h-16 md:h-20 object-contain absolute top-4 right-4"
        />

        <!-- Title Text with Game-style Font -->
        <div 
          class="text-center transform"
          :class="[
            ad.className,
            'space-y-2'
          ]"
        >
          <!-- Main Title with 3D Effect -->
          <h1 
            class="text-4xl md:text-6xl font-bold text-white tracking-wider"
            style="text-shadow: 
              2px 2px 0 #000,
              -2px -2px 0 #000,
              2px -2px 0 #000,
              -2px 2px 0 #000,
              0 2px 0 #000,
              2px 0 0 #000,
              0 -2px 0 #000,
              -2px 0 0 #000;
            "
          >
            {{ ad.title }}
          </h1>

          <!-- Subtitle -->
          <h2 
            class="text-3xl md:text-5xl font-bold text-white tracking-wide"
            style="text-shadow: 
              2px 2px 0 #000,
              -2px -2px 0 #000,
              2px -2px 0 #000,
              -2px 2px 0 #000;
            "
          >
            {{ ad.subtitle }}
          </h2>
        </div>

        <!-- Optional: Floating Effects -->
        <div class="absolute inset-0 bg-gradient-to-t from-black/30 to-transparent pointer-events-none"></div>
        
        <!-- Sparkle Effects -->
        <div class="absolute inset-0 overflow-hidden pointer-events-none">
          <div 
            v-for="i in 5" 
            :key="i"
            class="absolute w-1 h-1 bg-white rounded-full animate-ping"
            :style="{
              left: `${Math.random() * 100}%`,
              top: `${Math.random() * 100}%`,
              animationDelay: `${i * 0.5}s`
            }"
          ></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Custom Animation for Floating Effect */
@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

/* Optional: Add custom font for game-style text */
@font-face {
  font-family: 'GameFont';
  src: url('/path-to-your-game-font.ttf') format('truetype');
}

.font-game {
  font-family: 'GameFont', sans-serif;
}

/* Custom Animation Classes */
.animate-float {
  animation: float 3s ease-in-out infinite;
}

/* Custom Sparkle Animation */
@keyframes sparkle {
  0%, 100% { opacity: 0; transform: scale(0); }
  50% { opacity: 1; transform: scale(1); }
}

.animate-sparkle {
  animation: sparkle 2s ease-in-out infinite;
}
</style>