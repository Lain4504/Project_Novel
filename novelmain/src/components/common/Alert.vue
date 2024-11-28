<template>
  <div v-if="isVisible" :class="alertClass" class="fixed top-4 right-4 p-4 mb-4 text-sm rounded-lg shadow-lg z-50">
    <div class="flex items-center">
      <FontAwesomeIcon :icon="iconClass" class="me-3"/>
      <span>{{ message }}</span>
    </div>
  </div>
</template>
<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref } from 'vue';

// Import specific icons for Vue FontAwesome

// Register icons globally if needed, or use locally
const icons = {
  info: "info-circle",
  danger: "exclamation-circle",
  success: "check-circle",
  warning: "exclamation-triangle",
  default: "info-circle", 
};

// State for managing alert visibility, message, and style
const isVisible = ref(false);
const message = ref('');
const alertClass = ref('');
const iconClass = ref(icons.default);

// Function to show the alert
const showAlert = (type: string, messageContent: string) => {
  isVisible.value = true;
  message.value = messageContent;

  // Set the appropriate icon based on the alert type
  iconClass.value = icons[type] || icons.default;

  // Set alert styling based on the type
  switch (type) {
    case 'info':
      alertClass.value = 'text-blue-800 border-blue-300 bg-blue-50';
      break;
    case 'danger':
      alertClass.value = 'text-red-800 border-red-300 bg-red-50';
      break;
    case 'success':
      alertClass.value = 'text-green-800 border-green-300 bg-green-50';
      break;
    case 'warning':
      alertClass.value = 'text-yellow-800 border-yellow-300 bg-yellow-50';
      break;
    default:
      alertClass.value = 'text-gray-800 border-gray-300 bg-gray-50';
      break;
  }

  // Hide alert after 2 seconds
  setTimeout(() => {
    isVisible.value = false;
  }, 2000); // Adjust duration as needed
};

// Expose `showAlert` for use in other components or app-wide
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
