<script lang="ts" setup>
import DynamicFormNew from "@/components/common/DynamicFormNew.vue";
import {createNovelCategory} from "@/api/novelCategory";
import {useRouter} from "vue-router";
import DynamicFormEdit from "@/components/common/DynamicFormEdit.vue";
import router from "@/router";

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
