<script lang="ts" setup>
import DynamicFormNew from "@/components/common/DynamicFormNew.vue";
import {createNovelCategory} from "@/api/novelCategory";
import {useRouter} from "vue-router";

const collectionInputs = [
  {
    id: 'name',
    label: 'Name',
    type: 'text',
    placeholder: 'Enter category name',
    required: true,
  },
  {
    id: 'description',
    label: 'Description',
    type: 'tiptap',
    placeholder: 'Enter category description',
    required: false,
  },
];
const handleCreateNovelCategory = async (data: any) => {
  const response = await createNovelCategory(data);
}
const location = '/novel-category-list';

</script>

<template>
  <DynamicFormNew
      :handleAdd="handleCreateNovelCategory"
      :inputs="collectionInputs"
      :location="location"
      title="Create Novel Category"
  />
</template>
