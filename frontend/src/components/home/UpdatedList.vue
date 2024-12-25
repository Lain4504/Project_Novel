<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getLatestNovels} from "@/api/novel";

// Dữ liệu mẫu
interface Novel {
  id: number;
  title: string;
  latestChapterTitle: string; // ISO 8601 Date String
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

// Kiểm tra màn hình mobile
const isMobile = ref(false);

onMounted(() => {
  const checkMobile = () => {
    isMobile.value = window.innerWidth < 768; // 768px là ngưỡng độ rộng màn hình mobile
  };
  checkMobile();
  window.addEventListener('resize', checkMobile);
});
</script>

<template>
  <div class="max-w-7xl mx-auto py-8">
    <h1 class="text-xl font-semibold mb-6 text-start">Cập nhật mới nhất</h1>

    <!-- Hiển thị bảng trên màn hình lớn -->
    <table v-if="!isMobile && novels.length > 0" class="w-full border-collapse">
      <tbody>
      <tr v-for="novel in novels" :key="novel.id" class="border-b hover:bg-gray-50">
        <!-- <td :style="{ width: columnWidths.category + 'px' }" class="px-4 py-2 text-gray-700">ssss</td>  -->
        <td :style="{ width: columnWidths.title + 'px' }" class="px-4 py-2 text-sm font-semibold text-blue-600">
          {{ novel.title }}
        </td>
        <td :style="{ width: columnWidths.author + 'px' }" class="px-4 py-2 text-sm text-gray-700">{{
            novel.authorName
          }}
        </td>
        <td :style="{ width: columnWidths.latestChapter + 'px' }" class="px-4 py-2 text-sm text-gray-700">
          {{ novel.latestChapterTitle }}
        </td>
        <td :style="{ width: columnWidths.updatedAt + 'px', textAlign: 'right' }"
            class="px-4 py-2 text-sm text-gray-500">{{ novel.created }}
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Hiển thị cho mobile -->
    <div v-else-if="novels.length > 0">
      <div v-for="novel in novels" :key="novel.id" class="border-b hover:bg-gray-50 mb-4 p-4">
        <!-- Title -->
        <div class="text-sm font-semibold text-blue-600 mb-2">{{ novel.title }}</div>

        <!-- Author, Chapter (hàng ngang) -->
        <div class="flex justify-between mb-2">
          <div class="text-sm text-gray-700">{{ novel.authorName }}</div>
          <div class="text-sm text-gray-700">{{ novel.latestChapterTitle }}</div>
        </div>

        <!-- Category và UpdateAt (hàng ngang) -->
        <div class="flex justify-between">
          <div class="text-sm text-gray-700"></div>
          <div class="text-sm text-gray-500">{{ novel.created }}</div>
        </div>
      </div>
    </div>


    <!-- Hiển thị thông báo nếu không có tiểu thuyết -->
    <p v-else class="text-gray-500 text-center">Không có tiểu thuyết nào được cập nhật.</p>
  </div>
</template>


<style scoped>
@media screen and (max-width: 768px) {
  /* Định dạng cho màn hình di động */
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
  