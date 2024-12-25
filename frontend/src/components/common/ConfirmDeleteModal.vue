<template>
  <div
      v-if="show"
      class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50 px-4 py-6 md:px-0"
  >
    <div class="bg-white p-6 rounded-lg shadow-lg w-full max-w-md mx-auto">
      <h4 class="text-lg md:text-xl font-semibold mb-4">{{ title }}</h4>
      <p class="text-sm md:text-base mb-4 text-gray-700">{{ message }}</p>
      <div class="flex flex-row justify-end space-x-4">
        <!-- Cancel Button -->
        <a-button class="w-full sm:w-auto" type="default" @click="cancel">
          Cancel
        </a-button>
        <!-- Confirm Button -->
        <a-button class="w-full sm:w-auto" danger type="primary" @click="confirm">
          {{ confirmText }}
        </a-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {defineEmits, defineProps} from 'vue';

const props = defineProps({
  show: {
    type: Boolean,
    required: true,
  },
  title: {
    type: String,
    default: 'Are you sure?',
  },
  message: {
    type: String,
    default: 'Do you really want to delete this item?',
  },
  confirmText: {
    type: String,
    default: 'Delete',
  },
});

const emit = defineEmits(['confirm', 'cancel']);

const confirm = () => {
  emit('confirm');
};

const cancel = () => {
  emit('cancel');
};
</script>

<style scoped>
/* Add any additional styling if needed */
</style>