<script setup lang="ts">
import { ref, computed } from 'vue';
import Ads from '@/components/home/Banner.vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';

// Define the structure of a transaction
interface Transaction {
  id: number;
  time: string;
  content: string;
  transaction: number;
}

// Sample transaction data (can be fetched from an API)
const transactions = ref<Transaction[]>([
  { id: 2617767, time: '2024-11-28 20:13:15', content: 'Mở khóa Chương 296: Thần tiên trứ yêu! Khách hành hướng tán đi!', transaction: -150 },
  { id: 2617722, time: '2024-11-28 20:05:48', content: 'Mở khóa Chương 399: Đều sẽ nói chuyện', transaction: -120 },
  { id: 2617641, time: '2024-11-28 19:58:46', content: 'Mở khóa Chương 398: Tâm thuật bất chính', transaction: -120 },
  { id: 2617583, time: '2024-11-28 19:50:33', content: 'Mở khóa Chương 939: Dừng đợi (2)', transaction: -150 },
  { id: 2617471, time: '2024-11-28 19:39:58', content: 'Mở khóa Chương 939: Dừng đợi (1)', transaction: -150 },
  { id: 2613614, time: '2024-11-28 12:52:22', content: 'Mở khóa Chương 295: Người trong giang hồ không bị trói buộc', transaction: -140 },
  { id: 2613251, time: '2024-11-28 12:40:35', content: 'Mở khóa Chương 24: Bổ Tát năm trang (2)', transaction: -50 },
  { id: 2613080, time: '2024-11-28 12:35:56', content: 'Mở khóa Chương 24: Bổ Tát năm trang (1)', transaction: -100 },
  { id: 2606363, time: '2024-11-27 22:50:19', content: 'Mở khóa Chương 294: Trừ vặt túi', transaction: -130 },
  { id: 2603890, time: '2024-11-27 18:23:13', content: 'Mở khóa Chương 938: Mai táng (2)', transaction: -150 },
]);

const page = ref(1); // Current page for pagination
const pageInput = ref<number>(1); // User input for page jump
const itemsPerPage = 5; // Define how many items per page (for simplicity)

// Calculate total pages
const totalPagesComputed = computed(() => Math.ceil(transactions.value.length / itemsPerPage));

// Paginated transactions
const paginatedTransactions = computed(() => {
  const start = (page.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return transactions.value.slice(start, end);
});

// Functions to handle pagination
const nextPage = () => {
  if (page.value < totalPagesComputed.value) {
    page.value++;
    pageInput.value = page.value;
  }
};

const previousPage = () => {
  if (page.value > 1) {
    page.value--;
    pageInput.value = page.value;
  }
};

// Handle input for page jump
const onPageInput = () => {
  const inputPage = Math.min(Math.max(pageInput.value, 1), totalPagesComputed.value);
  page.value = inputPage;
};

// Go to a specific page when clicking a page number
const goToPage = (n: number) => {
  page.value = n;
  pageInput.value = n;
};

// Top up function (example action when button is clicked)
const topUp = () => {
  window.alert("Nạp Tiền button clicked");
};
</script>

<template>
    <div class="max-w-7xl mx-auto">
        <Ads class="my-4" />
        <Breadcrumb :breadcrumbs="[
            { label: 'Home', to: '/' },
            { label: 'Account', to: '/templates' },
            { label: 'Transaction History', to: '/flowbite', isCurrent: true }
        ]" />
        <div>
            <div class="flex justify-between items-center my-2">
                <h4 class="text-lg font-semibold">TIỂU THUYẾT ĐÁNH DẤU CỦA BẠN</h4>
                <button @click="topUp"
                    class="text-sm bg-green-400 border-[1px] hover:bg-green-400 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300 flex items-center justify-center">
                    Deposit
                </button>

            </div>
            <table class="min-w-full table-auto border-collapse bg-white rounded-lg shadow-md">
                <thead>
                    <tr>
                        <th class="py-2 px-4 border-b text-left">ID</th>
                        <th class="py-2 px-4 border-b text-left">Thời gian</th>
                        <th class="py-2 px-4 border-b text-left">Nội dung</th>
                        <th class="py-2 px-4 border-b text-left">Giao dịch</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in paginatedTransactions" :key="item.id" class="text-sm">
                        <td class="py-2 px-4 border-b">{{ item.id }}</td>
                        <td class="py-2 px-4 border-b">{{ item.time }}</td>
                        <td class="py-2 px-4 border-b">{{ item.content }}</td>
                        <td class="py-2 px-4 border-b">{{ item.transaction }}</td>
                    </tr>
                </tbody>
            </table>

            <!-- Pagination controls -->
            <div class="mt-4 flex justify-between items-center my-4">
                <div class="flex items-center">
                    <button :disabled="page === 1" @click="previousPage"
                        class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300 flex items-center justify-center">
                        <font-awesome-icon :icon="['fas', 'chevron-left']" />
                    </button>

                    <ul class="inline-flex -space-x-px text-sm">
                        <li v-for="n in totalPagesComputed" :key="n">
                            <a href="#"
                                :class="{ 'bg-blue-500 text-white': n === page, 'text-blue-500 hover:bg-blue-100': n !== page }"
                                @click="goToPage(n)"
                                class="flex items-center justify-center px-3 h-8 leading-tight border border-gray-300 hover:bg-gray-100 rounded-md">
                                {{ n }}
                            </a>
                        </li>
                    </ul>

                    <button :disabled="page === totalPagesComputed" @click="nextPage"
                        class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300 flex items-center justify-center">
                        <font-awesome-icon :icon="['fas', 'chevron-right']" />
                    </button>
                </div>

                <!-- Quick jump input -->
                <div class="flex items-center text-sm">
                    <input v-model="pageInput" type="number" min="1" :max="totalPagesComputed"
                        class="px-3 py-1 border rounded-md w-20" @input="onPageInput" placeholder="Jump to" />
                    <span class="ml-2">/ {{ totalPagesComputed }} pages</span>
                </div>
            </div>
        </div>
    </div>
</template>


<style scoped>
/* Optional: Add some custom styles here */
</style>
