<script setup lang="ts">
import { ref, computed } from 'vue';

// Sample data for invoices (transactions)
const invoices = ref([
  { id: '001', type: 'Nhập hàng', amount: '500.000', status: 'Chờ thanh toán', time: '01/12/2024' },
  { id: '002', type: 'Mua hàng', amount: '300.000', status: 'Đã thanh toán', time: '15/11/2024' },
  { id: '003', type: 'Mua hàng', amount: '700.000', status: 'Chờ thanh toán', time: '20/11/2024' },
  { id: '004', type: 'Nhập hàng', amount: '800.000', status: 'Đã thanh toán', time: '22/11/2024' },
  { id: '005', type: 'Mua hàng', amount: '100.000', status: 'Chờ thanh toán', time: '25/11/2024' },
  { id: '006', type: 'Mua hàng', amount: '150.000', status: 'Đã thanh toán', time: '27/11/2024' },
  { id: '007', type: 'Nhập hàng', amount: '500.000', status: 'Chờ thanh toán', time: '01/12/2024' },
  { id: '008', type: 'Mua hàng', amount: '200.000', status: 'Đã thanh toán', time: '02/12/2024' },
  { id: '009', type: 'Mua hàng', amount: '400.000', status: 'Chờ thanh toán', time: '03/12/2024' },
  { id: '010', type: 'Nhập hàng', amount: '600.000', status: 'Đã thanh toán', time: '04/12/2024' }
]);

const page = ref(1); // Current page for pagination
const pageInput = ref<number>(1); // User input for page jump
const itemsPerPage = 5; // Define how many items per page (for simplicity)

// Calculate total pages
const totalPagesComputed = computed(() => Math.ceil(invoices.value.length / itemsPerPage));

// Paginated invoices
const paginatedInvoices = computed(() => {
  const start = (page.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return invoices.value.slice(start, end);
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
</script>

<template>
  <div class="max-w-7xl mx-auto p-8 bg-white shadow-lg rounded-lg">
    <!-- Table for displaying invoices -->
    <div class="mt-8 p-4">
      <h2 class="text-2xl font-semibold text-gray-700 mb-4">Hóa đơn</h2>
      <table class="min-w-full table-auto border-collapse">
        <thead>
          <tr>
            <th class="px-4 py-2 text-left border-b">ID</th>
            <th class="px-4 py-2 text-left border-b">Loại</th>
            <th class="px-4 py-2 text-left border-b">Số tiền</th>
            <th class="px-4 py-2 text-left border-b">Tình trạng</th>
            <th class="px-4 py-2 text-left border-b">Thời gian</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(invoice, index) in paginatedInvoices" :key="index">
            <td class="px-4 py-2 border-b">{{ invoice.id }}</td>
            <td class="px-4 py-2 border-b">{{ invoice.type }}</td>
            <td class="px-4 py-2 border-b">{{ invoice.amount }}</td>
            <td class="px-4 py-2 border-b">{{ invoice.status }}</td>
            <td class="px-4 py-2 border-b">{{ invoice.time }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Pagination controls -->
    <div class="mt-4 flex justify-between items-center my-4">
      <div class="flex items-center">
        <button :disabled="page === 1" @click="previousPage"
                class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300 flex items-center justify-center">
          <font-awesome-icon :icon="['fas', 'chevron-left']" />
        </button>

        <ul class="inline-flex -space-x-px text-sm">
          <li v-for="n in totalPagesComputed" :key="n">
            <a href="#" :class="{'bg-blue-500 text-white': n === page, 'text-blue-500 hover:bg-blue-100': n !== page}"
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
</template>

<style scoped>
/* Add any additional custom styling here */
</style>
