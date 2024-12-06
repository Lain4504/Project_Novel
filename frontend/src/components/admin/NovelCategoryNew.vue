<script setup lang="ts">
import DynamicFormNew from "@/components/common/DynamicFormNew.vue";
import {createNovelCategory} from "@/api/novelcategory";
const collectionInputs = [
  {
    id: 'name',
    label: 'Name',
    type: 'text',
    placeholder: 'Enter category name',
  },
  {
    id: 'description',
    label: 'Description',
    type: 'tiptap',
    placeholder: 'Enter category description',
  },
];
const handleCreateNovelCategory = async (data: any) => {
    const response = await createNovelCategory(data);
}
const location = '/novel-category-list';

</script>

<template>
  <DynamicFormNew
      :inputs="collectionInputs"
      title="Create Novel Category"
      :handleAdd="handleCreateNovelCategory"
      :location="location"
  />
</template>
