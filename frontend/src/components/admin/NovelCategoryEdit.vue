<script lang="ts" setup>

import {getNovelCategory, updateNovelCategory} from "@/api/novelCategory";
import {onMounted, ref} from "vue";
import router from "@/router";
import DynamicFormEdit from "@/components/common/DynamicFormEdit.vue";

const fields = {
  title: 'Novel Category',
  inputs: [
    {id: 'name', label: 'Name', type: 'text', required: true},
    {id: 'description', label: 'Description', type: 'tiptap', required: false},
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
    initialData.value = await getNovelCategory(props.id);
  } catch (error) {
    console.error('Failed to fetch novel category:', error);
  }
}

onMounted(() => {
  loadNovelCategory();
});
</script>
<template>
  <DynamicFormEdit
      :fields="fields"
      :initialData="initialData"
      :onSave="updateNovelCategory"
  />
</template>