<script setup lang="ts">
import DynamicDataTable from "@/components/common/DynamicDataTable.vue";
import router from "@/router";

const postColumns = [
  { field: "id", headerName: "ID", width: 70 },
  { field: "title", headerName: "Tiêu đề", width: 200 },
  {
    field: "category",
    headerName: "Danh mục",
    width: 200,
    renderCell: (row: any) => row.categoryId,
  },
  { field: "createdAt", headerName: "Thời gian tạo", width: 200 },
  {
    field: "author",
    headerName: "Tác giả",
    width: 150,
    renderCell: (row: any) => row.userId,
  },
  {
    field: "actions",
    headerName: "Hành động",
    width: 200,
    isAction: true, // Đánh dấu không hiển thị cột này
  },
];


const postRows = [
  { id: 1, title: "Overlord", categoryId: "Light Novel", createdAt: "2024-12-04", userId: "Maruyama Kugane" },
  { id: 2, title: "Re:Zero", categoryId: "Fantasy", createdAt: "2024-12-04", userId: "Tappei Nagatsuki" },
];

function handleEdit() {
  router.push({ name: "editnovel"});
}
// function handleEdit(row: any) {
//   router.push({ name: "edit-novel", params: { id: row.id } });
// }
function handleDelete(row: any) {
  alert(`Xoá: ${JSON.stringify(row)}`);
}
</script>

<template>
  <h3 class="text-2xl font-bold text-left py-2">Post Management</h3>
  <DynamicDataTable
      :columns="postColumns"
      :rows="postRows"
      @edit="handleEdit"
      @delete="handleDelete"
  />
</template>
