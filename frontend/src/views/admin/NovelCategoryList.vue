<script setup lang="ts">
import { ref, onMounted, watch } from "vue";
import DynamicDataTable from "@/components/common/DynamicDataTable.vue";
import {getNovelCategories, deleteNovelCategory, getNovelCategory} from "@/api/novelcategory";
import router from "@/router";
import ConfirmDeleteModal from "@/components/common/ConfirmDeleteModal.vue";
const novelCategoryColumns = [
  { field: "id", headerName: "ID", width: 70 },
  { field: "name", headerName: "Tiêu đề", width: 200 },
  { field: "created", headerName: "Thời gian tạo", width: 200 },
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
const createPath = '/novel-category-admin';
const fetchNovelCategories = async (page: number, size: number) =>{
  try{
    const response = await getNovelCategories(page, size);
    novelCategoryRows.value = response.data;
    totalPages.value = response.totalPages;
  }
  catch (error){
    console.error('Error fetching novel categories:', error);
  }
}
const handleDelete = (row: any) =>{
  rowToDelete.value = row;
  showConfirmModal.value = true;
}
const confirmDelete = async () =>{
  if (rowToDelete.value){
    try{
      await deleteNovelCategory(rowToDelete.value.id);
      fetchNovelCategories(currentPage.value, pageSize.value);
    }
    catch (error){
      console.error('Error deleting novel category:', error);
    }
  }
  showConfirmModal.value = false;
}
const cancelDelete = () =>{
  showConfirmModal.value = false;
}
watch(currentPage, () =>{
  fetchNovelCategories(currentPage.value, pageSize.value);
})
onMounted(() =>{
  fetchNovelCategories(currentPage.value, pageSize.value);
})
const handlePageChange = (page: number) =>{
  currentPage.value = page;
}
const handleEdit = (row: any) =>{
  router.push({name: 'novelcategoryupdateform', params: {id: row.id}});
}
</script>

<template>
  <h3 class="text-2xl font-bold text-left py-2">Novel Category Management</h3>

  <!-- Modal Confirm Delete -->
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
      :columns="novelCategoryColumns"
      :rows="novelCategoryRows"
      :currentPage="currentPage"
      :totalPages="totalPages"
      :createPath="createPath"
      :emits="['page-change', 'delete', 'edit']"
      @page-change="handlePageChange"
      @edit="handleEdit"
      @delete="handleDelete"
  />
</template>