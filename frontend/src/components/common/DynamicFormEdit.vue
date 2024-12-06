<script setup lang="ts">
import {inject, ref, watch} from 'vue';
import Tiptap from "@/components/common/Tiptap.vue"; // Đảm bảo đường dẫn đến Tiptap đúng

interface Field {
  id: string;
  label: string;
  type: string;
  options?: { value: string; label: string }[];
}

interface Props {
  fields: { title: string; inputs: Field[] };
  initialData: Record<string, any>;
  onSave: (id: string, data: Record<string, any>) => Promise<void>;
  onCancel: () => void;
}

const props = defineProps<Props>();
const formData = ref({ ...props.initialData });

watch(
    () => props.initialData,
    (newData) => {
      formData.value = { ...newData };
    }
);
const showAlert = inject('showAlert') as ((type: string, message: string) => void);
const showNotification = (type: string, message: string) => {
  if (showAlert) {
    showAlert(type, message); // Gọi hàm showAlert toàn cục
  } else {
    console.error('showAlert is not available in this context');
  }
};
const handleSave = async () => {
  try {
    await props.onSave(formData.value.id, formData.value);
    showNotification('success', 'Item updated successfully.');
  } catch (error: any) {
    console.error('Failed to update item:', error);
    // Xử lý lỗi trực tiếp trong DynamicFormEdit
    if (error.response) {
      showNotification('danger', error.response.data.message || 'Item update failed. Please try again.');
    } else if (error.request) {
      showNotification('danger', 'No response from server. Please try again.');
    } else {
      showNotification('danger', 'An unexpected error occurred. Please try again.');
    }
  }
};
</script>

<template>
  <div class="py-10 px-4">
    <div class="bg-white shadow-lg rounded-lg p-6 max-w-4xl mx-auto">
      <h3 class="text-xl font-semibold mb-6">Edit {{ props.fields.title }}</h3>
      <form class="space-y-6">
        <div class="flex flex-col space-y-6">
          <div v-for="field in props.fields.inputs" :key="field.id">
            <label :for="field.id" class="block text-sm font-medium text-gray-700">
              {{ field.label }}
            </label>
            <div class="mt-1">
              <!-- Xử lý kiểu 'tiptap' -->
              <template v-if="field.type === 'tiptap'">
                <Tiptap
                    :content="formData[field.id]"
                    @update:content="formData[field.id] = $event"
                />
              </template>
              <!-- Xử lý kiểu 'select' -->
              <template v-else-if="field.type === 'select'">
                <select
                    :id="field.id"
                    v-model="formData[field.id]"
                    class="w-full border border-gray-300 rounded-lg py-1 px-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm"
                >
                  <option
                      v-for="option in field.options"
                      :key="option.value"
                      :value="option.value"
                  >
                    {{ option.label }}
                  </option>
                </select>
              </template>
              <!-- Xử lý các kiểu còn lại -->
              <template v-else>
                <input
                    :type="field.type"
                    :id="field.id"
                    v-model="formData[field.id]"
                    class="w-full border border-gray-300 rounded-lg py-1 px-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 placeholder:text-sm"
                />
              </template>
            </div>
          </div>
        </div>

        <div class="flex justify-end space-x-4">
          <button
              type="button"
              @click="props.onCancel"
              class="cursor-pointer text-sm bg-transparent border-[1px] border-red-500 text-red-500 hover:border-red-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300"
          >
            Cancel
          </button>
          <button
              type="button"
              @click="handleSave"
              class="cursor-pointer text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300"
          >
            Save
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
