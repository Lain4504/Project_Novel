<script lang="ts" setup>
import { computed, defineEmits, defineProps, ref, watch } from 'vue';

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
const isLoading = ref(false); // Add isLoading state

const onShowSizeChange = async (current: number, size: number) => {
  pageSize.value = size;
  await fetchData(current, size);
};

const handlePageChange = async (page: number) => {
  currentPage.value = page;
  await fetchData(page, pageSize.value);
};

const fetchData = async (page: number, size: number) => {
  isLoading.value = true; // Set loading to true
  try {
    const response = await props.fetchApi(page, size);
    rows.value = response.data;
    totalPages.value = response.totalPages;
    emit('page-change', page, size);
  } catch (error) {
    console.error('Error fetching data:', error);
  } finally {
    isLoading.value = false; // Set loading to false
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
  <div class="p-3">
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
    <a-spin :spinning="isLoading"> <!-- Add a-spin component -->
      <div class="w-full overflow-x-auto">
        <div class="relative sm:rounded-lg min-w-full">
          <a-table
              :columns="formattedColumns"
              :dataSource="rows"
              rowKey="id"
              :pagination="false"
              :scroll="{ x: 'max-content' }"
              class="min-w-full"
          >
            <template #bodyCell="{ column, record }">
              <template v-if="column.isAction">
                <div class="flex justify-center items-center space-x-3 whitespace-nowrap">
                  <a-button v-if="props.emits.includes('view')" type="default" @click="$emit('view', record)">
                    <font-awesome-icon :icon="['far', 'eye']" style="color: #FFD43B"/>
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
                <div class="whitespace-nowrap">
                  {{ column.renderCell(record) }}
                </div>
              </template>
              <template v-else>
                <div class="whitespace-nowrap">
                  {{ record[column.field] }}
                </div>
              </template>
            </template>
          </a-table>
        </div>
      </div>
    </a-spin>

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

    <a-modal
        v-model:visible="showConfirmModal"
        title="Are you sure you want to delete?"
        okText="Delete"
        cancelText="Cancel"
        @ok="confirmDelete"
        @cancel="cancelDelete"
    >
      <p>Thao tác này không thể hoàn tác.</p>
    </a-modal>
  </div>
</template>

<style scoped>
:deep(.ant-table-wrapper) {
  overflow-x: auto;
}

:deep(.ant-table) {
  min-width: 100%;
}

:deep(.ant-table-cell) {
  white-space: nowrap;
  padding: 8px;
}
</style>