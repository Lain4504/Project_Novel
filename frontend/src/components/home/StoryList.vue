<script lang="ts" setup>
import {computed, onMounted, ref} from 'vue';
import {getPosts} from "@/api/post";
import {getMyReadingList} from "@/api/user";
import store from "@/store";
import {Typography} from 'ant-design-vue';

const { Title, Text } = Typography;

interface Post {
  id: string;
  title: string;
  userId: string;
  content: string;
  categoryName: string;
  created: string;
}

interface Novel {
  novelId: string;
  novelName: string;
  author: string;
  image: string;
  novelChapterId: string;
  novelChapterTitle: string;
}

const latestPosts = ref<Post[]>([]);
const readingNovels = ref<Novel[]>([]);
const isAuthenticated = computed(() => store.getters.isAuthenticated);

const fetchLatestPosts = async () => {
  try {
    const page = 1;
    const size = 5;
    const response = await getPosts({ page, size });
    latestPosts.value = response.data.map((post: any) => ({
      id: post.id,
      title: post.title,
      userId: post.userId,
      content: post.content,
      categoryName: post.categoryName,
      created: post.created,
    }));
  } catch (error) {
    console.error("Error fetching latest posts:", error);
  }
};

const fetchReadingNovels = async () => {
  try {
    const page = 1;
    const size = 4;
    const response = await getMyReadingList(store.getters.getUserId, page, size);
    readingNovels.value = response.data.map((novel: any) => ({
      novelId: novel.novelId,
      novelName: novel.novelName,
      author: novel.author,
      image: novel.image,
      novelChapterId: novel.novelChapterId,
      novelChapterTitle: novel.novelChapterTitle,
    }));
  } catch (error) {
    console.error("Error fetching reading novels:", error);
  }
};

onMounted(() => {
  fetchLatestPosts();
  if (isAuthenticated.value) {
    fetchReadingNovels();
  }
});
</script>

<template>
  <div class="flex flex-col md:flex-row md:space-x-4 space-y-4 md:space-y-0 mt-10">
    <!-- Latest Posts Section -->
    <div class="w-full md:w-3/5">
      <a-card class="mb-6">
        <template #title>
          <div class="flex justify-between items-center">
            <span>Thảo luận mới nhất</span>
            <router-link :to="{ name: 'postforum' }">
              <a-button type="link" class="text-[#18A058] text-sm hover:underline">Xem tất cả</a-button>
            </router-link>
          </div>
        </template>
        <div class="mt-4 text-sm">
          <ul class="space-y-4">
            <li v-if="latestPosts.length === 0">No posts available</li>
            <li v-for="(post, index) in latestPosts" :key="index" class="border-b pb-2">
              <a-typography-title level={5} style="font-size: 16px">
                <router-link :to="{ name: 'postdetail', params: { id: post.id } }">
                  <span class="font-medium text-black truncate hover:text-[#18A058]">
                  {{ post.title }}
                  </span>
                </router-link>
              </a-typography-title>
              <a-typography-text class="text-[#18A058] text-xs space-x-2">
                <a-tag class="italic text-[#E7F5EE] bg-[#18A058]">{{ post.categoryName }}</a-tag>
                {{ post.created }}
              </a-typography-text>
            </li>
          </ul>
        </div>
      </a-card>
    </div>
    <!-- Reading Section -->
    <div v-if="isAuthenticated" class="w-full md:w-2/5">
      <a-card class="mb-6">
        <template #title>
          <div class="flex justify-between items-center">
            <span>Tiểu thuyết vừa đọc</span>
            <router-link :to="{ name: 'readinglist' }">
              <a-button type="link" class="text-[#18A058] text-sm hover:underline">Xem tất cả</a-button>
            </router-link>
          </div>
        </template>
        <div class="mt-4 text-sm">
          <div class="space-y-4">
            <div v-if="readingNovels.length === 0">You are not reading any novels</div>
            <div v-for="(novel, index) in readingNovels" :key="index" class="flex items-center space-x-4">
              <img :src="novel.image" alt="Reading Image" class="w-14 h-20 object-cover rounded-lg" />
              <div class="flex-1">
                <a-typography-title level={5} style="font-size: 16px">
                  <router-link :to="{ name: 'noveldetail', params: { id: novel.novelId } }">
                     <span class="font-medium text-black truncate hover:text-[#18A058]">
                    {{ novel.novelName }}
                    </span>
                  </router-link>
                </a-typography-title>
                <Text class="text-gray-500 hover:text-[#18A058]">
                  <router-link :to="{ name: 'chapter', params: { novel: novel.novelId, chapter: novel.novelChapterId}}">
                    {{ novel.novelChapterTitle }}
                  </router-link>
                </Text>
              </div>
            </div>
          </div>
        </div>
      </a-card>
    </div>
  </div>
</template>

<style scoped>
.bg-white {
  background-color: #FFFFFF;
}

.border {
  border-color: #E7F5EE;
}
</style>