<script lang="ts" setup>
import {Avatar, Breadcrumb, Card, Col, Descriptions, List, Pagination, Row, Typography} from 'ant-design-vue';
import {UserOutlined} from '@ant-design/icons-vue';
import {computed, onMounted, ref} from 'vue';
import {getUserProfile} from '@/api/user';
import {getNovelsByAuthorId} from '@/api/novel';
import Ads from '@/components/home/Banner.vue';
import {useRoute} from "vue-router";
import store from "../../store";

const {Title, Paragraph, Text} = Typography;
const route = useRoute();
const userId = route.params.id as string;
const userProfile = ref({
  id: '',
  image: '',
  username: '',
  gender: '',
  dateOfBirth: '',
  bio: '',
  readNovels: '0',
  marked: '0',
  recommended: '0',
  comments: '0',
  ratings: '0',
  publishedNovels: '0',
  created: ''
});

interface Novel {
  id: string;
  title: string;
  authorName: string;
  description: string;
  image: {
    path: string;
  };
  categories: { name: string }[];
  chapterCount: number;
  created: string;
}

const novels = ref<Novel[]>([]);
const currentPage = ref(1);
const pageSize = 3;
const totalPages = ref(1);
const loading = ref(false);
const expandedNovels = ref<{ [key: string]: boolean }>({});

const userStats = computed(() => [
  {key: 'Read', value: `${userProfile.value.readNovels} novels`},
  {key: 'Marked', value: userProfile.value.marked},
  {key: 'Recommended', value: userProfile.value.recommended},
  {key: 'Comments', value: userProfile.value.comments},
  {key: 'Ratings', value: userProfile.value.ratings}
]);

const breadcrumbItems = [
  {title: 'Home', path: '/'},
  {title: 'Account'},
];
const totalElements = ref(0);
const fetchNovelsData = async (page: number, size: number) => {
  loading.value = true;
  try {
    const novelsData = await getNovelsByAuthorId(userId, page, size);
    console.log('Novels:', novelsData);
    novels.value = novelsData.data;
    totalElements.value = novelsData.totalElements;
    totalPages.value = novelsData.totalPages;
  } catch (error) {
    console.error('Error fetching novels data:', error);
  } finally {
    loading.value = false;
  }
};

const fetchUserProfile = async () => {
  try {
    const userProfileData = await getUserProfile(userId);
    userProfile.value = {
      ...userProfileData,
      image: userProfileData.image.path
    };
  } catch (error) {
    console.error('Error fetching user profile:', error);
  }
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchNovelsData(page, pageSize);
};

const toggleExpanded = (id: string) => {
  expandedNovels.value[id] = !expandedNovels.value[id];
};

const isDescriptionLong = (description: string) => {
  return description.length > 300; // Adjust the length as needed
};

onMounted(() => {
  fetchNovelsData(currentPage.value, pageSize);
  fetchUserProfile();
});
</script>

<template>
  <div class="min-h-screen bg-[#f0f2f5] py-8">
    <div class="max-w-7xl mx-auto px-4">
      <Ads class="mb-6"/>

      <Breadcrumb class="mb-6">
        <Breadcrumb.Item v-for="(item, index) in breadcrumbItems" :key="index">
          <router-link v-if="item.path" :to="item.path">{{ item.title }}</router-link>
          <span v-else>{{ item.title }}</span>
        </Breadcrumb.Item>
      </Breadcrumb>

      <Row :gutter="24">
        <!-- User Profile Card -->
        <Col :md="8" :sm="24" :xs="24">
          <Card :bordered="false" class="shadow-sm">
            <template #title>
              <Title :level="4">User Information</Title>
            </template>

            <div class="flex items-center mb-6">
              <Avatar
                  :icon="!userProfile.image && UserOutlined"
                  :size="80"
                  :src="userProfile.image"
                  class="mr-4"
              />
              <div>
                <Title :level="4" class="mb-0">{{ userProfile.username }}</Title>
                <a-tag class="bg-purple-300">{{store.getters.getUserRole}}</a-tag>
              </div>
            </div>

            <Descriptions :column="1" layout="vertical">
              <Descriptions.Item label="Date Join">
                {{ userProfile.created || 'No date available' }}
              </Descriptions.Item>
              <Descriptions.Item label="Bio">
                <span v-html="userProfile.bio || 'No bio available'"></span>
              </Descriptions.Item>
              <template v-for="stat in userStats" :key="stat.key">
                <Descriptions.Item :label="stat.key">
                  {{ stat.value }}
                </Descriptions.Item>
              </template>
            </Descriptions>
          </Card>
        </Col>

        <!-- Published Novels Card -->
        <Col :md="16" :sm="24" :xs="24">
          <Card :bordered="false" class="shadow-sm">
            <template #title>
              <Title :level="4">Tiểu thuyết đã đăng</Title>
            </template>

            <List
                :data-source="novels"
                :loading="loading"
                item-layout="vertical"
                size="large"
            >
              <template #renderItem="{ item }">
                <List.Item>
                  <div class="flex">
                    <img
                        :alt="item.title"
                        :src="item.image.path"
                        class="w-32 h-48 object-cover rounded mr-6"
                    />
                    <div class="flex-1">
                      <router-link :to="`/${item.id}`">
                        <Title :level="4" class="mb-2 hover:text-[#18A058]">{{ item.title }}</Title>
                      </router-link>

                      <Paragraph type="secondary">
                        <strong>Tác giả:</strong> <span class="italic">{{ item.authorName }}</span>
                      </Paragraph>

                      <Paragraph type="secondary">
                        <strong class="mr-2">Thể loại:</strong>
                        <template v-for="cat in item.categories" :key="cat.name">
                          <a-tag class="text-[#18A058] font-semibold bg-[#E7F5EE]">{{ cat.name }}</a-tag>
                        </template>
                      </Paragraph>

                      <Paragraph class="italic" v-html="expandedNovels[item.id] ? item.description : item.description.slice(0, 300) + '...'" />
                      <div v-if="isDescriptionLong(item.description) && !expandedNovels[item.id]" class="text-right">
                        <a @click="toggleExpanded(item.id)" class="text-[#18A058] cursor-pointer hover:text-[#18A058]">Đọc thêm</a>
                      </div>
                      <div v-if="expandedNovels[item.id]" class="text-right">
                        <a @click="toggleExpanded(item.id)" class="text-[#18A058] cursor-pointer hover:text-[#18A058]">Rút gọn</a>
                      </div>

                      <div class="flex justify-between text-gray-500 text-sm">
                        <span>{{ item.chapterCount }} Chapters</span>
                        <span>Last Updated: {{ item.created }}</span>
                      </div>
                    </div>
                  </div>
                </List.Item>
              </template>
            </List>

            <div class="flex justify-center mt-6">
              <Pagination
                  :current="currentPage"
                  :pageSize="pageSize"
                  :show-total="() => `Total ${totalElements} items`"
                  :total="totalPages * pageSize"
                  show-quick-jumper
                  @change="handlePageChange"
              />
            </div>
          </Card>
        </Col>
      </Row>
    </div>
  </div>
</template>

<style scoped>
.ant-card {
  border-radius: 8px;
}

.ant-list-item {
  padding: 24px;
  transition: all 0.3s;
}

.ant-list-item:hover {
  background-color: #fafafa;
}

.ant-description-item-label {
  font-weight: 500;
}
</style>