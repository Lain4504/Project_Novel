<script setup lang="ts">
import {computed, ref} from 'vue';
import Comment from '@/components/home/CommentSection.vue';
import ReviewNovel from '@/components/home/ReviewNovelForm.vue';
import Ads from '@/components/home/Banner.vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';

interface Chapter {
  title: string;
}

const isCollected = ref(false);

const toggleCollect = () => {
  isCollected.value = !isCollected.value;
};

interface Volume {
  title: string;
  showMore: boolean;
  expanded: boolean;
  chapters: Chapter[];
}

const volumes = ref<Volume[]>([
  {
    title: 'Tập 1',
    showMore: false,
    expanded: false,
    chapters: [
      {title: 'Chương 1: Khởi Đầu Mới'},
      {title: 'Chương 2: Cuộc Phiêu Lưu'},
      {title: 'Chương 3: Tìm Kiếm'},
      {title: 'Chương 4: Đột Phá'},
      {title: 'Chương 5: Tái Ngộ'},
      {title: 'Chương 6: Chuyến Phiêu Lưu Mới'},
      {title: 'Chương 7: Đối Mặt Với Nguy Cơ'},
      {title: 'Chương 8: Khám Phá Thế Giới'},
    ],
  },
  {
    title: 'Tập 2',
    showMore: false,
    expanded: false,
    chapters: [
      {title: 'Chương 1: Khởi Đầu Mới'},
      {title: 'Chương 2: Cuộc Phiêu Lưu'},
      {title: 'Chương 3: Tìm Kiếm'},
      {title: 'Chương 4: Đột Phá'},
      {title: 'Chương 5: Tái Ngộ'},
      {title: 'Chương 6: Chuyến Phiêu Lưu Mới'},
      {title: 'Chương 7: Đối Mặt Với Nguy Cơ'},
      {title: 'Chương 8: Khám Phá Thế Giới'},
    ],
  },
]);

const toggleVolume = (index: number) => {
  volumes.value[index].expanded = !volumes.value[index].expanded;
};

const toggleShowMore = (index: number) => {
  volumes.value[index].showMore = !volumes.value[index].showMore;
};
const tabs = ['Thảo Luận', 'Đánh Giá', ,];
const currentTab = ref('Thảo Luận');

// Mapping tab names to components
const currentTabComponent = computed(() => {
  switch (currentTab.value) {
    case 'Thảo Luận':
      return Comment;
    case 'Đánh Giá':
      return ReviewNovel;
    default:
      return Comment;
  }
});

</script>
<template>
  <div class="max-w-7xl mx-auto my-5">
    <Ads class="my-4"/>
    <Breadcrumb
        :breadcrumbs="[
    { label: 'Home', href: '/' },
    { label: 'Novel', href: '/novel' },
    { label: 'Daininki Idol na Classmate ni Natsukareta, Isshou Hatarakitakunai Ore', href: '/flowbite', isCurrent: true }
  ]"
    />

  </div>
  <div class="bg-gray-50 p-6 rounded-lg shadow-lg max-w-7xl mx-auto">
    <!-- Header Section -->
    <div class="flex flex-col lg:flex-row items-center space-y-6 lg:space-y-0 lg:space-x-8">
      <!-- Novel Image -->
      <div class="w-full lg:w-1/4 flex justify-center">
        <img src="/src/assets/logo.jpg" class="w-56 h-72 object-cover rounded-lg shadow-lg" alt="Novel Cover">
      </div>

      <!-- Novel Information -->
      <div class="w-full lg:w-3/4 space-y-4 text-left lg:text-left">
        <h1 class="text-xl font-semibold text-gray-800 hover:text-[#728156]">Daininki Idol na Classmate ni
          Natsukareta, Isshou Hatarakitakunai Ore</h1>
        <div class="space-x-2">
                    <span
                        class="inline-block py-1 px-3 text-sm font-semibold text-gray-700 bg-gray-200 rounded-full hover:bg-[#b6c99b]">Fantasy</span>
          <span
              class="inline-block py-1 px-3 text-sm font-semibold text-gray-700 bg-gray-200 rounded-full hover:bg-[#b6c99b]">Science
                        Fiction</span>
          <span
              class="inline-block py-1 px-3 text-sm font-semibold text-gray-700 bg-gray-200 rounded-full hover:bg-[#b6c99b]">Romance</span>
          <span
              class="inline-block py-1 px-3 text-sm font-semibold text-gray-700 bg-gray-200 rounded-full hover:bg-[#b6c99b]">Adventure</span>
        </div>

        <!-- Tác giả Section -->
        <p class=" text-gray-600 text-left">
          <span class="font-bold text-md">Author:</span>
          Sarimang
        </p>
        <p class=" text-gray-600 text-left">
          <span class="font-bold text-md">Status:</span>
          Completed
        </p>

        <div class="grid grid-cols-3 lg:grid-cols-6 gap-4">
          <!-- Collect -->
          <div class="flex justify-center items-center">
            <a id="collect" class="flex flex-col items-center text-center text-gray-700 cursor-pointer"
               @click="toggleCollect">
              <font-awesome-icon :icon="['fa', 'heart']"
                                 :class="isCollected ? 'text-red-700' : 'text-red-300'"
                                 class="text-xl transition-colors duration-200"/>
              <span class="text-sm">{{ isCollected ? '544' : '543' }}</span>
            </a>
          </div>


          <!-- Rating -->
          <div class="flex justify-center items-center">
            <div class="flex flex-col items-center text-center text-gray-700 hover:text-blue-600">
              <a>
                <label for="open-rating" class="flex flex-col items-center">
                  <font-awesome-icon :icon="['far', 'star']" class=" text-yellow-500"/>
                  <span class="text-sm">Đánh giá</span>
                </label>
              </a>
            </div>
          </div>

          <!-- Catalog -->
          <div class="flex justify-center items-center">
            <div class="flex flex-col items-center text-center text-gray-700 hover:text-blue-600">
              <font-awesome-icon :icon="['fas', 'list']"/>
              <span class="text-sm">Mục lục</span>
            </div>
          </div>

          <!-- Discussions -->
          <div class="flex justify-center items-center">
            <RouterLink to="#"
                        class="flex flex-col items-center text-center text-gray-700 hover:text-blue-600">
              <font-awesome-icon :icon="['far', 'comments']"/>
              <span class="text-sm">Bàn luận</span>
            </RouterLink>
          </div>

          <!-- Share -->
          <div class="flex justify-center items-center">
            <label for="open-sharing"
                   class="flex flex-col items-center text-center text-gray-700 hover:text-blue-600">
              <font-awesome-icon :icon="['fas', 'share-nodes']"/>
              <span class="text-sm">Chia sẻ</span>
            </label>
            <input type="checkbox" id="open-sharing" class="hidden">
            <div class="sharing-box hidden mt-2">
              <a x-data="" href="#"
                 class="sharing-item block text-sm text-blue-600 hover:text-blue-800">Link rút gọn</a>
              <a class="sharing-item block text-xl text-blue-600 hover:text-blue-800" href="#"
                 target="_blank"><i class="fab fa-facebook-f"></i></a>
              <a class="sharing-item block text-xl text-blue-600 hover:text-blue-800" href="#"
                 target="_blank"><i class="fab fa-twitter"></i></a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Novel Description Section -->
    <div class="mt-10 text-left lg:text-left">
      <h3 class="font-bold text-md items-start justify-start flex">Description:</h3>
      <p class="text-md text-gray-600 mt-4 text-left">
        Đây là phần mô tả chi tiết về nội dung của truyện, nơi bạn có thể tìm thấy
        các thông tin bổ sung về câu chuyện, các nhân vật chính, và những điều thú vị khác.
      </p>
    </div>

    <!-- New Chapters Section -->
    <div class="w-full max-w-7xl mx-auto mt-10">
      <h2 class="text-xl font-semibold text-gray-800 mb-6">Chapter List</h2>

      <div class="space-y-6">
        <div v-for="(volume, volumeIndex) in volumes" :key="volumeIndex"
             class="border border-gray-200 rounded-lg overflow-hidden">
          <!-- Volume Header -->
          <button @click="toggleVolume(volumeIndex)"
                  class="w-full px-4 py-3 bg-gray-50 flex items-center justify-between hover:bg-gray-100 transition-colors">
            <h3 class="text-lg font-medium text-gray-800">{{ volume.title }}</h3>
            <font-awesome-icon :icon="volume.expanded ? 'fa-solid fa-sort-up' : 'fa-solid fa-sort-down'"/>
          </button>

          <!-- Chapter List -->
          <div v-show="volume.expanded" class="transition-all duration-300 ease-in-out">
            <div class="p-4">
              <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                <!-- Initial Chapters -->
                <div v-for="(chapter, chapterIndex) in volume.chapters.slice(0, volume.showMore ? undefined : 6)"
                     :key="chapterIndex" class="group relative">
                  <div class="p-3 bg-white rounded-lg shadow-sm hover:shadow-md transition-shadow
                           border border-gray-100 hover:border-gray-200">
                                        <span class="text-sm text-gray-700 truncate block">
                                            {{ chapter.title }}
                                        </span>
                    <!-- Tooltip -->
                    <div class="absolute left-1/2 transform -translate-x-1/2 bottom-full mb-2
                              hidden group-hover:block z-10 bg-black text-white text-xs py-1 px-2 rounded">
                      {{ chapter.title }}
                    </div>
                  </div>
                </div>
              </div>

              <!-- Show More Button -->
              <div v-if="volume.chapters.length > 6" class="mt-4 text-center">
                <button @click="toggleShowMore(volumeIndex)" class="inline-flex items-center justify-center gap-2 px-4 py-2 text-sm text-gray-600
                 hover:text-gray-800 transition-colors">
                  <span>{{ volume.showMore ? 'Show Less' : 'Show More' }}</span>
                  <font-awesome-icon
                      :icon="volume.showMore ? 'fa-solid fa-angles-up' : 'fa-solid fa-angles-down'"/>
                </button>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Comments Section -->
    <div class="container mx-auto p-4">
      <!-- Tabs -->
      <div class="flex space-x-4 border-b mb-4">
        <button
            v-for="(tab, index) in tabs"
            :key="index"
            :class="[
          'px-4 py-2 text-sm font-semibold',
          currentTab === tab ? 'border-b-2 border-blue-500 text-blue-500' : 'text-gray-600',
        ]"
            @click="currentTab = tab"
        >
          {{ tab }}
        </button>
      </div>

      <!-- Dynamic Component -->
      <component :is="currentTabComponent"/>
    </div>
  </div>
</template>


<style scoped>
.sharing-box {
  display: none;
}

#open-sharing:checked + .sharing-box {
  display: block;
}
</style>
