<script setup lang="ts">
import { inject, ref, watch } from 'vue';
import Tiptap from "@/components/common/Tiptap.vue";
import { Input, Select, Button, notification } from 'ant-design-vue';

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

const showNotification = (type: 'success' | 'error', message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3,
  });
};

const handleSave = async () => {
  try {
    await props.onSave(formData.value.id, formData.value);
    showNotification('success', 'Item updated successfully.');
  } catch (error: any) {
    console.error('Failed to update item:', error);
    if (error.response) {
      showNotification('error', error.response.data.message || 'Item update failed. Please try again.');
    } else if (error.request) {
      showNotification('error', 'No response from server. Please try again.');
    } else {
      showNotification('error', 'An unexpected error occurred. Please try again.');
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
              {{ field.label }}<span v-if="field.required" class="text-red-500">*</span>
            </label>
            <div class="mt-1">
              <template v-if="field.type === 'tiptap'">
                <Tiptap
                  :content="formData[field.id]"
                  @update:content="formData[field.id] = $event"
                />
              </template>
              <template v-else-if="field.type === 'select'">
                <Select
                  :id="field.id"
                  v-model:value="formData[field.id]"
                  class="w-full"
                >
                  <Select.Option
                    v-for="option in field.options"
                    :key="option.value"
                    :value="option.value"
                  >
                    {{ option.label }}
                  </Select.Option>
                </Select>
              </template>
              <template v-else>
                <Input
                  :type="field.type"
                  :id="field.id"
                  v-model:value="formData[field.id]"
                  class="w-full"
                />
              </template>
            </div>
          </div>
        </div>

        <div class="flex justify-end space-x-4">
          <Button
            type="default"
            @click="props.onCancel"
          >
            Cancel
          </Button>
          <Button
            type="primary"
            @click="handleSave"
          >
            Save
          </Button>
        </div>
      </form>
    </div>
  </div>
</template>