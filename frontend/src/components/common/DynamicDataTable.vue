<template>
  <div v-if="props.createPath" class="mt-4 flex justify-end my-4">
    <router-link :to="props.createPath">
    <a-button
    type="primary"
    shape="circle"
    class="bg-blue-400 hover:bg-blue-600 border-none text-white flex items-center justify-center"
    style="width: 40px; height: 40px;"
>
      <font-awesome-icon :icon="['fas', 'plus']" class="text-xl"/>
</a-button>
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
        </tbody>
      </table>
    </div>

    <!-- Pagination -->
    <div class="flex flex-col items-center my-4">
      <a-pagination
          :current="currentPage"
          :pageSize="pageSize"
          :total="totalPages * pageSize"
          show-size-changer
          @showSizeChange="onShowSizeChange"
          @change="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import {defineProps, defineEmits, ref, watch} from 'vue';

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

const pageSize = ref(10);

const onShowSizeChange = (current: number, size: number) => {
  pageSize.value = size;
  emit('page-change', current, size);
};

const handlePageChange = (page: number) => {
  emit('page-change', page, pageSize.value);
};

watch(() => props.currentPage, (newPage) => {
  emit('page-change', newPage, pageSize.value);
});
</script>