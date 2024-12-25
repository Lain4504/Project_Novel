<script lang="ts" setup>
import {computed, onMounted, ref} from 'vue';
import {getPosts} from "@/api/post";
import {getMyNovels} from "@/api/novel";

interface Post {
  id: string;
  title: string;
  userId: string;
  content: string;
  categoryId: string;
  created: string;
}

const latestPosts = ref<Post[]>([]);
// Đảm bảo rằng getPosts trả về dữ liệu đúng định dạng
const fetchLatestPosts = async () => {
  try {
    const page = 1;
    const size = 5;
    const response = await getPosts({page, size});
    const posts = response.data.map((post: any) => ({
      id: post.id,
      title: post.title,
      userId: post.userId,
      content: post.content,
      categoryId: post.categoryName, // Map categoryName to categoryId
      created: post.created,
    }));
    console.log("Latest posts:", response);
    latestPosts.value = posts; // Ensure posts is an array
  } catch (error) {
    console.error("Error fetching latest posts:", error);
  }
};
onMounted(() => {
  fetchLatestPosts();
});

interface Novel {
  id: string;
  title: string;
  description: string;
  image: string;
  author: string;
}

const myNovels = ref<Novel[]>([]);

const fetchMyNovels = async () => {
  try {
    const page = 1;
    const size = 4;
    const response = await getMyNovels(page, size);
    console.log("My novels:", response);
    myNovels.value = response.data.map((novel: any) => ({
      id: novel.id,
      title: novel.title,
      description: novel.description,
      image: novel.image.path,
      author: novel.author,
    }));
  } catch (error) {
    console.error("Error fetching my novels:", error);
  }
};

onMounted(() => {
  fetchMyNovels();
});

// Computed property
const limitedReadings = computed(() => readings.value.slice(0, 4));
</script>
<template>
  <div class="flex flex-col md:flex-row md:space-x-4 space-y-4 md:space-y-0 mt-10">
    <!-- Bài viết mới nhất Section -->
    <div class="w-full md:w-3/5">
      <section class="p-4 border rounded">
        <div class="flex justify-between items-center">
          <h4 class="text-md font-bold">Bài viết mới nhất</h4>
          <router-link class="text-[#98a77c] text-sm" to="#">Xem tất cả</router-link>
        </div>
        <div class="mt-4 text-sm">
          <ul class="space-y-4">
            <li v-if="latestPosts.length === 0">Chưa có dữ liệu bài viết</li>
            <li
                v-for="(post, index) in latestPosts"
                :key="index"
                class="border-b pb-2"
            >
              <h5 class="font-semibold truncate">{{ post.title }}</h5>
              <p class="text-gray-500 text-xs">
                {{ post.created }} - {{ post.categoryId }}
              </p>
            </li>
          </ul>
        </div>
      </section>
    </div>
    <!-- Đang đọc Section -->
    <div class="w-full md:w-2/5">
      <section class="p-4 border rounded">
        <div class="flex justify-between items-center">
          <h4 class="text-md font-bold">Đang đọc</h4>
          <router-link class="text-[#98a77c] text-sm" to="/list/readinglist">Xem tất cả</router-link>
        </div>
        <div class="mt-4 text-sm">
          <div class="space-y-4">
            <div v-if="myNovels.length === 0">Bạn chưa đọc truyện nào</div>
            <div
                v-for="(novel, index) in myNovels"
                :key="index"
                class="flex items-center space-x-4"
            >
              <img
                  :src="novel.image"
                  alt="Reading Image"
                  class="w-14 h-20 object-cover"
              />
              <div class="flex justify-between items-center w-full">
                <h5 class="font-semibold truncate">{{ novel.title }}</h5>
                <p class="text-gray-500 flex-shrink-0">
                  Chương 100/200
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>