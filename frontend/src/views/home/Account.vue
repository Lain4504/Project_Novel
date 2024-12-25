<script lang="ts" setup>
import {Avatar, Breadcrumb, Card, Col, Descriptions, List, Pagination, Row, Typography} from 'ant-design-vue';
import {UserOutlined} from '@ant-design/icons-vue';
import {computed, onMounted, ref} from 'vue';
import {getUserProfile} from '@/api/user';
import {getNovelsByAuthorId} from '@/api/novel';
import store from '@/store';
import Ads from '@/components/home/Banner.vue';

const {Title, Paragraph, Text} = Typography;

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

const novels = ref<any[]>([]);
const currentPage = ref(1);
const pageSize = 3;
const totalPages = ref(1);
const loading = ref(false);

const userStats = computed(() => [
  {key: 'Read', value: `${userProfile.value.readNovels} novels`},
  {key: 'Marked', value: userProfile.value.marked},
  {key: 'Recommended', value: userProfile.value.recommended},
  {key: 'Comments', value: userProfile.value.comments},
  {key: 'Ratings', value: userProfile.value.ratings}
]);

const breadcrumbItems = [
  {title: 'Home', path: '/'},
  {title: 'Account', path: '/templates'},
  {title: 'Member'}
];

const fetchNovelsData = async (page: number, size: number) => {
  loading.value = true;
  try {
    const novelsData = await getNovelsByAuthorId(store.getters.getUserId, page, size);
    novels.value = novelsData.data;
    totalPages.value = novelsData.totalPages;
  } catch (error) {
    console.error('Error fetching novels data:', error);
  } finally {
    loading.value = false;
  }
};

const fetchUserProfile = async () => {
  try {
    const userProfileData = await getUserProfile(store.getters.getUserId);
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
                <Text type="secondary">Member</Text>
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
              <Title :level="4">Your Published Novels</Title>
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
                      <router-link :to="`noveldetail/${item.id}`">
                        <Title :level="4" class="mb-2">{{ item.title }}</Title>
                      </router-link>

                      <Paragraph type="secondary">
                        <strong>Author:</strong> {{ item.authorName }}
                      </Paragraph>

                      <Paragraph type="secondary">
                        <strong>Genres:</strong>
                        {{ item.categories.map((cat: { name: string }) => cat.name).join(', ') }}
                      </Paragraph>

                      <Paragraph class="italic" v-html="item.description"/>

                      <div class="flex justify-between text-gray-500 text-sm">
                        <span>{{ item.chapters }} Chapters</span>
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
                  :show-total="(total: number) => `Total ${total} items`"
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