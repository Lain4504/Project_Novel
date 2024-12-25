<script lang="ts" setup>
import {onMounted, ref, watch} from "vue";
import DynamicDataTable from "@/components/common/DynamicDataTable.vue";
import {deletePost, getPosts} from "@/api/post";
import router from "@/router";

const postColumns = [
  {field: "id", headerName: "ID", width: 70},
  {field: "title", headerName: "Tiêu đề", width: 200},
  {field: "created", headerName: "Thời gian tạo", width: 200},
  {
    field: "actions",
    headerName: "Hành động",
    width: 200,
    isAction: true,
  },
];

const currentPage = ref(1);
const pageSize = ref(10);
const totalPages = ref(1);
const postRows = ref<any[]>([]);

const showConfirmModal = ref(false);
const rowToDelete = ref<any>(null);

const fetchPosts = async (page: number, size: number) => {
  try {
    const response = await getPosts({page, size});
    postRows.value = response.data;
    totalPages.value = response.totalPages;
  } catch (error) {
    console.error("Error fetching posts:", error);
  }
};
const handleEdit = (row: any) => {
  router.push({name: 'postupdateform', params: {id: row.id}});
};
const handleDelete = (row: any) => {
  rowToDelete.value = row;
  showConfirmModal.value = true;
};

const confirmDelete = async () => {
  if (rowToDelete.value) {
    try {
      await deletePost(rowToDelete.value.id);
      fetchPosts(currentPage.value, pageSize.value);
    } catch (error) {
      console.error('Error deleting post:', error);
    }
  }
  showConfirmModal.value = false;
};

const cancelDelete = () => {
  showConfirmModal.value = false;
};

watch(currentPage, () => {
  fetchPosts(currentPage.value, pageSize.value);
});

onMounted(() => {
  fetchPosts(currentPage.value, pageSize.value);
});

const handlePageChange = (page: number) => {
  currentPage.value = page;
};
const viewPost = (row: any) => {
  router.push({name: 'postdetail', params: {id: row.id}});
};
</script>

<template>
  <h3 class="text-2xl font-bold text-left py-2">Post Management</h3>

  <!-- Modal Confirm Delete -->
  <div v-if="showConfirmModal" class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-10">
    <div class="bg-white p-6 rounded-lg shadow-lg w-1/3">
      <h4 class="text-xl font-semibold mb-4">Bạn có chắc chắn muốn xóa không?</h4>
      <div class="flex justify-end space-x-4">
        <!-- Cancel Button -->
        <button class="px-[1.2rem] py-[0.4rem] border border-gray-400 text-gray-700 rounded-md hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-500"
                @click="cancelDelete">
          Cancel
        </button>
        <!-- Confirm Delete Button -->
        <button class="px-[1.2rem] py-[0.4rem] border border-red-500 text-red-500 rounded-md hover:bg-red-600 hover:text-white focus:outline-none focus:ring-2 focus:ring-red-500"
                @click="confirmDelete">
          Delete
        </button>
      </div>
    </div>
  </div>

  <!-- Pass the fetched rows and columns to the DynamicDataTable component -->
  <DynamicDataTable
      :columns="postColumns"
      :currentPage="currentPage"
      :emits="['page-change', 'view', 'delete', 'edit']"
      :rows="postRows"
      :show-go-to-page="true"
      :totalPages="totalPages"
      @delete="handleDelete"
      @edit="handleEdit"
      @view="viewPost"
      @page-change="handlePageChange"
  />
</template>