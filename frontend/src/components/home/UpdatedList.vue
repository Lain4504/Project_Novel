<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getLatestNovels} from "@/api/novel";

interface Novel {
  id: number;
  title: string;
  latestChapterTitle: string;
  latestChapterId: string;
  authorId: string;
  authorName: string;
  created: string;
}

const novels = ref<Novel[]>([]);
const fetchNewUpdateNovels = async () => {
  const page = 1;
  const size = 10;
  try {
    const result = await getLatestNovels(page, size);
    console.log('New update novels:', result.data);
    novels.value = result.data;
  } catch (error) {
    console.error('Error fetching new update novels:', error);
  }
};
onMounted(() => {
  fetchNewUpdateNovels();
});
const columnWidths = ref({
  category: 50,
  title: 500,
  author: 250,
  latestChapter: 200,
  updatedAt: 200,
});

const isMobile = ref(false);

onMounted(() => {
  const checkMobile = () => {
    isMobile.value = window.innerWidth < 768;
  };
  checkMobile();
  window.addEventListener('resize', checkMobile);
});
</script>

<template>
  <div class="max-w-7xl mx-auto py-8">
    <h1 class="text-xl font-semibold mb-6 text-start">Cập nhật mới nhất</h1>

    <table v-if="!isMobile && novels.length > 0" class="w-full border-collapse">
      <tbody>
      <tr v-for="novel in novels" :key="novel.id" class="border-b hover:bg-[#E7F5EE]">
        <td :style="{ width: columnWidths.title + 'px' }" class="px-4 py-2 text-sm font-semibold text-[#18A058]">
          <router-link :to="{ name: 'noveldetail', params: { id: novel.id } }">{{ novel.title }}</router-link>
        </td>
        <td :style="{ width: columnWidths.author + 'px' }" class="px-4 py-2 text-sm text-gray-700 hover:text-[#18A058] italic">
          <router-link :to="{ name: 'account', params: { id: novel.authorId } }">{{ novel.authorName }}</router-link>
        </td>
        <td :style="{ width: columnWidths.latestChapter + 'px' }"
            class="px-4 py-2 text-sm text-gray-700 hover:text-[#18A058] hover:underline">
          <router-link :to="{ name: 'chapter', params: { novel: novel.id, chapter: novel.latestChapterId } }">
            {{ novel.latestChapterTitle }}
          </router-link>
        </td>
        <td :style="{ width: columnWidths.updatedAt + 'px', textAlign: 'right' }"
            class="px-4 py-2 text-sm text-gray-500">{{ novel.created }}
        </td>
      </tr>
      </tbody>
    </table>

    <div v-else-if="novels.length > 0">
      <div v-for="novel in novels" :key="novel.id" class="border-b hover:bg-[#E7F5EE] mb-4 p-4">
        <div class="text-sm font-semibold text-[#18A058] mb-2">
          <router-link :to="{ name: 'noveldetail', params: { id: novel.id } }">{{ novel.title }}</router-link>
        </div>

        <div class="flex justify-between mb-2">
          <div class="text-sm text-gray-700 italic">
            <router-link :to="{ name: 'account', params: { id: novel.authorId } }">{{ novel.authorName }}</router-link>
          </div>
          <div class="text-sm text-gray-700 hover:underline">
            <router-link :to="{ name: 'chapter', params: { novel: novel.id, chapter: novel.latestChapterId } }">
              {{ novel.latestChapterTitle }}
            </router-link>
          </div>
        </div>

        <div class="flex justify-between">
          <div class="text-sm text-gray-700"></div>
          <div class="text-sm text-gray-500">{{ novel.created }}</div>
        </div>
      </div>
    </div>

    <p v-else class="text-gray-500 text-center">Không có tiểu thuyết nào được cập nhật.</p>
  </div>
</template>

<style scoped>
@media screen and (max-width: 768px) {
  .text-start {
    text-align: left;
  }

  .w-full {
    width: 100%;
  }

  .max-w-7xl {
    max-width: 100%;
  }

  .px-4 {
    padding-left: 1rem;
    padding-right: 1rem;
  }

  .py-2 {
    padding-top: 0.5rem;
    padding-bottom: 0.5rem;
  }
}
</style>