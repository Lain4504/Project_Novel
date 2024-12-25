<template>
  <a-modal
      v-model:visible="visible"
      :title="title"
      cancelText="Cancel"
      okText="Confirm"
      @cancel="handleCancel"
      @ok="handleConfirm"
  >
    <p>{{ content }}</p>
  </a-modal>
</template>

<script lang="ts" setup>
import {defineEmits, defineProps, ref, watch} from 'vue';

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