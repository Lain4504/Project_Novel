<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import DynamicFormEdit from "@/components/common/DynamicFormEdit.vue";
import {getAdsById, updateAds} from "@/api/resource";

// Inject hàm showAlert từ context
const fields = {
  title: 'quảng cáo',
  inputs: [
    {id: 'title', label: 'Title', type: 'text', required: true},
    {id: 'redirectUrl', label: 'Redirect Url', type: 'text', required: true},
    {id: 'imageUrl', label: 'Image Url', type: 'text', required: true},
    {id: 'description', label: 'Description', type: 'tiptap'},
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
    initialData.value = await getAdsById(props.id);
  } catch (error) {
    console.error('Failed to fetch post category:', error);
  }
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
      :onSave="updateAds"
  />
</template>
