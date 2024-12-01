<template>
  <div
    v-if="isVisible"
    :class="`fixed top-4 right-4 p-4 mb-4 text-sm rounded-lg shadow-lg z-50 ${alertClass} w-80 h-auto max-w-lg`"
  >
    <div class="flex items-center justify-between">
      <!-- Icon and type -->
      <div class="flex items-center">
        <FontAwesomeIcon :icon="iconClass" class="me-3" />
        <span class="font-semibold me-2 capitalize">{{ alertType }}</span>
      </div>

      <!-- Close button -->
      <button
        class="ms-auto text-gray-400 hover:text-gray-600 focus:outline-none"
        @click="closeAlert"
        aria-label="Close"
      >
        <FontAwesomeIcon icon="times" />
      </button>
    </div>

    <!-- Message -->
    <div class="mt-2">{{ message }}</div>
  </div>
</template>

<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref } from 'vue';

// Icon mapping for different alert types
const icons = {
  info: 'info-circle',
  danger: 'exclamation-circle',
  success: 'check-circle',
  warning: 'exclamation-triangle',
  default: 'info-circle',
};

// State for managing alert visibility and content
const isVisible = ref(false);
const message = ref('');
const alertClass = ref('');
const alertType = ref('info'); // Type: info, success, danger, warning
const iconClass = ref(icons.default);

// Function to show the alert
const showAlert = (type: string, messageContent: string) => {
  isVisible.value = true;
  message.value = messageContent;
  alertType.value = type;

  // Set the appropriate icon and style
  iconClass.value = icons[type] || icons.default;
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

  // Auto-hide after 2 seconds
  setTimeout(() => {
    isVisible.value = false;
  }, 5000);
};

// Function to close the alert
const closeAlert = () => {
  isVisible.value = false;
};

// Expose `showAlert` for use
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
