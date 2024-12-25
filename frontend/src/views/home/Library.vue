<template>
  <div class="container max-w-7xl mx-auto min-h-screen flex flex-col">
    <Ads class="my-4"/>
    <Breadcrumb class="mb-6">
      <Breadcrumb.Item v-for="(item, index) in breadcrumbItems" :key="index">
        <router-link v-if="item.path" :to="item.path">{{ item.title }}</router-link>
        <span v-else>{{ item.title }}</span>
      </Breadcrumb.Item>
    </Breadcrumb>
    <div class="content flex-1">
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
              <img
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
        <div class="flex justify-center mt-6">
          <Pagination
              :current="currentPage"
              :pageSize="pageSize"
              :show-total="(total: number) => `Total ${total} items`"
              :total="totalElements"
              show-quick-jumper
              @change="handlePageChange"
          />
        </div>
      </section>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";
import Ads from "@/components/home/Banner.vue";
import {getMyFollowedNovels} from "@/api/user";
import store from "@/store";
import {Breadcrumb, Pagination} from 'ant-design-vue';

// Define the data structure for the novels
interface Novel {
  novelId: string;
  novelName: string;
  author: string;
  chapterCount: number | null;
  image: string;
}

const novels = ref<Novel[]>([]);
const currentPage = ref(1);
const pageSize = ref(10);
const totalElements = ref(0);

const fetchFollowNovels = async (page: number, size: number) => {
  try {
    const userId = store.getters.getUserId;
    const result = await getMyFollowedNovels(userId, page, size);
    console.log("Followed novels:", result);
    novels.value = result.data;
    totalElements.value = result.totalElements;
  } catch (error) {
    console.error("Failed to fetch followed novels:", error);
  }
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchFollowNovels(page, pageSize.value);
};

onMounted(() => {
  fetchFollowNovels(currentPage.value, pageSize.value);
});
const breadcrumbItems = [
  {title: 'Home', path: '/'},
  {title: 'Account', path: '/templates'},
  {title: 'Library'}
];
</script>

<style scoped>
.container {
  @apply min-h-screen flex flex-col;
}

.content {
  @apply flex-1;
}
</style>