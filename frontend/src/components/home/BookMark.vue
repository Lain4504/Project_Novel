<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getMyReadingList} from "@/api/user";
import store from "@/store";

interface Novel {
  novelId: string;
  novelName: string;
  author: string;
  image: string;
  novelChapterId: string;
  novelChapterTitle: string;
}

const currentPage = ref(1);
const totalPages = ref(0);
const pageSize = 10;
const novel = ref<Novel[]>([]);

const fetchNovel = async (page: number) => {
  try {
    const response = await getMyReadingList(store.getters.getUserId, page, pageSize);
    console.log("Reading novels:", response);
    novel.value = response.data.map((novel: any) => ({
      novelId: novel.novelId,
      novelName: novel.novelName,
      image: novel.image,
      novelChapterId: novel.novelChapterId,
      novelChapterTitle: novel.novelChapterTitle,
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
  <div class=" max-w-7xl mx-auto ">
    <section class="p-4 border rounded ">
      <div class="mt-4 text-sm">
        <div class="space-y-4">
          <div
              v-for="(item, index) in novel"
              :key="index"
              class="flex items-center space-x-4"
          >
            <img
                :src="item.image"
                alt="Book Image"
                class="w-14 h-20 object-cover"
            />
            <div class="flex justify-between items-center w-full">
              <h5 class="font-semibold truncate">
                <router-link :to="{ name: 'noveldetail', params: { id: item.novelId } }">
                  {{ item.novelName }}
                </router-link>
              </h5>
              <p class="text-gray-500 flex-shrink-0">
                {{ item.novelChapterTitle }}
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
  

