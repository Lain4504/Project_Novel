<script setup lang="ts">
import Ads from '@/components/home/Banner.vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';

import {ref, onMounted, reactive} from 'vue';
import { getUserProfile } from '@/api/user';
import store from '@/store';
import {getNovelsByAuthorId} from "@/api/novel";

const userProfile = ref({
  id: '',
  image: '',
  username: '',
  gender: '',
  dateOfBirth: '',
  bio: '',
  readNovels: '',
  marked: '',
  recommended: '',
  comments: '',
  ratings: '',
  publishedNovels: '',
  created: ''
});
const novels = ref<any[]>([]);
const currentPage = ref(1);
const pageSize = 3;
const totalPages = ref(1);

const fetchNovelsData = async (page: number, size: number) => {
  try {
    const novelsData = await getNovelsByAuthorId(store.getters.getUserId, page, size);
    novels.value = novelsData.data;
    totalPages.value = novelsData.totalPages;
  } catch (error) {
    console.error('Error fetching novels data:', error);
  }
};

const fetchUserProfile = async () => {
  try {
    const userProfileData = await getUserProfile(store.getters.getUserId);
    userProfile.value = userProfileData;
    userProfile.value.image = userProfileData.image.path;
  } catch (error) {
    console.error('Error fetching user profile:', error);
  }
};

const goToPage = (page: number) => {
  if (page > 0 && page <= totalPages.value) {
    currentPage.value = page;
    fetchNovelsData(page, pageSize);
  }
};

onMounted(() => {
  fetchNovelsData(currentPage.value, pageSize);
  fetchUserProfile();
});
</script>
<template>
  <div class="bg-gray-50 min-h-screen">
    <div class="max-w-7xl mx-auto py-10 px-4 sm:px-6 lg:px-8">
      <Ads class="mb-6" />

      <Breadcrumb
          :breadcrumbs="[
          { label: 'Home', to: '/' },
          { label: 'Account', to: '/templates' },
          { label: 'Member', to: '/flowbite', isCurrent: true }
        ]"
          class="mb-6"
      />

      <div class="grid lg:grid-cols-3 gap-8">
        <!-- User Info Box -->
        <div class="lg:col-span-1 bg-white rounded-xl shadow-lg p-6 transform transition-all hover:scale-[1.02]">
          <h2 class="text-2xl font-bold text-gray-800 mb-6 border-b pb-3">User Information</h2>

          <div class="flex items-center space-x-6 mb-6">
            <img
                class="w-20 h-20 rounded-full object-cover ring-4 ring-blue-100 shadow-md"
                :src="userProfile?.image || 'https://via.placeholder.com/150'"
                alt="User Profile Picture"
            />
            <div>
              <h3 class="text-xl font-semibold text-gray-900">{{ userProfile?.username }}</h3>
              <p class="text-sm text-gray-500">Member</p>
            </div>
          </div>

          <div class="space-y-4">
            <div
                v-for="(detail, key) in {
      'Date Join': userProfile?.created || 'No date available',
      'Bio': userProfile?.bio || 'No bio available',
      'Read': `${userProfile?.readNovels || '0'} novels`,
      'Marked': userProfile?.marked || '0',
      'Recommended': userProfile?.recommended || '0',
      'Comments': userProfile?.comments || '0',
      'Ratings': userProfile?.ratings || '0'
    }"
                :key="key"
                class="flex justify-between border-b py-2 last:border-b-0"
            >
              <span class="text-gray-600 font-medium">{{ key }}:</span>
              <span
                  v-if="key === 'Bio'"
                  class="text-gray-800 font-semibold"
                  v-html="detail">
    </span>
              <span
                  v-else
                  class="text-gray-800 font-semibold">
      {{ detail }}
    </span>
            </div>
          </div>
        </div>

        <!-- Published Novels Box -->
        <div class="lg:col-span-2 bg-white rounded-xl shadow-lg p-6">
          <h2 class="text-2xl font-bold text-gray-800 mb-6 border-b pb-3">Your Published Novels</h2>

          <div class="space-y-6">
            <div
                v-for="novel in novels"
                :key="novel.id"
                class="flex items-center space-x-6 pb-6 border-b last:border-b-0 hover:bg-gray-50 p-4 rounded-lg transition-all"
            >
              <img
                  :src="novel.image.path"
                  alt="Novel Cover"
                  class="w-32 h-48 object-cover rounded-lg shadow-md"
              />
              <div class="flex-1">
                <router-link :to='`noveldetail/${novel.id}`'>
                <h3 class="text-xl font-bold text-gray-900 mb-2">{{ novel.title }}</h3>
                </router-link>
                <div class="space-y-2 mb-4">
                  <p class="text-sm text-gray-600">
                    <span class="font-semibold">Author:</span> {{ novel.authorName }}
                  </p>
                  <p class="text-sm text-gray-600 font-semibold">
                    Genres:
                    <span class="font-normal"
                          v-for="(category, index) in novel.categories"
                          :key="index"
                    >{{ category.name }}{{ index < novel.categories.length - 1 ? ', ' : '' }}</span>
                  </p>
                  <p v-html="novel.description" class="text-sm text-gray-500 italic"/>
                </div>
                <div class="flex justify-between text-sm text-gray-500">
                  <span>{{ novel.chapters }} Chapters</span>
                  <span>Last Updated: {{ novel.created }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Pagination -->
          <div class="flex flex-col items-center my-4">
            <div class="flex justify-center mb-2">
              <button @click="goToPage(1)" :disabled="currentPage === 1"
                      class="px-3 py-1 mx-1 text-sm text-gray-400 rounded hover:bg-[#BA5B38]">
                Đầu
              </button>
              <button v-for="page in totalPages" :key="page" @click="goToPage(page)"
                      class="px-3 py-1 mx-1 text-sm rounded hover:bg-[#BA5B38] hover:text-[#F8F8F7]"
                      :class="{'bg-[#C96442] text-white': page === currentPage, 'text-[#BA5B38]': page !== currentPage}">
                {{ page }}
              </button>
              <button @click="goToPage(totalPages)" :disabled="currentPage === totalPages"
                      class="px-3 py-1 mx-1 text-sm text-gray-400 rounded hover:bg-[#BA5B38]">
                Cuối
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>