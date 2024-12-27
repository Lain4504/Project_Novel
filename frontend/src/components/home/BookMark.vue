<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getBookmark} from "@/api/user";
import store from "@/store";

interface Novel {
  id: string;
  novelId: string;
  novelTitle: string;
  image: string;
  novelChapterId: string;
  novelChapterTitle: string;
  created: string;
  contentNote: string;
}

const currentPage = ref(1);
const totalPages = ref(0);
const pageSize = 10;
const novel = ref<Novel[]>([]);

const fetchNovel = async (page: number) => {
  try {
    const response = await getBookmark(store.getters.getUserId, page, pageSize);
    console.log("Bookmark novels:", response);
    novel.value = response.data.map((novel: any) => ({
      id: novel.id,
      novelId: novel.novelId,
      novelTitle: novel.novelTitle,
      image: novel.image,
      novelChapterId: novel.novelChapterId,
      novelChapterTitle: novel.novelChapterTitle,
      created: novel.created,
      contentNote: novel.contentNote,
    }));
    currentPage.value = response.currentPage;
    totalPages.value = response.totalPages;
  } catch (error) {
    console.error("Error fetching novels:", error);
  }
};

const handlePageChange = (page: number) => {
  fetchNovel(page);
};

onMounted(() => {
  fetchNovel(currentPage.value);
});
</script>

<template>
  <div class="max-w-7xl mx-auto">
    <section class="p-4 border rounded">
      <div class="mt-4 text-sm">
        <div class="space-y-4">
          <div
              v-for="(item, index) in novel"
              :key="index"
              class="flex items-center space-x-4 border p-4 rounded"
          >
            <div class="flex flex-col w-full">
              <h5 class="font-semibold truncate font-bold break-words text-lg text-blue-600">
                <router-link :to="{ name: 'noveldetail', params: { id: item.novelId } }">
                  {{ item.novelTitle }}
                </router-link>
              </h5>
              <p class="text-gray-500 flex-shrink-0 break-words text-base">
                <router-link :to="{ name: 'chapter', params: { novel: item.novelId, chapter: item.novelChapterId } }">
                  {{ item.novelChapterTitle }}
                </router-link>
              </p>
              <p class="text-gray-400 break-words text-sm italic">
                "{{ item.contentNote }}"
              </p>
            </div>
          </div>
        </div>
      </div>
      <div class="flex justify-center mt-4">
        <a-pagination
            :current="currentPage"
            :pageSize="pageSize"
            :total="totalPages * pageSize"
            @change="handlePageChange"
        />
      </div>
    </section>
  </div>
</template>


