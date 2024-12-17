<script setup lang="ts">
import { onMounted, ref } from "vue";
import Ads from "@/components/home/Banner.vue";
import { getMyFollowedNovels } from "@/api/user";
import store from "@/store";

// Define the data structure for the novels
interface Novel {
  novelId: string;
  novelName: string;
  author: string;
  chapterCount: number | null;
  image: string;
}

const novels = ref<Novel[]>([]);

const fetchFollowNovels = async () => {
  try {
    const userId = store.getters.getUserId;
    const result = await getMyFollowedNovels(userId);
    console.log("Followed novels:", result);
    novels.value = result.data;
  } catch (error) {
    console.error("Failed to fetch followed novels:", error);
  }
};
onMounted(() => {
  fetchFollowNovels();
});
</script>

<template>
  <div class="max-w-7xl mx-auto">
    <Ads class="my-4" />
    <div class="max-w-7xl mx-auto">
      <div class="flex justify-between items-center my-2">
        <h4 class="text-lg font-semibold">TIỂU THUYẾT ĐÁNH DẤU CỦA BẠN</h4>
      </div>
      <section class="p-4 border rounded my-2">
        <div class="mt-4 text-sm">
          <div class="space-y-4">
            <div
                v-for="(item, index) in novels"
                :key="index"
                class="flex items-center space-x-4"
            >
              <imgv
                  :src="item.image"
                  alt="Book Image"
                  class="w-14 h-20 object-cover"
              />
              <div class="flex justify-between items-center w-full">
                <div>
                  <h5 class="font-semibold truncate">{{ item.novelName }}</h5>
                  <p class="text-gray-500">{{ item.author }}</p>
                </div>
                <p class="text-gray-500 flex-shrink-0">
                  {{ item.chapterCount }} chương
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped>
/* Add any custom styles if needed */
</style>
