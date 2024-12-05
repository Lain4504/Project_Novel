<template>
    <div class="max-w-7xl mx-auto py-8">
      <h1 class="text-xl font-semibold mb-6 text-start">Cập nhật mới nhất</h1>
  
      <!-- Hiển thị bảng trên màn hình lớn -->
      <table v-if="!isMobile && novels.length > 0" class="w-full border-collapse">
        <tbody>
          <tr v-for="novel in novels" :key="novel.id" class="border-b hover:bg-gray-50">
            <!-- <td :style="{ width: columnWidths.category + 'px' }" class="px-4 py-2 text-gray-700">ssss</td>  -->
            <td :style="{ width: columnWidths.title + 'px' }" class="px-4 py-2 text-sm font-semibold text-blue-600">{{ novel.title }}</td>
            <td :style="{ width: columnWidths.author + 'px' }" class="px-4 py-2 text-sm text-gray-700">{{ novel.author }}</td>
            <td :style="{ width: columnWidths.latestChapter + 'px' }" class="px-4 py-2 text-sm text-gray-700">{{ novel.latestChapter }}</td>
            <td :style="{ width: columnWidths.updatedAt + 'px', textAlign: 'right' }" class="px-4 py-2 text-sm text-gray-500">{{ formatDate(novel.updatedAt) }}</td>
          </tr>
        </tbody>
      </table>
  
      <!-- Hiển thị cho mobile -->
      <div v-else-if="novels.length > 0">
  <div v-for="novel in novels" :key="novel.id" class="border-b hover:bg-gray-50 mb-4 p-4">
    <!-- Title -->
    <div class="text-sm font-semibold text-blue-600 mb-2">{{ novel.title }}</div>

    <!-- Author, Chapter (hàng ngang) -->
    <div class="flex justify-between mb-2">
      <div class="text-sm text-gray-700">{{ novel.author }}</div>
      <div class="text-sm text-gray-700">{{ novel.latestChapter }}</div>
    </div>

    <!-- Category và UpdateAt (hàng ngang) -->
    <div class="flex justify-between">
      <div class="text-sm text-gray-700"></div> 
      <div class="text-sm text-gray-500">{{ formatDate(novel.updatedAt) }}</div>
    </div>
  </div>
</div>


  
      <!-- Hiển thị thông báo nếu không có tiểu thuyết -->
      <p v-else class="text-gray-500 text-center">Không có tiểu thuyết nào được cập nhật.</p>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue';
  
  // Dữ liệu mẫu
  interface Novel {
    id: number;
    title: string;
    updatedAt: string; // ISO 8601 Date String
    latestChapter: string;
    author: string;
  }
  
  const novels = ref<Novel[]>([
    { id: 1, title: 'Tu Hành, Từ Phàm Cốt Bắt Đầu Tăng Lên', updatedAt: '2024-11-24T10:00:00Z', latestChapter: 'Chương 5: Hành trình', author: 'Nguyễn Văn A' },
    { id: 2, title: 'Tiểu thuyết 2', updatedAt: '2024-11-23T15:30:00Z', latestChapter: 'Chương 12: Đối đầu', author: 'Trần Thị B' },
    { id: 3, title: 'Pháp Sư Nghề Nghiệp Yếu Sao? Đánh Thường Bạo Tinh Nói Thế Nào3', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu', author: 'Lê Minh C' },
    { id: 4, title: 'Tiểu thuyết 4', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu', author: 'Phạm Hoàng D' },
    { id: 5, title: 'Tiểu thuyết 5', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu', author: 'Nguyễn Thị E' },
    { id: 6, title: 'Tiểu thuyết 6', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu', author: 'Bùi Quang F' },
    { id: 7, title: 'Tiểu thuyết 7', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu', author: 'Nguyễn Hoàng G' },
    { id: 8, title: 'Tiểu thuyết 8', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu', author: 'Trần Kim H' },
  ]);
  
  // Hàm format thời gian
  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toLocaleString('vi-VN', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };
  
  // Biến cho phép điều chỉnh chiều rộng cột
  const columnWidths = ref({
    category: 50,
    title: 500,
    author: 250,
    latestChapter: 200,
    updatedAt: 200,
  });
  
  // Kiểm tra màn hình mobile
  const isMobile = ref(false);
  
  onMounted(() => {
    const checkMobile = () => {
      isMobile.value = window.innerWidth < 768; // 768px là ngưỡng độ rộng màn hình mobile
    };
    checkMobile();
    window.addEventListener('resize', checkMobile);
  });
  </script>
  
  <style scoped>
  @media screen and (max-width: 768px) {
    /* Định dạng cho màn hình di động */
    .text-start {
      text-align: left;
    }
  
    .w-full {
      width: 100%;
    }
  
    .max-w-7xl {
      max-width: 100%;
    }

    .px-4 {
      padding-left: 1rem;
      padding-right: 1rem;
    }

    .py-2 {
      padding-top: 0.5rem;
      padding-bottom: 0.5rem;
    }
  }
  </style>
  