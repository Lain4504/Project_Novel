<script lang="ts" setup>
import {computed, onMounted, ref, watch} from 'vue';
import {getLatestNovels} from '@/api/novel';

interface Category {
  id: string;
  name: string;
  description: string;
  createdDate: string;
  modifiedDate: string;
}

interface Novel {
  id: string;
  title: string;
  description: string;
  image: string;
  authorName: string;
  authorId: string;
  categories: Category[];
}

const tabs = [
  {label: 'TOP THÁNG', value: 'top', sort: ''},
  {label: 'ĐỀ CỬ', value: 'recommentation', sort: 'recommentation'},
];

const activeTab = ref(tabs[0].value);
const currentPage = ref(1);
const books = ref<Novel[]>([]);
const itemsPerPage = 6;

const fetchBooks = async (page: number, sort: string) => {
  try {
    const response = await getLatestNovels(page, itemsPerPage);
    console.log('Books:', response);
    books.value = response.data.map((book: any) => ({
      id: book.id,
      title: book.title,
      description: book.description,
      image: book.image.path,
      authorName: book.authorName,
      authorId: book.authorId,
      categories: book.categories.map((category: any) => ({
        id: category.id,
        name: category.name,
        description: category.description,
        createdDate: category.createdDate,
        modifiedDate: category.modifiedDate,
      })),
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
          :class="[
          activeTab === tab.value
            ? 'text-blue-600 font-bold border-b-2 border-blue-600'
            : 'text-gray-600',
          'hover:scale-105',
        ]"
          class="item cursor-pointer transition-transform duration-300 ease-in-out"
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
          <h3 class="text-lg font-semibold line-clamp-2">
            <router-link :to="{ name: 'noveldetail', params: { id: book.id } }">
              {{ book.title }}
            </router-link>
          </h3>
          <p class="text-sm text-gray-500 line-clamp-3" v-html="book.description"/>
          <div class="flex items-center text-sm text-gray-700 mt-2">
            <i class="fas fa-user mr-2"></i>
            <router-link :to="{ name: 'account', params: { id: book.authorId } }">
              {{ book.authorName }}
            </router-link>
          </div>
          <span
              v-for="category in book.categories.slice(0, 4)"
              :key="category.id"
              class="inline-block bg-yellow-100 text-yellow-600 text-xs font-semibold mt-3 px-2 py-1 rounded-full"
          >
              {{ category.name }}
          </span>
        </div>
      </div>
    </div>

    <div class="flex justify-center mt-4 space-x-2">
      <button
          v-for="page in [1, 2]"
          :key="page"
          :class="[
          'w-3 h-3 rounded-full transition-all',
          currentPage === page ? 'bg-yellow-600' : 'bg-gray-300',
        ]"
          @click="() => changePage(page)"
      ></button>
    </div>
  </div>
</template>
