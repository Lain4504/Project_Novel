<script setup lang="ts">
import {ref, onMounted, inject} from 'vue';
import DynamicFormEdit from "@/components/common/DynamicFormEdit.vue";
import { updatePostCategory } from "@/api/postcategory";
import { getPostCategory } from "@/api/postcategory";
import router from "@/router";

// Inject hàm showAlert từ context
const fields = {
  title: 'Post Category',
  inputs: [
    { id: 'name', label: 'Name', type: 'text' },
    { id: 'description', label: 'Description', type: 'tiptap' },
  ],
};

// Nhận prop `id` từ route hoặc component cha
const props = defineProps({
  id: {
    type: String,
    required: true,
  },
});

// Khởi tạo biến chứa dữ liệu ban đầu
const initialData = ref({});

// Hàm tải dữ liệu từ API
const loadPostCategory = async () => {
  try {
    const response = await getPostCategory(props.id);
    initialData.value = response;
  } catch (error) {
    console.error('Failed to fetch post category:', error);
  }
};
// Hàm xử lý lưu dữ liệu
const handleSave = async (id: string, data: Record<string, any>) => {
  try {
    await updatePostCategory(id, data);
    console.log('Post category updated successfully!');
    router.push({ name: 'postcategorylist' });
  } catch (error) {
    console.error('Failed to update post category:', error);
    throw error; // Ném lỗi để component con xử lý
  }
};

// Hàm xử lý khi người dùng hủy chỉnh sửa
const handleCancel = () => {
  router.back();
};

// Gọi hàm tải dữ liệu khi component được mount
onMounted(() => {
  loadPostCategory();
});
</script>

<template>
  <DynamicFormEdit
      :fields="fields"
      :initialData="initialData"
      :onSave="handleSave"
      :onCancel="handleCancel"
  />
</template>
