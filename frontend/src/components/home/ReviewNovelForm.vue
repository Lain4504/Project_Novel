<script lang="ts" setup>
import {createReview, getReviewList} from '@/api/user';
import {computed, onMounted, ref} from 'vue';
import {useStore} from 'vuex';
import {Button, Pagination} from 'ant-design-vue';

interface Review {
  id: string;
  review: string;
  userId: string;
  novelName: string;
  userName: string;
  image: string;
  created: string;
}

const props = defineProps<{ itemId: string }>();
const emit = defineEmits(['reviewCreated']);

const store = useStore();
const userId = computed(() => store.getters.getUserId);
const isAuthenticated = computed(() => store.getters.isAuthenticated);

const reviewContent = ref('');
const reviews = ref<Review[]>([]);
const totalReviews = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const totalPages = ref(0);
const expandedReviews = ref<string[]>([]);

const fetchReviews = (page: number, size: number) => {
  getReviewList(props.itemId, page, size)
      .then(response => {
        reviews.value = response.data;
        totalReviews.value = response.totalElements;
        totalPages.value = response.totalPages;
      })
      .catch(error => {
        console.error('Error fetching reviews:', error);
      });
};

const submitReview = () => {
  if (isAuthenticated.value && reviewContent.value.trim()) {
    const reviewData = {
      userId: userId.value,
      novelId: props.itemId,
      review: reviewContent.value,
    };

    createReview(reviewData)
        .then(() => {
          console.log('Review created successfully');
          emit('reviewCreated');
          fetchReviews(currentPage.value, pageSize.value);
        })
        .catch(error => {
          console.error('Error:', error);
        });
  } else {
    console.error('User is not authenticated or review content is empty');
  }
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchReviews(page, pageSize.value);
};

const toggleReview = (id: string) => {
  if (expandedReviews.value.includes(id)) {
    expandedReviews.value = expandedReviews.value.filter(reviewId => reviewId !== id);
  } else {
    expandedReviews.value.push(id);
  }
};

onMounted(() => {
  fetchReviews(currentPage.value, pageSize.value);
});
</script>

<template>
  <section class="bg-white py-4 px-6 mx-auto review-section">
    <h3 class="text-xl font-bold mb-4">Chấm điểm nội dung truyện</h3>

    <!-- Form gửi đánh giá -->
    <div v-if="isAuthenticated" class="flex flex-col">
      <a-textarea
          v-model="reviewContent"
          class="w-full"
          placeholder="Nội dung bài đánh giá (ít nhất 100 từ)"
          :autosize="{ minRows: 2, maxRows: 6 }"></a-textarea>
      <div class="flex justify-end">
        <Button class="mt-2" type="primary" @click="submitReview">
          Gửi đánh giá
        </Button>
      </div>
    </div>
    <div v-else class="text-gray-500">
      <p>Bạn cần đăng nhập để gửi đánh giá.</p>
    </div>

    <!-- Danh sách đánh giá -->
    <div v-if="reviews.length" class="mt-6">
      <h4 class="text-lg font-semibold mb-2">Danh sách đánh giá</h4>
      <ul class="space-y-4">
        <li
            v-for="review in reviews"
            :key="review.id"
            class="p-4 border rounded shadow-sm bg-gray-50 review-item"
        >
          <h5 class="font-semibold text-[#18A058]">{{ review.novelName }}</h5>
          <div :class="{'max-h-48': !expandedReviews.includes(review.id)}" class="overflow-hidden">
            <p class="text-gray-800">{{ review.review }}</p>
          </div>
          <a-button type="link" v-if="review.review.length > 200" @click="toggleReview(review.id)"
                  class="text-[#18A058] hover:underline float-right">
            {{ expandedReviews.includes(review.id) ? 'Read Less' : 'Read More' }}
          </a-button>
          <div class="mt-auto flex items-center justify-between">
            <div class="flex items-center space-x-2">
              <img :src="review.image" alt="User Avatar" class="w-8 h-8 rounded-full">
              <div class="font-medium">
                <router-link :to="{ name: 'account', params: { id: review.userId } }">
                {{ review.userName }}
                </router-link>
              </div>
            </div>
            <div class="text-sm text-gray-500">{{ review.created }}</div>
          </div>
        </li>
      </ul>
      <Pagination
          :current="currentPage"
          :pageSize="pageSize"
          :total="totalReviews"
          class="mt-4"
          @change="handlePageChange"
      />
    </div>

    <!-- Trường hợp không có đánh giá -->
    <div v-else class="mt-6 text-gray-500">
      <p>Hiện tại chưa có đánh giá nào. Hãy là người đầu tiên đánh giá!</p>
    </div>
  </section>
</template>

<style scoped>
.review-section {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.review-item {
  border: 1px solid #d0d0d0;
  border-radius: 4px;
  padding: 16px;
  background-color: #ffffff;
}

.max-h-48 {
  max-height: 12rem;
}
</style>