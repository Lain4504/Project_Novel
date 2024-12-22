<template>
  <a-modal
    v-model:visible="visible"
    :title="title"
    @ok="handleConfirm"
    @cancel="handleCancel"
    okText="Confirm"
    cancelText="Cancel"
  >
    <p>{{ content }}</p>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, watch } from 'vue';

const props = defineProps({
  title: String,
  content: String,
  confirmText: String,
  cancelText: String,
  visible: Boolean,
});

const emit = defineEmits(['close']);

const visible = ref(props.visible);

watch(() => props.visible, (newVal) => {
  visible.value = newVal;
});

const handleConfirm = () => {
  emit('close');
};

const handleCancel = () => {
  emit('close');
};
</script>

<style scoped>
/* No additional styling needed as Ant Design handles it */
</style>