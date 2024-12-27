<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getLatestNovels, getNovels} from '@/api/novel';

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
  authorId: string
  followCount?: number;
  categories: Category[];
}

const trendingBooks = ref<Novel[]>([]);
const latestNovels = ref<Novel[]>([]);

const fetchLatestNovels = async () => {
  try {
    const page = 1;
    const size = 6;
    const response = await getNovels(page, size);
    console.log('Latest novels:', response);
    latestNovels.value = response.data.map((novel: any) => ({
      id: novel.id,
      title: novel.title,
      description: novel.description,
      image: novel.image.path,
      authorName: novel.authorName,
      authorId: novel.authorId,
      categories: novel.categories.map((category: any) => ({
        id: category.id,
        name: category.name,
        description: category.description,
        createdDate: category.createdDate,
        modifiedDate: category.modifiedDate,
      })),
    }));
  } catch (error) {
    console.error('Error fetching latest novels:', error);
  }
};

const fetchTrendingNovels = async () => {
  try {
    const page = 1;
    const size = 6;
    const response = await getLatestNovels(page, size);
    console.log('Trending novels:', response);
    trendingBooks.value = response.data.map((novel: any) => ({
      id: novel.id,
      title: novel.title,
      description: novel.description,
      image: novel.image.path,
      authorName: novel.authorName,
      followCount: novel.followCount,
      categories: novel.categories.map((category: any) => ({
        id: category.id,
        name: category.name,
        description: category.description,
        createdDate: category.createdDate,
        modifiedDate: category.modifiedDate,
      })),
    }));
  } catch (error) {
    console.error('Error fetching latest novels:', error);
  }
};

onMounted(() => {
  fetchLatestNovels();
  fetchTrendingNovels();
});
</script>

<template>
  <div class="max-w-screen-xl mx-auto p-6">
    <div class="flex flex-col lg:flex-row gap-6">
      <!-- Left: Latest Books Section -->
      <div class="flex-1">
        <div class="flex justify-between items-center my-2 p-2">
          <h4 class="text-lg font-semibold">TIỂU THUYẾT MỚI</h4>
        </div>
        <div v-if="latestNovels.length === 0" class="text-center text-gray-500">
          Chưa có dữ liệu tiểu thuyết mới
        </div>
        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-2 gap-6">
          <div
              v-for="novel in latestNovels"
              :key="novel.id"
              class="flex space-x-4 border p-4 rounded-lg shadow-sm hover:shadow-md transition-shadow duration-200"
          >
            <img
                :src="novel.image"
                alt="book cover"
                class="w-24 h-36 object-cover rounded-lg"
            />
            <div class="flex-1">
              <h3 class="text-lg font-semibold line-clamp-2">
                <router-link :to="{ name: 'noveldetail', params: { id: novel.id } }">
                  {{ novel.title }}
                </router-link>
              </h3>
              <p class="text-sm text-gray-500 line-clamp-3" v-html="novel.description"/>
              <div class="flex items-center text-sm text-gray-700 mt-2">
                <i class="fas fa-user mr-2"></i>
                <router-link :to="{ name: 'account', params: { id: novel.authorId } }">
                  {{ novel.authorName }}
                </router-link>
              </div>
              <span
                  v-for="category in novel.categories.slice(0, 4)"
                  :key="category.id"
                  class="inline-block bg-yellow-100 text-yellow-600 text-xs font-semibold mt-3 px-3 py-1 rounded-full"
              >
                {{ category.name }}
              </span>
            </div>
          </div>
        </div>
        <div class="flex justify-center mt-4">
          <button
              class="text-sm bg-yellow-600 text-white py-2 px-4 rounded-full hover:bg-yellow-700 transition"
          >
            Xem Thêm
          </button>
        </div>
      </div>

      <!-- Right: Trending Books Sidebar -->
      <div class="w-full lg:w-1/4 flex flex-col gap-6">
        <div class="bg-gray-50 p-4 rounded-lg shadow-lg">
          <h3 class="text-xl font-semibold">Theo Dõi Nhiều</h3>
          <div v-if="trendingBooks.length === 0" class="text-center text-gray-500">
            Chưa có dữ liệu tiểu thuyết theo dõi nhiều
          </div>
          <div v-else class="mt-4">
            <div v-for="book in trendingBooks" :key="book.id" class="flex items-center mb-4">
              <img :src="book.image" alt="book cover" class="w-16 h-24 object-cover rounded-md"/>
              <div class="ml-4">
                <h4 class="text-sm font-semibold text-gray-800">
                  <router-link :to="{ name: 'noveldetail', params: { id: book.id } }">
                    {{ book.title }}
                  </router-link>
                </h4>
                <p class="text-xs text-gray-500">{{ book.followCount }} theo dõi</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>