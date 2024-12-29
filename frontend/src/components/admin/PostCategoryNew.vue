<script lang="ts" setup>
import DynamicFormNew from "@/components/common/DynamicFormNew.vue";
import {createPostCategory} from "@/api/postCategory";

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
  },
];

</script>

<template>
  <DynamicFormNew
      :handleAdd="createPostCategory"
      :inputs="collectionInputs"
      title="Create Post Category"
  />
</template>
