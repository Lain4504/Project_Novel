<template>
  <div class="fixed inset-0 bg-gray-500 bg-opacity-75 flex justify-center items-center z-50">
    <div class="bg-white rounded-lg p-6 w-96">
      <div class="text-center">
        <h3 class="text-xl font-semibold">{{ title }}</h3>
        <p class="mt-2 text-sm text-gray-600">{{ content }}</p>
        <div class="mt-4">
          <button @click="handleConfirm" class="px-4 py-2 bg-[#C15E3C] text-white rounded-md hover:bg-[#d76843]">{{ confirmText }}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps({
  title: String,
  content: String,
  confirmText: String,
  cancelText: String,
});
const emit = defineEmits(['close']);

const handleConfirm = () => {
  emit('close');
};
</script>

<style scoped>
/* Modal styling */
</style>
