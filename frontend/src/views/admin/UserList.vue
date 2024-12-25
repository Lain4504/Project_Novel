<script lang="ts" setup>
import {onMounted, ref, watch} from "vue";
import router from "@/router";
import ConfirmDeleteModal from "@/components/common/ConfirmDeleteModal.vue";
import DynamicDataTable from "@/components/common/DynamicDataTable.vue";

const novelColumns = [
  {field: "id", headerName: "ID", width: 70},
  {field: "username", headerName: "UserName", width: 200},
  {field: "email", headerName: "Email", width: 200},
  {field: "role", headerName: "Role", width: 200},
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
}
const handleDelete = (row: any) => {
  rowToDelete.value = row;
  showConfirmModal.value = true;
}
const confirmDelete = async () => {
  if (rowToDelete.value) {
    try {
      await InactiveUser(rowToDelete.value.id);
      fetchNovels(currentPage.value, pageSize.value);
    } catch (error) {
      console.error('Error deleting novel:', error);
    }
  }
  showConfirmModal.value = false;
}
const cancelDelete = () => {
  showConfirmModal.value = false;
}
watch(currentPage, () => {
  fetchNovels(currentPage.value, pageSize.value);
})
onMounted(() => {
  fetchNovels(currentPage.value, pageSize.value);
})
const handlePageChange = (page: number) => {
  currentPage.value = page;
}
const handleEdit = (row: any) => {
  router.push({name: 'novelupdateform', params: {id: row.id}});
}
const handleView = (row: any) => {
  router.push({name: 'noveldetail', params: {id: row.id}});
}
</script>
<template>
  <h3 class="text-2xl font-bold text-left py-2"> Novel Management</h3>
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
      :show-go-to-page="true"
      :totalPages="totalPages"
      @delete="handleDelete"
      @edit="handleEdit"
      @view="handleView"
      @page-change="handlePageChange"
  />
</template>

