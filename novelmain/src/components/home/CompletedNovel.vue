<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue';

interface Novel {
  id: number;
  title: string;
  imageUrl: string;
}

const currentPage = ref(0);

const getItemsPerPage = () => {
  const width = window.innerWidth;
  if (width >= 1024) return 6;  // lg: 1024px and up
  if (width >= 768) return 4;   // md: 768px - 1023px
  if (width >= 640) return 3;   // sm: 640px - 767px
  return 2;                     // xs: below 640px
};

const itemsPerPage = ref(getItemsPerPage());

// Update items per page on window resize
const handleResize = () => {
  const newItemsPerPage = getItemsPerPage();
  if (newItemsPerPage !== itemsPerPage.value) {
    itemsPerPage.value = newItemsPerPage;
    // Adjust current page if necessary
    const maxPage = Math.ceil(novels.length / newItemsPerPage) - 1;
    if (currentPage.value > maxPage) {
      currentPage.value = maxPage;
    }
  }
};

onMounted(() => {
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
});

const novels: Novel[] = [
  {
    id: 1,
    title: 'Từ Tử Trầng Xuất Bao Quân',
    imageUrl: 'https://placehold.co/240x320'
  },
  {
    id: 2,
    title: 'Pokemon Chi Niary',
    imageUrl: 'https://placehold.co/240x320'
  },
  {
    id: 3,
    title: 'Cao Trung Trang Nguyên, Người Để Cho Ta Bất Quy? Di, Ta Bắt',
    imageUrl: 'https://placehold.co/240x320'
  },
  {
    id: 4,
    title: 'Nhân Vật Phản Diện, Ta Đem Cứu Nhân Đốt Đèn Trời, Không Quá Phân Di',
    imageUrl: 'https://placehold.co/240x320'
  },
  {
    id: 5,
    title: 'Xuyên Việt Hải Tặc: Toàn Viên Đem Hoàng Cấp',
    imageUrl: 'https://placehold.co/240x320'
  },
  {
    id: 6,
    title: 'Xuyên Việt Hải Tặc: Toàn Viên Đem Hoàng Cấp',
    imageUrl: 'https://placehold.co/240x320'
  }
];

const totalPages = computed(() => Math.ceil(novels.length / itemsPerPage.value));

const paginatedNovels = computed(() => {
  const start = currentPage.value * itemsPerPage.value;
  return novels.slice(start, start + itemsPerPage.value);
});

const nextPage = () => {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value++;
  }
};

const prevPage = () => {
  if (currentPage.value > 0) {
    currentPage.value--;
  }
};
</script>

<template>
  <div class="w-full max-w-7xl mx-auto px-4">
    <div class="flex items-center justify-between mb-6">
      <h2 class="text-xl font-bold text-amber-600">MỚI HOÀN THÀNH</h2>
      <div class="flex items-center gap-4">
        <span class="text-sm text-gray-600">{{ currentPage + 1 }}/{{ totalPages }}</span>
        <div class="flex gap-2">
          <button 
            @click="prevPage" 
            :disabled="currentPage === 0"
            class="text-amber-600 hover:text-amber-700 disabled:opacity-50 disabled:cursor-not-allowed w-8 h-8 flex items-center justify-center"
          >
            «
          </button>
          <button 
            @click="nextPage" 
            :disabled="currentPage === totalPages - 1"
            class="text-amber-600 hover:text-amber-700 disabled:opacity-50 disabled:cursor-not-allowed w-8 h-8 flex items-center justify-center"
          >
            »
          </button>
        </div>
      </div>
    </div>
    
    <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-4">
      <div v-for="novel in paginatedNovels" :key="novel.id" class="flex flex-col">
        <div class="relative aspect-[3/4] mb-2 overflow-hidden rounded-lg bg-gray-100">
          <img 
            :src="novel.imageUrl" 
            :alt="novel.title"
            class="w-full h-full object-cover hover:scale-105 transition-transform duration-300"
            loading="lazy"
          />
        </div>
        <h3 class="text-sm line-clamp-2 hover:text-amber-600">
          {{ novel.title }}
        </h3>
      </div>
    </div>
  </div>
</template>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>