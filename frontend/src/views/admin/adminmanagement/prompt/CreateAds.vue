<script lang="ts" setup>
import DynamicFormNew from "../../../../components/admin/DynamicFormNew.vue";
import {createAds} from "../../../../api/resource.ts";

const collectionInputs = [
  {
    id: 'title',
    label: 'Title',
    type: 'text',
    placeholder: 'Enter title',
    required: true,
  },
  {
    id: 'redirectUrl',
    label: 'Redirect Url',
    type: 'text',
    placeholder: 'Enter redirect url',
    required: true,
  },
  {
    id: 'imageUrl',
    label: 'Image Url',
    type: 'text',
    placeholder: 'Enter image url',
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
      :handleAdd="createAds"
      :inputs="collectionInputs"
      title="Tạo quảng cáo"
  />
</template>
