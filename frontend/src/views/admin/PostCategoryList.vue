<script lang="ts" setup>
import {onMounted, ref, watch} from 'vue';
import DynamicDataTable from "@/components/common/DynamicDataTable.vue";
import {deletePostCategory, getPostCategories} from "@/api/postCategory";
import router from "@/router";
import ConfirmDeleteModal from "@/components/common/ConfirmDeleteModal.vue";

// Define columns for the data table
const postCategoryColumns = [
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

// Define reactive variables for pagination
const currentPage = ref(1);
const pageSize = ref(10);
const totalPages = ref(1);
const postRows = ref<any[]>([]);

const createPath = '/create-post-category';

// Reactive variable for showing the delete confirmation modal
const showConfirmModal = ref(false);
const rowToDelete = ref<any>(null);

// Function to fetch post categories
const fetchPostCategories = async (page: number, size: number) => {
  try {
    const response = await getPostCategories(page, size);
    postRows.value = response.data;
    totalPages.value = response.totalPages;
  } catch (error) {
    console.error('Error fetching post categories:', error);
  }
};

const handleDelete = (row: any) => {
  rowToDelete.value = row;
  showConfirmModal.value = true;
};

const confirmDelete = async () => {
  if (rowToDelete.value) {
    try {
      await deletePostCategory(rowToDelete.value.id);
      if (postRows.value.length === 1 && currentPage.value > 1) {
        currentPage.value -= 1;
      }
      fetchPostCategories(currentPage.value, pageSize.value);
    } catch (error) {
      console.error('Error deleting post category:', error);
    }
  }
  showConfirmModal.value = false;
};

const cancelDelete = () => {
  showConfirmModal.value = false;
};

watch(currentPage, (newPage) => {
  fetchPostCategories(newPage, pageSize.value);
});

onMounted(() => {
  fetchPostCategories(currentPage.value, pageSize.value);
});

const handlePageChange = (page: number) => {
  currentPage.value = page;
};

const handleEdit = (row: any) => {
  router.push({name: 'updatePostCategory', params: {id: row.id}});
};
</script>

<template>
  <h3 class="text-2xl font-bold text-left py-2">Post Category Management</h3>

  <ConfirmDeleteModal
      :show="showConfirmModal"
      confirmText="Xóa"
      message="Việc xóa sẽ không thể khôi phục lại được!"
      title="Bạn có chắc chắn muốn xóa không?"
      @cancel="cancelDelete"
      @confirm="confirmDelete"
  />

  <DynamicDataTable
      :columns="postCategoryColumns"
      :createPath="createPath"
      :currentPage="currentPage"
      :emits="['page-change', 'delete', 'edit']"
      :rows="postRows"
      :totalPages="totalPages"
      @delete="handleDelete"
      @edit="handleEdit"
      @page-change="handlePageChange"
  />
</template>