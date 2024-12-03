<template>
  <div class="p-5 border border-gray-300 w-full bg-[#F8F8F7]">
    <h3 class="text-xl font-semibold mb-4">Sắp xếp</h3>
    <div class="p-2 mb-4">
      <!-- draggable component to enable drag-and-drop -->
      <draggable v-model="items" item-key="id" class="list" @end="logOrder">
        <template #item="{ element }">
          <div class="bg-green-100 p-2 mb-2 rounded border text-sm">
            {{ element.name }}
          </div>
        </template>
      </draggable>
    </div>
    <div class="flex justify-between">
      <button @click="saveOrder" class="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600">Lưu thứ
        tự</button>
      <button @click="goBack" class="px-4 py-2 bg-yellow-500 text-white rounded hover:bg-yellow-600">Quay lại</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import draggable from 'vue3-draggable-next';

// Define the data for items
const items = ref([
  { id: 1, name: 'Manga' },
  { id: 2, name: 'Tập 01: Tài liệu về chuyến thám hiểm kì dị của họ.' },
  { id: 3, name: 'Tập 02' },
  { id: 4, name: 'Tập 03' },
  { id: 5, name: 'Tập 04' },
  { id: 6, name: 'Tập 05' },
]);

// Function to handle saving the order
const saveOrder = () => {
  console.log('Saved order:', items.value);
  // Here you can implement the logic to save the order to the backend
};

// Function to handle going back
const goBack = () => {
  console.log('Going back');
  // Implement any back navigation logic here
};
// Log the current order after the drag ends
const logOrder = () => {
  console.log('Current order after drag:', items.value);
};
</script>

<style scoped>
/* No custom CSS needed since Tailwind CSS is handling the styles */
</style>