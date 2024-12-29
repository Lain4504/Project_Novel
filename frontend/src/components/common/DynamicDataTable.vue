<script lang="ts" setup>
import {defineEmits, defineProps, ref, computed, watch} from 'vue';

const props = defineProps<{
  columns: Column[];
  rows: Row[];
  createPath?: string | null;
  currentPage: number;
  totalPages: number;
  emits: string[];
  showGoToPage?: boolean;
  deleteApi: (id: string) => Promise<any>;
  fetchApi: (page: number, size: number) => Promise<any>;
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
const currentPage = ref(props.currentPage);
const showConfirmModal = ref(false);
const rowToDelete = ref<any>(null);
const rows = ref(props.rows);
const totalPages = ref(props.totalPages);

const onShowSizeChange = async (current: number, size: number) => {
  pageSize.value = size;
  await fetchData(current, size);
};

const handlePageChange = async (page: number) => {
  currentPage.value = page;
  await fetchData(page, pageSize.value);
};

const fetchData = async (page: number, size: number) => {
  try {
    const response = await props.fetchApi(page, size);
    rows.value = response.data;
    totalPages.value = response.totalPages;
    emit('page-change', page, size);
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

watch(() => props.currentPage, async (newPage) => {
  currentPage.value = newPage;
  await fetchData(newPage, pageSize.value);
});

watch(() => props.rows, (newRows) => {
  rows.value = newRows;
});

watch(() => props.totalPages, (newTotalPages) => {
  totalPages.value = newTotalPages;
});

const formattedColumns = computed(() => {
  return props.columns.map(column => ({
    ...column,
    title: column.headerName
  }));
});

const handleDelete = (row: any) => {
  rowToDelete.value = row;
  showConfirmModal.value = true;
};

const confirmDelete = async () => {
  if (rowToDelete.value) {
    try {
      await props.deleteApi(rowToDelete.value.id);
      emit('delete', rowToDelete.value);
      // Check if current page is now empty and adjust currentPage if necessary
      if (rows.value.length === 1 && currentPage.value > 1) {
        currentPage.value -= 1;
      }
      await fetchData(currentPage.value, pageSize.value);
    } catch (error) {
      console.error('Error deleting item:', error);
    }
  }
  showConfirmModal.value = false;
};

const cancelDelete = () => {
  showConfirmModal.value = false;
};
</script>

<template>
  <div class="p-6">
    <div v-if="props.createPath" class="mt-4 flex justify-end my-4">
      <router-link :to="props.createPath">
        <a-button
          class="bg-[#18A058] hover:bg-[#18A058] border-none text-white flex items-center justify-center"
          shape="circle"
          style="width: 40px; height: 40px;"
          type="primary"
        >
          <font-awesome-icon :icon="['fas', 'plus']" class="text-xl"/>
        </a-button>
      </router-link>
    </div>
    <div class="relative sm:rounded-lg">
      <a-table class="custom-table" :columns="formattedColumns" :dataSource="rows" rowKey="id" :pagination="false">
        <template #bodyCell="{ column, record }">
          <template v-if="column.isAction">
            <div class="flex justify-center items-center space-x-3">
              <a-button v-if="props.emits.includes('view')" type="default" @click="$emit('view', record)">
                <font-awesome-icon :icon="['far', 'eye']" style="color: #FFD43B" />
              </a-button>
              <a-button v-if="props.emits.includes('edit')" type="default" @click="$emit('edit', record)">
                <font-awesome-icon :icon="['fas', 'pen']" style="color: #1890ff"/>
              </a-button>
              <a-button v-if="props.emits.includes('delete')" type="default" @click="handleDelete(record)">
                <font-awesome-icon :icon="['fas', 'trash']" style="color: #ff0f0f"/>
              </a-button>
            </div>
          </template>
          <template v-else-if="column.renderCell">
            {{ column.renderCell(record) }}
          </template>
          <template v-else>
            {{ record[column.field] }}
          </template>
        </template>
      </a-table>
    </div>

    <!-- Pagination -->
    <div class="flex flex-col items-center my-4">
      <a-pagination
        :current="currentPage"
        :pageSize="pageSize"
        :total="totalPages * pageSize"
        show-size-changer
        @change="handlePageChange"
        @showSizeChange="onShowSizeChange"
      />
    </div>

    <!-- Confirm Delete Modal -->
    <a-modal
      v-model:visible="showConfirmModal"
      title="Are you sure you want to delete?"
      okText="Delete"
      cancelText="Cancel"
      @ok="confirmDelete"
      @cancel="cancelDelete"
    >
      <p>Thao tác này sẽ gửi yêu cầu xóa tiểu thuyết tới admin để xem xét, thời gian giải quyết tối đa 3 ngày làm việc.</p>
    </a-modal>
  </div>
</template>

<style scoped>
.custom-table {
  border: 1px solid #d9d9d9;
  border-radius: 4px;
}
</style>