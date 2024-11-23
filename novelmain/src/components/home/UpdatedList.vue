<template>
    <div class="max-w-7xl mx-auto py-8">
        <h1 class="text-2xl font-semibold mb-6 text-start">Cập nhật mới nhất</h1>
        <div v-if="novels.length" class="space-y-0 md:grid md:grid-cols-1 md:gap-0">
            <div v-for="novel in novels" :key="novel.id"
                class="flex flex-wrap items-center justify-between border rounded-lg p-4 hover:bg-gray-50 transition md:border-b-0">
                <div class="flex-1">
                    <h2 class="text-sm sm:text-base md:text-lg font-semibold text-blue-600">{{ novel.title }}</h2>
                </div>
                <div class="flex items-center justify-between">
                    <div class="text-gray-700 text-xs sm:text-sm md:text-base">
                        <p>{{ novel.latestChapter }}</p>
                    </div>
                    <div class="text-gray-500 text-xs sm:text-sm md:text-base ml-2">
                        <p>Cập nhật lúc: {{ formatDate(novel.updatedAt) }}</p>
                    </div>
                </div>
            </div>
        </div>
        <p v-else class="text-gray-500 text-center">Không có tiểu thuyết nào được cập nhật.</p>
    </div>
</template>
<script setup lang="ts">
import { ref } from 'vue';
// Dữ liệu mẫu
interface Novel {
    id: number;
    title: string;
    updatedAt: string; // ISO 8601 Date String
    latestChapter: string;
}

const novels = ref<Novel[]>([
    { id: 1, title: 'Tiểu thuyết 1', updatedAt: '2024-11-24T10:00:00Z', latestChapter: 'Chương 5: Hành trình' },
    { id: 2, title: 'Tiểu thuyết 2', updatedAt: '2024-11-23T15:30:00Z', latestChapter: 'Chương 12: Đối đầu' },
    { id: 3, title: 'Tiểu thuyết 3', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu' },
    { id: 4, title: 'Tiểu thuyết 4', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu' },
    { id: 5, title: 'Tiểu thuyết 5', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu' },
    { id: 6, title: 'Tiểu thuyết 6', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu' },
    { id: 7, title: 'Tiểu thuyết 7', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu' },
    { id: 8, title: 'Tiểu thuyết 8', updatedAt: '2024-11-22T08:20:00Z', latestChapter: 'Chương 3: Bắt đầu' },

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
</script>

<style scoped>
/* Optional: Adjust text size for mobile */
.text-sm {
    font-size: 0.875rem;
    /* Smaller text size for mobile */
}

.text-base {
    font-size: 1rem;
    /* Slightly smaller base text */
}

.text-lg {
    font-size: 1.125rem;
    /* Adjust the large text size for mobile */
}
</style>