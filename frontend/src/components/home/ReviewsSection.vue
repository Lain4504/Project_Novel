<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getLatestReview} from "@/api/user";

interface Review {
  id: string;
  userId: string;
  novelId: string;
  novelName: string;
  review: string;
  created: string;
  userName: string;
  image: string;
}

const reviews = ref<Review[]>([]);

const getLatestReviews = async () => {
  try {
    const response = await getLatestReview(1, 8);
    reviews.value = response.data;
  } catch (error) {
    console.error('Failed to fetch latest reviews:', error);
  }
};

onMounted(() => {
  getLatestReviews();
});
</script>
<template>
  <div class="max-w-7xl mx-auto p-4">
    <h1 class="text-2xl font-bold mb-6 text-gray-800">Latest Reviews</h1>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      <div v-for="review in reviews" :key="review.id"
           class="bg-white rounded-lg shadow-md p-4 hover:shadow-lg transition-shadow duration-200 overflow-hidden flex flex-col">
        <h2 class="text-xl font-semibold mb-2">
          <router-link :to="{ name: 'noveldetail', params: { id: review.novelId } }">{{
              review.novelName
            }}
          </router-link>
        </h2>
        <div class="overflow-y-auto max-h-48 flex-grow">
          <p class="text-gray-600 italic mb-4 break-words">{{ review.review }}</p>
        </div>
        <div class="mt-auto flex items-center justify-between">
          <div class="flex items-center space-x-2">
            <img :src="review.image" alt="User Avatar" class="w-8 h-8 rounded-full">
            <div class="font-medium">{{ review.userName }}</div>
          </div>
          <div class="text-sm text-gray-500">{{ review.created }}</div>
        </div>
      </div>
    </div>
  </div>
</template>