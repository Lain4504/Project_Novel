<script lang="ts" setup>
import {onMounted, ref, watch} from "vue";
import {deleteNovel, getNovels} from "@/api/novel";
import router from "@/router";
import ConfirmDeleteModal from "@/components/common/ConfirmDeleteModal.vue";
import DynamicDataTable from "@/components/common/DynamicDataTable.vue";

const novelColumns = [
  {field: "id", headerName: "ID", width: 70},
  {field: "title", headerName: "Tiêu đề", width: 200},
  {field: "authorName", headerName: "Tác giả", width: 200},
  {field: "created", headerName: "Thời gian tạo", width: 200},
  {field: "actions", headerName: "Hành động", width: 200, isAction: true}
];

const totalPages = ref(1);
const pageSize = ref(10);
const currentPage = ref(1);
const novelRows = ref<any[]>([]);
const showConfirmModal = ref(false);
const rowToDelete = ref<any>(null);

const fetchNovels = async (page: number, size: number) => {
  try {
    const response = await getNovels(page, size);
    novelRows.value = response.data;
    totalPages.value = response.totalPages;
  } catch (error) {
    console.error('Error fetching novels:', error);
  }
};

const handleDelete = (row: any) => {
  rowToDelete.value = row;
  showConfirmModal.value = true;
};

const confirmDelete = async () => {
  if (rowToDelete.value) {
    try {
      await deleteNovel(rowToDelete.value.id);
      // Check if the current page is empty after deletion
      if (novelRows.value.length === 1 && currentPage.value > 1) {
        currentPage.value -= 1;
      }
      fetchNovels(currentPage.value, pageSize.value);
    } catch (error) {
      console.error('Error deleting novel:', error);
    }
  }
  showConfirmModal.value = false;
};

const cancelDelete = () => {
  showConfirmModal.value = false;
};

watch([currentPage, pageSize], () => {
  fetchNovels(currentPage.value, pageSize.value);
});

onMounted(() => {
  fetchNovels(currentPage.value, pageSize.value);
});

const handlePageChange = (page: number, size: number = pageSize.value) => {
  currentPage.value = page;
  pageSize.value = size;
};
const handleEdit = (row: any) => {
  router.push({name: 'novelAuthorManagement', params: {id: row.id}});
};

const handleView = (row: any) => {
  router.push({name: 'noveldetail', params: {id: row.id}});
};
</script>

<template>
  <h3 class="text-2xl font-bold text-left py-2">Novel Management</h3>
  <ConfirmDeleteModal
      :show="showConfirmModal"
      confirmText="Xóa"
      message="Thao tác này sẽ gửi yêu cầu xóa tiểu thuyết tới admin để xem xét, thời gian giải quyết tối đa 3 ngày làm việc."
      title="Bạn có chắc chắn muốn xóa không?"
      @cancel="cancelDelete"
      @confirm="confirmDelete"
  />
  <DynamicDataTable
      :columns="novelColumns"
      :currentPage="currentPage"
      :emits="['page-change', 'delete', 'view', 'edit']"
      :rows="novelRows"
      :totalPages="totalPages"
      @delete="handleDelete"
      @edit="handleEdit"
      @view="handleView"
      @page-change="handlePageChange"
  />
</template>