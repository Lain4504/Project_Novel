<script setup lang="ts">
import Ads from '@/components/home/Banner.vue';
import { useStore } from 'vuex';
import { ref, onMounted } from 'vue';
import { getUserProfile } from '@/api/user';
import  Breadcrumb  from '@/components/home/Breadcrumb.vue';
const store = useStore();
const userId = store.getters.getUserId;

interface PublishedNovel {
  id: number;
  title: string;
  coverImage: string;
  lastUpdated: string;
}

interface UserProfile {
  profilePicture: string;
  username: string;
  dateCreated: string;
  bio: string;
  readNovels: number;
  marked: number;
  recommended: number;
  comments: number;
  ratings: number;
  publishedNovels: PublishedNovel[];
}

const userProfile = ref<UserProfile | null>(null);

const fetchUserProfile = async () => {
  try {
    const response = await getUserProfile(userId);
    userProfile.value = response.data;
    publishedNovels.value = response.data.publishedNovels || [];
  } catch (error) {
    console.error('Error fetching user profile:', error);
  }
};

onMounted(() => {
  fetchUserProfile();
});
const publishedNovels = ref([
  {
    id: 1,
    title: 'The Dark Chronicles',
    image: 'https://via.placeholder.com/150',
    description: 'A dark and mysterious adventure of epic proportions. A dark and mysterious adventure of epic proportions. A dark and mysterious adventure of epic proportions.',
    author: 'John Doe',
    genre: 'Fantasy',
    lastUpdated: '2024-11-25',
  },
  {
    id: 2,
    title: 'Journey of the Lost Kingdom',
    image: 'https://via.placeholder.com/150',
    description: 'Follow the journey of a young prince to reclaim his kingdom.A dark and mysterious adventure of epic proportions.A dark and mysterious adventure of epic proportions.',
    author: 'Jane Smith',
    genre: 'Adventure',
    lastUpdated: '2024-11-20',
  },

]);

</script>
<template>
      <div class="max-w-7xl mx-auto my-10">

        <Ads class="flex items-center justify-center mx-auto my-4" />
        <Breadcrumb
  :breadcrumbs="[
    { label: 'Home', href: '/' },
    { label: 'Account', href: '/templates' },
    { label: 'Member', href: '/flowbite', isCurrent: true }
  ]" 
/>
    <div class="container mx-auto p-2">
        <div class="flex flex-col lg:flex-row">
            <!-- User Info Box -->
            <div class="flex-shrink-0 w-full lg:w-1/3 bg-gray-100 p-4 rounded-md shadow-sm mb-6 md:mb-0">
                <h2 class="text-2xl font-semibold mb-4">User Information</h2>
                <div class="flex items-center space-x-4 mb-6">
                    <img class="w-16 h-16 rounded-full object-cover"
                        :src="userProfile?.profilePicture || 'https://via.placeholder.com/150'"
                        alt="User Profile Picture" />
                    <div>
                        <h3 class="text-xl font-semibold">{{ userProfile?.username || 'John Doe' }}</h3>
                    </div>
                </div>

                <div class="space-y-4">
                    <div class="flex items-center">
                        <label for="email" class="block text-sm font-medium text-gray-700 w-1/3">Date join:</label>
                        <p class="mt-1 block w-2/3 px-3 py-2">{{ userProfile?.dateCreated || '' }}</p>
                    </div>

                    <div class="flex items-center">
                        <label for="bio" class="block text-sm font-medium text-gray-700 w-1/3">Bio</label>
                        <p class="mt-1 block w-2/3 px-3 py-2">{{ userProfile?.bio || 'Lorem ipsum dolor sit amet,consectetur adipiscing elit.' }}</p>
                    </div>

                    <div class="flex items-center">
                        <label for="readBooks" class="block text-sm font-medium text-gray-700 w-1/3">Read</label>
                        <p class="mt-1 block w-2/3 px-3 py-2">{{ userProfile?.readNovels || '0 novels' }}</p>
                    </div>

                    <div class="flex items-center">
                        <label for="marked" class="block text-sm font-medium text-gray-700 w-1/3">Marked</label>
                        <p class="mt-1 block w-2/3 px-3 py-2">{{ userProfile?.marked || '0' }}</p>
                    </div>

                    <div class="flex items-center">
                        <label for="recommended"
                            class="block text-sm font-medium text-gray-700 w-1/3">Recommended</label>
                        <p class="mt-1 block w-2/3 px-3 py-2">{{ userProfile?.recommended || '0' }}</p>
                    </div>

                    <div class="flex items-center">
                        <label for="comments" class="block text-sm font-medium text-gray-700 w-1/3">Comments</label>
                        <p class="mt-1 block w-2/3 px-3 py-2">{{ userProfile?.comments || '0' }}</p>
                    </div>

                    <div class="flex items-center">
                        <label for="ratings" class="block text-sm font-medium text-gray-700 w-1/3">Ratings</label>
                        <p class="mt-1 block w-2/3 px-3 py-2">{{ userProfile?.ratings || '0' }}</p>
                    </div>
                </div>
            </div>

            <!-- Published Novels Box -->
            <div class="flex-1 w-full bg-gray-50 rounded-md p-4 shadow-sm">
                <h2 class="text-2xl font-semibold mb-4">Your Published Novels</h2>

                <div class="space-y-4">
                    <div v-for="novel in publishedNovels" :key="novel.id"
                        class="flex justify-between items-center border-b pb-4">
                        <div class="flex space-x-4">
                            <img :src="novel.coverImage || 'https://via.placeholder.com/150'" alt="Novel Cover"
                                class="w-24 h-36 object-cover rounded-lg" />
                            <div class="flex-1 flex flex-col justify-between">
                                <h3 class="text-lg font-semibold">{{ novel.title }}</h3>
                                <p class="text-sm text-gray-500"> {{ novel.description || 'N/A' }}</p>
                                <p class="text-sm text-gray-500"> {{ novel.author || 'N/A' }}</p>
                                <p class="text-sm text-gray-500"> {{ novel.genre || 'N/A' }}</p>

                                <div class="flex justify-between text-sm text-gray-500 mt-auto">
                                    <p>1304 chương</p>
                                    <p>Last updated: {{ novel.lastUpdated || 'N/A' }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>  </div>
</template>


<style scoped>
/* Add any custom styles here */
</style>