<script setup lang="ts">
import { computed, ref, onMounted, watch } from 'vue';
import { getMyNovels } from '@/api/novel';

interface Novel {
  id: string;
  title: string;
  description: string;
  image: string;
  author: string;
}

const tabs = [
  { label: 'TOP THÁNG', value: 'top', sort: '' },
  { label: 'ĐỀ CỬ', value: 'recommentation', sort: 'recommentation' },
];

const activeTab = ref(tabs[0].value);
const currentPage = ref(1);
const books = ref<Novel[]>([]);
const itemsPerPage = 6;

const fetchBooks = async (page: number, sort: string) => {
  try {
    const response = await getMyNovels(page, itemsPerPage);
    console.log('Books:', response);
    books.value = response.data.map((book: any) => ({
      id: book.id,
      title: book.title,
      description: book.description,
      image: book.image.path,
    }));
  } catch (error) {
    console.error('Error fetching books:', error);
  }
};

const totalPages = computed(() => {
  return Math.ceil(books.value.length / itemsPerPage);
});

const setActiveTab = (tab: { label: string; value: string; sort: string }) => {
  activeTab.value = tab.value;
  currentPage.value = 1;
  fetchBooks(currentPage.value, tab.sort);
};

const changePage = (page: number) => {
  currentPage.value = page;
  fetchBooks(page, tabs.find(tab => tab.value === activeTab.value)?.sort || '');
};

onMounted(() => {
  fetchBooks(currentPage.value, tabs[0].sort);
});

watch(activeTab, (newTab) => {
  fetchBooks(currentPage.value, tabs.find(tab => tab.value === newTab)?.sort || '');
});
</script>
<template>
  <div class="p-6">
    <ul class="hnt-tab flex justify-start space-x-4 text-start py-4">
      <li
          v-for="tab in tabs"
          :key="tab.value"
          class="item cursor-pointer transition-transform duration-300 ease-in-out"
          :class="[
          activeTab === tab.value
            ? 'text-blue-600 font-bold border-b-2 border-blue-600'
            : 'text-gray-600',
          'hover:scale-105',
        ]"
          @click="setActiveTab(tab)"
      >
        {{ tab.label }}
      </li>
    </ul>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
          v-for="book in books"
          :key="book.id"
          class="flex space-x-4 border p-4 rounded-lg shadow-sm hover:shadow-md transition-shadow duration-200"
      >
        <img
            :src="book.image"
            alt="book cover"
            class="w-24 h-36 object-cover rounded-lg"
        />
        <div class="flex-1">
          <h3 class="text-lg font-semibold line-clamp-2">{{ book.title }}</h3>
          <p class="text-sm text-gray-500 line-clamp-3">{{ book.description }}</p>
          <div class="flex items-center text-sm text-gray-700 mt-2">
            <i class="fas fa-user mr-2"></i>
            {{ book.author }}
          </div>
          <span
              class="inline-block bg-yellow-100 text-yellow-600 text-xs font-semibold mt-3 px-3 py-1 rounded-full"
          >
            dsasadsdssd
          </span>
        </div>
      </div>
    </div>

    <div class="flex justify-center mt-4 space-x-2">
      <button
          v-for="page in [1, 2]"
          :key="page"
          @click="() => changePage(page)"
          :class="[
          'w-3 h-3 rounded-full transition-all',
          currentPage === page ? 'bg-yellow-600' : 'bg-gray-300',
        ]"
      ></button>
    </div>
  </div>
</template>
