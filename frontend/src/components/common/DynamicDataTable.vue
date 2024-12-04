<script setup lang="ts">
import { defineProps, computed } from "vue";
// Định nghĩa interface cho column và row
interface Column {
  field: string;
  headerName: string;
  width?: number;
  renderCell?: (row: any) => any; // Hàm tùy chỉnh hiển thị
  isAction?: boolean; // Boolean để xác định là cột hành động
}

interface Row {
  [key: string]: any;
}

// Nhận props
const props = defineProps<{
  columns: Column[];
  rows: Row[];
}>();

const emit = defineEmits(["edit", "delete"]);

// Lọc cột hiển thị
const visibleColumns = computed(() =>
    props.columns.filter((column) => column.isAction !== false)
);
</script>
<template>
  <div class="overflow-x-auto relative sm:rounded-lg">
    <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
      <thead class="text-xs text-gray-700 uppercase bg-[#F8F8F7] dark:bg-gray-700 dark:text-gray-400">
      <tr>
        <th
            v-for="column in visibleColumns"
            :key="column.field"
            class="py-3 px-6"
            :style="{ width: column.width ? `${column.width}px` : 'auto' }"
        >
          {{ column.headerName }}
        </th>
      </tr>
      </thead>
      <tbody>
      <tr
          v-for="(row, rowIndex) in props.rows"
          :key="rowIndex"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
      >
        <td
            v-for="column in visibleColumns"
            :key="column.field"
            class="py-4 px-6 dark:text-white"
        >
          <!-- Nếu là cột hành động -->
          <template v-if="column.isAction">
            <div class="flex justify-center items-center space-x-3">
              <button
                  @click="$emit('edit', row)"
                  class="bg-transparent border-[1px] border-blue-500 text-blue-500 hover:bg-blue-500 hover:text-white font-medium py-2 px-3 rounded transition duration-300 flex items-center"
              >
                <font-awesome-icon :icon="['fas', 'pen']" />
              </button>
              <button
                  @click="$emit('delete', row)"
                  class="bg-transparent border-[1px] border-red-500 text-red-500 hover:bg-red-500 hover:text-white font-medium py-2 px-3 rounded transition duration-300 flex items-center"
              >
                <font-awesome-icon :icon="['fas', 'trash']" />
              </button>
            </div>
          </template>
          <!-- Nếu không phải cột hành động -->
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
</template>

