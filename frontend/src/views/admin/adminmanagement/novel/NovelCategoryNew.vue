<script lang="ts" setup>
import DynamicFormNew from "../../../../components/admin/DynamicFormNew.vue";
import {createNovelCategory} from "../../../../api/novelCategory.ts";

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

</script>

<template>
  <DynamicFormNew
      :handleAdd="createNovelCategory"
      :inputs="collectionInputs"
      title="Create Novel Category"
  />
</template>
