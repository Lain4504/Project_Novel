<script lang="ts" setup>

import {getNovelCategory, updateNovelCategory} from "@/api/novelCategory";
import {onMounted, ref} from "vue";
import router from "@/router";
import DynamicFormEdit from "@/components/common/DynamicFormEdit.vue";

const fields = {
  title: 'Novel Category',
  inputs: [
    {id: 'name', label: 'Name', type: 'text'},
    {id: 'description', label: 'Description', type: 'tiptap'},
  ],
};
const props = defineProps({
  id: {
    type: String,
    required: true,
  },
});
const initialData = ref({});
const loadNovelCategory = async () => {
  try {
    const response = await getNovelCategory(props.id);
    initialData.value = response;
  } catch (error) {
    console.error('Failed to fetch novel category:', error);
  }
}
const handleSave = async (id: string, data: Record<string, any>) => {
  try {
    await updateNovelCategory(id, data);
    router.push({name: 'novelCategoryList'});
  } catch (error) {
    console.error('Failed to update novel category:', error);
    throw error;
  }
}
const handleCancel = () => {
  router.back();
}
onMounted(() => {
  loadNovelCategory();
});
</script>
<template>
  <DynamicFormEdit
      :fields="fields"
      :initialData="initialData"
      :onCancel="handleCancel"
      :onSave="handleSave"
      @error="() => showNotification('danger', 'Failed to update post category!')"
      @success="() => showNotification('success', 'Post category updated successfully!')"
  />
</template>