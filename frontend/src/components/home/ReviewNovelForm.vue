<script setup lang="ts">
import { createReview, getReviewList } from '@/api/user';
import { computed, ref, onMounted } from 'vue';
import { useStore } from 'vuex';

interface Review {
  id: string;
  review: string;
  userId: string;
}

const props = defineProps<{ itemId: string }>();
const emit = defineEmits(['reviewCreated']);

const store = useStore();
const userId = computed(() => store.getters.getUserId);

const reviewContent = ref('');
const reviews = ref<Review[]>([]);
const totalReviews = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const totalPages = ref(0);

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
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchReviews(page, pageSize.value);
};

onMounted(() => {
  fetchReviews(currentPage.value, pageSize.value);
});
</script>

<template>
  <section>
    <h3 class="text-xl font-bold mb-4">Chấm điểm nội dung truyện</h3>

    <!-- Form gửi đánh giá -->
    <div class="flex flex-col space-y-4">
      <div>
        <textarea
            v-model="reviewContent"
            class="w-full p-4 border rounded"
            placeholder="Nội dung bài đánh giá (ít nhất 100 từ)"
        ></textarea>
      </div>
      <button
          @click="submitReview"
          class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
      >
        Gửi đánh giá
      </button>
    </div>

    <!-- Danh sách đánh giá -->
    <div v-if="reviews.length" class="mt-6">
      <h4 class="text-lg font-semibold mb-2">Danh sách đánh giá</h4>
      <ul class="space-y-4">
        <li
            v-for="review in reviews"
            :key="review.id"
            class="p-4 border rounded shadow-sm bg-gray-50"
        >
          <p class="text-gray-800">{{ review.review }}</p>
          <small class="text-gray-500">Người dùng: {{ review.userId }}</small>
        </li>
      </ul>
      <a-pagination
        :current="currentPage"
        :pageSize="pageSize"
        :total="totalReviews"
        @change="handlePageChange"
        class="mt-4"
      />
    </div>

    <!-- Trường hợp không có đánh giá -->
    <div v-else class="mt-6 text-gray-500">
      <p>Hiện tại chưa có đánh giá nào. Hãy là người đầu tiên đánh giá!</p>
    </div>
  </section>
</template>