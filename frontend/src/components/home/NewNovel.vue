<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getLatestNovels, getNovels} from '@/api/novel';
import {Typography} from 'ant-design-vue';

const { Title, Text } = Typography;

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
  authorId: string;
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
  <div class="max-w-screen-xl mx-auto">
    <a-row :gutter="[24, 24]">
      <!-- Left: Latest Books Section -->
      <a-col :xs="24" :md="24" :lg="18">
        <a-card class="mb-6">
          <template #title>
            <Title :level="4" class="!mb-0">TIỂU THUYẾT MỚI</Title>
          </template>

          <a-empty v-if="latestNovels.length === 0" description="Chưa có dữ liệu tiểu thuyết mới" />

          <div v-else class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <a-card
                v-for="novel in latestNovels"
                :key="novel.id"
                class="hover:shadow-md transition-shadow duration-200"
                :bordered="false"
                :bodyStyle="{ padding: '12px' }"
            >
              <div class="flex space-x-4">
                <img
                    :src="novel.image"
                    :alt="novel.title"
                    class="w-24 h-36 object-cover rounded-lg"
                />
                <div class="flex-1">
                  <router-link
                      :to="{ name: 'noveldetail', params: { id: novel.id } }"
                      class="hover:text-[#18A058]"
                  >
                    <Title :level="5" class="!mb-2 line-clamp-2">
                      {{ novel.title }}
                    </Title>
                  </router-link>

                  <Text class="text-gray-500 line-clamp-3" v-html="novel.description" />

                  <div class="flex items-center mt-2 italic">
                    <router-link
                        :to="{ name: 'account', params: { id: novel.authorId } }"
                        class="text-gray-700"
                    >
                      <Text>
                        <template #prefix>
                          <UserOutlined class="mr-1" />
                        </template>
                        {{ novel.authorName }}
                      </Text>
                    </router-link>
                  </div>

                  <div class="mt-3 flex flex-wrap gap-2">
                    <a-tag
                        v-for="category in novel.categories.slice(0, 4)"
                        :key="category.id"
                        class="text-[#18A058] bg-[#E7F5EE] border-0"
                    >
                      {{ category.name }}
                    </a-tag>
                  </div>
                </div>
              </div>
            </a-card>
          </div>

          <div class="flex justify-center mt-4">
            <a-button type="primary" class="bg-[#18A058] hover:bg-[#16a34a]">
              Xem Thêm
            </a-button>
          </div>
        </a-card>
      </a-col>

      <!-- Right: Trending Books Sidebar -->
      <a-col :xs="24" :md="24" :lg="6">
        <a-card title="Theo Dõi Nhiều" class="bg-gray-50">
          <a-empty v-if="trendingBooks.length === 0" description="Chưa có dữ liệu tiểu thuyết theo dõi nhiều" />

          <template v-else>
            <div class="trending-books-grid">
              <a-card
                  v-for="book in trendingBooks"
                  :key="book.id"
                  :bordered="false"
                  :bodyStyle="{ padding: '8px' }"
              >
                <div class="flex items-center">
                  <img
                      :src="book.image"
                      :alt="book.title"
                      class="w-16 h-24 object-cover rounded-md"
                  />
                  <div class="ml-4">
                    <router-link
                        :to="{ name: 'noveldetail', params: { id: book.id } }"
                        class="hover:text-[#18A058]"
                    >
                      <Text strong class="text-sm">{{ book.title }}</Text>
                    </router-link>
                    <br />
                    <Text type="secondary" class="text-xs">
                      {{ book.followCount }} theo dõi
                    </Text>
                  </div>
                </div>
              </a-card>
            </div>
          </template>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<style scoped>

.trending-books-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 16px;
}

@media (min-width: 768px) {
  .trending-books-grid {
    grid-template-columns: 1fr;
  }
}
</style>