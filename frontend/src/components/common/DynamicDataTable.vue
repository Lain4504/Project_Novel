<script setup lang="ts">
import { defineProps, defineEmits, computed, ref, watch } from 'vue';

const props = defineProps<{
  columns: Column[];
  rows: Row[];
  createPath?: string | null;
  currentPage: number;
  totalPages: number;
  emits: string[]; // Accept an array of strings to check available events
  showGoToPage?: boolean;
}>();

const emit = defineEmits(['edit', 'delete', 'page-change', 'view']);

interface Column {
  field: string;
  headerName: string;
  width?: number;
  renderCell?: (row: any) => any;
  isAction?: boolean;
}

interface Row {
  [key: string]: any;
}

const visiblePages = computed(() => {
  const range = 2;
  const threshold = 5; // Define a threshold for the number of pages to display
  const start = Math.max(props.currentPage - range, 1);
  const end = Math.min(props.currentPage + range, props.totalPages);

  const pages = [];
  if (props.totalPages <= threshold) {
    for (let i = 1; i <= props.totalPages; i++) {
      pages.push(i);
    }
  } else {
    if (start > 1) {
      pages.push(1);
      if (start > 2) {
        pages.push('...');
      }
    }
    for (let i = start; i <= end; i++) {
      pages.push(i);
    }
    if (end < props.totalPages) {
      if (end < props.totalPages - 1) {
        pages.push('...');
      }
      pages.push(props.totalPages);
    }
  }
  return pages;
});

const goToPage = (page: number) => {
  if (page >= 1 && page <= props.totalPages) {
    emit('page-change', page);
  }
};

const inputPage = ref<number | null>(null);

const checkAndAdjustPage = () => {
  if (props.rows.length === 0 && props.currentPage > 1) {
    emit('page-change', props.currentPage - 1);
  }
};

// Watch for changes in rows and adjust page if necessary
watch(() => props.rows, checkAndAdjustPage, { immediate: true });
</script>

<template>
  <div v-if="props.createPath" class="mt-4 flex justify-end my-4">
    <router-link :to="props.createPath">
      <button
          class="flex items-center justify-center w-10 h-10 bg-[#C96442] text-white rounded-full hover:bg-[#BA5B38]">
        <font-awesome-icon :icon="['fas', 'plus']" class="text-lg"/>
      </button>
    </router-link>
  </div>
  <div class="relative sm:rounded-lg">
    <div class="overflow-x-auto">
      <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-[#F8F8F7] dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th v-for="column in props.columns" :key="column.field" class="py-3 px-6"
              :style="{ width: column.width ? `${column.width}px` : 'auto' }">
            {{ column.headerName }}
          </th>
        </tr>
        </thead>
<tbody>
  <tr v-if="props.rows.length === 0" class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
    <td :colspan="props.columns.length" class="py-4 px-6 text-center text-gray-500 dark:text-gray-400">
      Không có dữ liệu
    </td>
  </tr>
  <tr v-else v-for="(row, rowIndex) in props.rows" :key="rowIndex"
      class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
    <td v-for="column in props.columns" :key="column.field" class="py-4 px-6 dark:text-white">
      <template v-if="column.isAction">
        <div class="flex justify-center items-center space-x-3">
          <button v-if="props.emits.includes('view')" @click="$emit('view', row)"
                  class="bg-transparent border-[1px] border-yellow-500 text-yellow-500 hover:bg-yellow-500 hover:text-white font-medium py-2 px-3 rounded transition duration-300 flex items-center">
            <font-awesome-icon :icon="['far', 'eye']"/>
          </button>
          <button v-if="props.emits.includes('edit')" @click="$emit('edit', row)"
                  class="bg-transparent border-[1px] border-blue-500 text-blue-500 hover:bg-blue-500 hover:text-white font-medium py-2 px-3 rounded transition duration-300 flex items-center">
            <font-awesome-icon :icon="['fas', 'pen']"/>
          </button>
          <button v-if="props.emits.includes('delete')" @click="$emit('delete', row)"
                  class="bg-transparent border-[1px] border-red-500 text-red-500 hover:bg-red-500 hover:text-white font-medium py-2 px-3 rounded transition duration-300 flex items-center">
            <font-awesome-icon :icon="['fas', 'trash']"/>
          </button>
        </div>
      </template>
      <template v-else-if="column.renderCell">
        {{ column.renderCell(row) }}
      </template>
      <template v-else>
        {{ row[column.field] }}
      </template>
    </td>
  </tr>
</tbody>      </table>
    </div>

    <!-- Pagination -->
    <div class="flex flex-col items-center my-4">
      <div class="flex justify-center mb-2">
        <button @click="goToPage(1)" :disabled="props.currentPage === 1"
                class="px-3 py-1 mx-1 text-sm text-gray-400 rounded hover:bg-[#BA5B38]">
          Đầu
        </button>
        <button v-for="page in visiblePages" :key="page" @click="goToPage(page)"
                class="px-3 py-1 mx-1 text-sm rounded hover:bg-[#BA5B38] hover:text-[#F8F8F7]"
                :class="{'bg-[#C96442] text-white': page === props.currentPage, 'text-[#BA5B38]': page !== props.currentPage}">
          {{ page }}
        </button>
        <button @click="goToPage(props.totalPages)" :disabled="props.currentPage === props.totalPages"
                class="px-3 py-1 mx-1 text-sm text-gray-400 rounded hover:bg-[#BA5B38]">
          Cuối
        </button>
      </div>
      <div v-if="props.showGoToPage" class="flex items-center space-x-2">
        <div class="text-sm text-gray-500">
          Page {{ props.currentPage }} of {{ props.totalPages }}
        </div>
        <input v-model.number="inputPage" type="number" min="1" :max="props.totalPages"
               class="px-3 py-1 text-sm border rounded" placeholder="Page"/>
        <button @click="inputPage !== null && goToPage(inputPage)"
                class="px-3 py-1 text-sm rounded bg-[#C96442] text-white hover:bg-[#BA5B38]">
          Go
        </button>
      </div>
    </div>
  </div>
</template>