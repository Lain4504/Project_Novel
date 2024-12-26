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
      <Button
          type="primary"
          class="self-start"
          @click="submitReview"
      >
        Gửi đánh giá
      </Button>
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
          <h5 class="font-semibold">{{ review.novelName }}</h5>
          <p class="text-gray-800">{{ review.review }}</p>
          <div class="mt-auto flex items-center justify-between">
            <div class="flex items-center space-x-2">
              <img :src="review.image" alt="User Avatar" class="w-8 h-8 rounded-full">
              <div class="font-medium">{{ review.userName }}</div>
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