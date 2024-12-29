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
    <section class="p-4 border rounded bg-white">
      <div class="mt-4 text-sm">
        <div class="space-y-4">
          <article
              v-for="(item, index) in novel"
              :key="index"
              class="flex items-center space-x-4 border p-4 rounded bg-[#E7F5EE]"
          >
            <div class="flex flex-col w-full">
              <h2 class="font-semibold truncate break-words text-lg text-[#18A058]">
                <router-link :to="{ name: 'noveldetail', params: { id: item.novelId } }">
                  {{ item.novelTitle }}
                </router-link>
              </h2>
              <p class="text-gray-700 flex-shrink-0 break-words text-base">
                <router-link :to="{ name: 'chapter', params: { novel: item.novelId, chapter: item.novelChapterId } }">
                  {{ item.novelChapterTitle }}
                </router-link>
              </p>
              <p class="text-gray-500 break-words text-sm italic bg-gray-50 p-2">
                "{{ item.contentNote }}"
              </p>
            </div>
          </article>
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

<style scoped>

</style>