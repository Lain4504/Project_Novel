<script setup lang="ts">
import DynamicFormNew from "@/components/common/DynamicFormNew.vue";
import { createPostCategory} from "@/api/postcategory";
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

// Function to handle creating a post category
const handleCreatePostCategory = async (data: any) => {
  try {
    // Call the API function to create the post category
    const response = await createPostCategory(data);
    console.log('Category created:', response);
  } catch (error) {
    console.error('Failed to create category:', error);
  }
};
const location = '/post-category-list';
</script>

<template>
  <DynamicFormNew
      :inputs="collectionInputs"
       title="Create Post Category"
      :handleAdd="handleCreatePostCategory"
      :location="location"
  />
</template>
