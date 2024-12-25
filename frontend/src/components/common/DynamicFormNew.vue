<script lang="ts" setup>
import {PropType, ref} from 'vue';
import {useRouter} from 'vue-router';
import Tiptap from '@/components/common/Tiptap.vue';
import {Button, Input, notification, Select} from 'ant-design-vue';

interface InputField {
  id: string;
  label: string;
  type: string; // text, select, tiptap, etc.
  placeholder?: string;
  options?: { value: string; label: string }[];
}

const props = defineProps({
  inputs: {
    type: Array as PropType<InputField[]>,
    required: true
  },
  title: {
    type: String,
    required: true
  },
  handleAdd: {
    type: Function as PropType<(values: any) => Promise<void>>,
    required: true
  },
  location: {
    type: String,
    required: true
  }
});

const router = useRouter();
const formData = ref<any>({});

const showNotification = (type: 'success' | 'error', message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3,
  });
};

const handleAddItem = async () => {
  try {
    await props.handleAdd(formData.value);
    showNotification('success', 'Item added successfully.');
    router.push(props.location);
  } catch (error: any) {
    console.error('Failed to add item:', error);
    if (error.response) {
      showNotification('error', error.response.data.message || 'Novel category creation failed. Please try again.');
    } else if (error.request) {
      showNotification('error', 'No response from server. Please try again.');
    } else {
      showNotification('error', 'An unexpected error occurred. Please try again.');
    }
  }
};

const handleCancel = () => {
  router.back();
};
</script>

<template>
  <div class="flex items-center justify-center">
    <div class="w-full max-w-4xl bg-white shadow-md rounded-lg p-8">
      <h1 class="text-xl font-bold text-gray-800 mb-6">{{ title }}</h1>
      <form class="space-y-6" @submit.prevent="handleAddItem">
        <div v-for="input in inputs" :key="input.id">
          <label :for="input.id" class="block text-sm font-medium text-gray-700">
            {{ input.label }}<span v-if="input.required" class="text-red-500">*</span>
          </label>
          <div v-if="input.type === 'select'" class="mt-1">
            <Select :id="input.id" v-model:value="formData[input.id]" class="w-full">
              <Select.Option v-for="option in input.options" :key="option.value" :value="option.value">
                {{ option.label }}
              </Select.Option>
            </Select>
          </div>
          <div v-else-if="input.type === 'tiptap'" class="mt-1">
            <Tiptap :content="formData[input.id]" @update:content="formData[input.id] = $event"/>
          </div>
          <div v-else class="mt-1">
            <Input :id="input.id" v-model:value="formData[input.id]" :placeholder="input.placeholder || ''"
                   :type="input.type" class="w-full"/>
          </div>
        </div>
        <div class="flex justify-between items-center">
          <Button type="default" @click="handleCancel">Cancel</Button>
          <Button htmlType="submit" type="primary">Save</Button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
/* You can add additional styles if needed */
</style>