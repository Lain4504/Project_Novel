<script setup lang="ts">
import Ads from '@/components/home/Banner.vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';

import { ref, onMounted } from 'vue';
import { getUserProfile } from '@/api/user';
import store from '@/store';

const userProfile = ref({
  id: '',
  avatar: '',
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
});

const fetchUserProfile = async () => {
  try {
    const userProfileData = await getUserProfile(store.getters.getUserId);
    userProfile.value = userProfileData;
  } catch (error) {
    console.error('Error fetching user profile:', error);
  }
};

onMounted(fetchUserProfile);

const publishedNovels = ref([
  {
    id: 1,
    title: 'The Dark Chronicles',
    image: 'https://via.placeholder.com/150',
    description: 'A dark and mysterious adventure of epic proportions.',
    author: 'John Doe',
    genre: 'Fantasy',
    lastUpdated: '2024-11-25',
    chapters: 1304
  },
  {
    id: 2,
    title: 'Journey of the Lost Kingdom',
    image: 'https://via.placeholder.com/150',
    description: 'Follow the journey of a young prince to reclaim his kingdom.',
    author: 'Jane Smith',
    genre: 'Adventure',
    lastUpdated: '2024-11-20',
    chapters: 876
  }
]);
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
                :src="userProfile?.avatar || 'https://via.placeholder.com/150'"
                alt="User Profile Picture"
            />
            <div>
              <h3 class="text-xl font-semibold text-gray-900">{{ userProfile?.username }}</h3>
              <p class="text-sm text-gray-500">Member</p>
            </div>
          </div>

          <div class="space-y-4">
            <div v-for="(detail, key) in {
              'Date Join': userProfile?.dateCreated,
              'Bio': userProfile?.bio || 'No bio available',
              'Read': `${userProfile?.readNovels || '0'} novels`,
              'Marked': userProfile?.marked || '0',
              'Recommended': userProfile?.recommended || '0',
              'Comments': userProfile?.comments || '0',
              'Ratings': userProfile?.ratings || '0'
            }" :key="key" class="flex justify-between border-b py-2 last:border-b-0">
              <span class="text-gray-600 font-medium">{{ key }}:</span>
              <span class="text-gray-800 font-semibold">{{ detail }}</span>
            </div>
          </div>
        </div>

        <!-- Published Novels Box -->
        <div class="lg:col-span-2 bg-white rounded-xl shadow-lg p-6">
          <h2 class="text-2xl font-bold text-gray-800 mb-6 border-b pb-3">Your Published Novels</h2>

          <div class="space-y-6">
            <div
                v-for="novel in publishedNovels"
                :key="novel.id"
                class="flex items-center space-x-6 pb-6 border-b last:border-b-0 hover:bg-gray-50 p-4 rounded-lg transition-all"
            >
              <img
                  :src="novel.image"
                  alt="Novel Cover"
                  class="w-32 h-48 object-cover rounded-lg shadow-md"
              />
              <div class="flex-1">
                <h3 class="text-xl font-bold text-gray-900 mb-2">{{ novel.title }}</h3>
                <div class="space-y-2 mb-4">
                  <p class="text-sm text-gray-600">
                    <span class="font-semibold">Author:</span> {{ novel.author }}
                  </p>
                  <p class="text-sm text-gray-600">
                    <span class="font-semibold">Genre:</span> {{ novel.genre }}
                  </p>
                  <p class="text-sm text-gray-500 italic">{{ novel.description }}</p>
                </div>
                <div class="flex justify-between text-sm text-gray-500">
                  <span>{{ novel.chapters }} Chapters</span>
                  <span>Last Updated: {{ novel.lastUpdated }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Additional custom styles can be added here if needed */
</style>