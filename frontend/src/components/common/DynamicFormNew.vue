<script setup lang="ts">
import {inject, PropType, ref} from 'vue';
import { useRouter } from 'vue-router';
import Tiptap from '@/components/common/Tiptap.vue';

interface Input {
  id: string;
  label: string;
  type: string; // text, select, tiptap, etc.
  placeholder?: string;
  options?: { value: string; label: string }[];
}

const props = defineProps({
  inputs: {
    type: Array as PropType<Input[]>,
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
const showAlert = inject('showAlert') as ((type: string, message: string) => void);
const showNotification = (type: string, message: string) => {
  if (showAlert) {
    showAlert(type, message); // Gọi hàm showAlert toàn cục
  } else {
    console.error('showAlert is not available in this context');
  }
};
const router = useRouter();
const formData = ref<any>({});

const handleAddItem = async () => {
  try {
    await props.handleAdd(formData.value);
    showNotification('success', 'Item added successfully.');
    router.push(props.location); // Điều hướng khi thành công
  } catch (error : any) {
    console.error('Failed to add item:', error);
    // Xử lý lỗi trực tiếp trong DynamicFormNew
    if (error.response) {
      showNotification('danger', error.response.data.message || 'Novel category creation failed. Please try again.');
    } else if (error.request) {
      showNotification('danger', 'No response from server. Please try again.');
    } else {
      showNotification('danger', 'An unexpected error occurred. Please try again.');
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
      <form @submit.prevent="handleAddItem" class="space-y-6">
        <!-- Loop Through Inputs -->
        <div v-for="input in inputs" :key="input.id">
          <label :for="input.id" class="block text-sm font-medium text-gray-700">{{ input.label }}</label>

          <!-- Select Field -->
          <div v-if="input.type === 'select'" class="mt-1">
            <select
                v-model="formData[input.id]"
                :id="input.id"
                class="w-full p-2 border border-gray-300 rounded-md"
                required
            >
              <option disabled value="">{{ `Select ${input.label}` }}</option>
              <option v-for="option in input.options" :key="option.value" :value="option.value">
                {{ option.label }}
              </option>
            </select>
          </div>

          <!-- Tiptap Editor -->
          <div v-else-if="input.type === 'tiptap'" class="mt-1">
            <Tiptap :content="formData[input.id]" @update:content="formData[input.id] = $event" />
          </div>

          <!-- Default Input -->
          <div v-else class="mt-1">
            <input
                v-model="formData[input.id]"
                :type="input.type"
                :id="input.id"
                :placeholder="input.placeholder || ''"
                class="w-full border border-gray-300 rounded-lg py-1 px-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm"
                required
            />
          </div>
        </div>

        <!-- Action Buttons -->
        <div class="flex justify-between items-center">
          <button type="submit"
                  class="cursor-pointer text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
            Save
          </button>
          <button @click="handleCancel" type="button"
                  class="cursor-pointer text-sm bg-transparent border-[1px] border-red-500 text-red-500 hover:border-red-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
            Cancel
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
/* You can add additional styles if needed */
</style>
