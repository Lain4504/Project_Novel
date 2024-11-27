<template>
    <div v-if="isVisible" :class="alertClass" class="fixed top-4 right-4 p-4 mb-4 text-sm rounded-lg shadow-lg z-50">
      <div class="flex items-center">
        <svg class="flex-shrink-0 inline w-4 h-4 me-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
          <path :d="iconPath"/>
        </svg>
        <span>{{ message }}</span>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
  
  const isVisible = ref(false);
  const message = ref('');
  const alertClass = ref('');
  const iconPath = ref('');
  
  // Function to show the alert
  const showAlert = (type: string, messageContent: string) => {
    isVisible.value = true;
    message.value = messageContent;
  
    switch (type) {
      case 'info':
        alertClass.value = 'text-blue-800 border-blue-300 bg-blue-50';
        iconPath.value = 'M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z';
        break;
      case 'danger':
        alertClass.value = 'text-red-800 border-red-300 bg-red-50';
        iconPath.value = 'M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z';
        break;
      case 'success':
        alertClass.value = 'text-green-800 border-green-300 bg-green-50';
        iconPath.value = 'M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z';
        break;
      case 'warning':
        alertClass.value = 'text-yellow-800 border-yellow-300 bg-yellow-50';
        iconPath.value = 'M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z';
        break;
      default:
        alertClass.value = 'text-gray-800 border-gray-300 bg-gray-50';
        iconPath.value = 'M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z';
        break;
    }
  
    setTimeout(() => {
      isVisible.value = false;
    }, 2000); // Hide alert after 5 seconds
  };
  
  // Provide `showAlert` to the rest of the app
  defineExpose({
    showAlert,
  });
  </script>
  
  <style scoped>
  .fixed {
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 9999;
  }
  </style>
  