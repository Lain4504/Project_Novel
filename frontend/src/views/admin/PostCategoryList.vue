<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import DynamicDataTable from "@/components/common/DynamicDataTable.vue";
import { getPostCategories, deletePostCategory } from "@/api/postcategory";
import router from "@/router";
import ConfirmDeleteModal from "@/components/common/ConfirmDeleteModal.vue"; // Import modal component

// Define columns for the data table
const postCategoryColumns = [
  { field: "id", headerName: "ID", width: 70 },
  { field: "name", headerName: "Tiêu đề", width: 200 },
  { field: "created", headerName: "Thời gian tạo", width: 200 },
  {
    field: "actions",
    headerName: "Hành động",
    width: 200,
    isAction: true, // Marking this column as an action column
  },
];

// Define reactive variables for pagination
const currentPage = ref(1);
const pageSize = ref(10);
const totalPages = ref(1);
const postRows = ref<any[]>([]);

const createPath = '/post-category-admin'; // Create path for new post category

// Reactive variable for showing the delete confirmation modal
const showConfirmModal = ref(false);
const rowToDelete = ref<any>(null);

// Function to fetch post categories
const fetchPostCategories = async (page: number, size: number) => {
  try {
    const response = await getPostCategories(page, size);
    postRows.value = response.data; // Set the fetched data to postRows
    totalPages.value = response.totalPages; // Set the total pages for pagination
  } catch (error) {
    console.error('Error fetching post categories:', error);
  }
};

const handleDelete = (row: any) => {
  rowToDelete.value = row;
  showConfirmModal.value = true; // Show the confirmation modal
};

// Function to confirm deletion
const confirmDelete = async () => {
  if (rowToDelete.value) {
    try {
      await deletePostCategory(rowToDelete.value.id); // Call the API to delete
      fetchPostCategories(currentPage.value, pageSize.value); // Fetch updated categories
    } catch (error) {
      console.error('Error deleting post category:', error);
    }
  }
  showConfirmModal.value = false; // Close the modal after confirming delete
};

// Function to cancel deletion
const cancelDelete = () => {
  showConfirmModal.value = false; // Close the modal without deleting
};

// Watch for changes in the current page and fetch new data
watch(currentPage, (newPage) => {
  fetchPostCategories(newPage, pageSize.value);
});

onMounted(() => {
  fetchPostCategories(currentPage.value, pageSize.value);
});

// Function to handle page change
const handlePageChange = (page: number) => {
  currentPage.value = page;
};
const handleEdit = (row: any) => {
  router.push({ name: 'postcategoryedit', params: { id: row.id } });
};
</script>

<template>
  <h3 class="text-2xl font-bold text-left py-2">Post Category Management</h3>

  <!-- Pass the show prop to display the modal -->
  <ConfirmDeleteModal
      :show="showConfirmModal"
      title="Bạn có chắc chắn muốn xóa không?"
      message="Việc xóa sẽ không thể khôi phục lại được!"
      confirmText="Xóa"
      @confirm="confirmDelete"
      @cancel="cancelDelete"
  />

  <!-- Pass the fetched rows and columns to the DynamicDataTable component -->
  <DynamicDataTable
      :columns="postCategoryColumns"
      :rows="postRows"
      :currentPage="currentPage"
      :totalPages="totalPages"
      :createPath="createPath"
      :emits="['page-change', 'delete', 'edit']"
      @page-change="handlePageChange"
      @edit="handleEdit"
      @delete="handleDelete"
      :show-go-to-page="false"
  />
</template>
