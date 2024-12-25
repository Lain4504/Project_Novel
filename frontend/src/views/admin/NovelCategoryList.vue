<script lang="ts" setup>
import {onMounted, ref, watch} from "vue";
import DynamicDataTable from "@/components/common/DynamicDataTable.vue";
import {deleteNovelCategory, getNovelCategories} from "@/api/novelCategory";
import router from "@/router";
import ConfirmDeleteModal from "@/components/common/ConfirmDeleteModal.vue";

const novelCategoryColumns = [
  {field: "id", headerName: "ID", width: 70},
  {field: "name", headerName: "Tiêu đề", width: 200},
  {field: "created", headerName: "Thời gian tạo", width: 200},
  {
    field: "actions",
    headerName: "Hành động",
    width: 200,
    isAction: true,
  },
];

const totalPages = ref(1);
const pageSize = ref(10);
const currentPage = ref(1);
const novelCategoryRows = ref<any[]>([]);
const showConfirmModal = ref(false);
const rowToDelete = ref<any>(null);
const createPath = '/create-novel-category';

const fetchNovelCategories = async (page: number, size: number) => {
  try {
    const response = await getNovelCategories(page, size);
    novelCategoryRows.value = response.data;
    totalPages.value = response.totalPages;
  } catch (error) {
    console.error('Error fetching novel categories:', error);
  }
};

const handleDelete = (row: any) => {
  rowToDelete.value = row;
  showConfirmModal.value = true;
};

const confirmDelete = async () => {
  if (rowToDelete.value) {
    try {
      await deleteNovelCategory(rowToDelete.value.id);
      if (novelCategoryRows.value.length === 1 && currentPage.value > 1) {
        currentPage.value -= 1;
      }
      fetchNovelCategories(currentPage.value, pageSize.value);
    } catch (error) {
      console.error('Error deleting novel category:', error);
    }
  }
  showConfirmModal.value = false;
};

const cancelDelete = () => {
  showConfirmModal.value = false;
};

watch(currentPage, (newPage) => {
  fetchNovelCategories(newPage, pageSize.value);
});

onMounted(() => {
  fetchNovelCategories(currentPage.value, pageSize.value);
});

const handlePageSizeChange = (current: number, size: number) => {
  pageSize.value = size;
  currentPage.value = current;
  fetchNovelCategories(current, size);
};

const handleEdit = (row: any) => {
  router.push({name: 'updateNovelCategory', params: {id: row.id}});
};
</script>

<template>
  <h3 class="text-2xl font-bold text-left py-2">Novel Category Management</h3>

  <ConfirmDeleteModal
      :show="showConfirmModal"
      confirmText="Xóa"
      message="Việc xóa sẽ không thể khôi phục lại được!"
      title="Bạn có chắc chắn muốn xóa không?"
      @cancel="cancelDelete"
      @confirm="confirmDelete"
  />
  <DynamicDataTable
      :columns="novelCategoryColumns"
      :createPath="createPath"
      :currentPage="currentPage"
      :emits="['page-change', 'delete', 'edit']"
      :rows="novelCategoryRows"
      :totalPages="totalPages"
      @delete="handleDelete"
      @edit="handleEdit"
      @page-change="handlePageSizeChange"
  />
</template>