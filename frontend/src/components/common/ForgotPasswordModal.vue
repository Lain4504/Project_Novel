<script lang="ts" setup>
import {ref} from 'vue';
import {RuleObject} from 'ant-design-vue/es/form';

const props = defineProps({
  isModalVisible: Boolean,
});

const emit = defineEmits(['update:isModalVisible']);

const form = ref();
const formState = ref({
  email: '',
});
const message = ref('');

const rules: { [key: string]: RuleObject | RuleObject[] } = {
  email: [
    {required: true, message: 'Please input your email!'},
    {type: 'email', message: 'Please enter a valid email address!'}
  ],
};

const closeModal = () => {
  emit('update:isModalVisible', false);
  message.value = '';
};

const handleSubmit = () => {
  form.value.validate().then(() => {
    // Handle form submission
    message.value = 'Một email đã được gửi tới địa chỉ của bạn. Vui lòng kiểm tra hộp thư đến và thư mục spam.';
  }).catch((error: any) => {
    console.log('Validation failed:', error);
  });
};
</script>
<template>
  <a-modal
      :visible="isModalVisible"
      okText="Gửi yêu cầu khôi phục mật khẩu"
      title="Quên mật khẩu"
      @cancel="closeModal"
      @ok="handleSubmit"
  >
    <a-form ref="form" :model="formState" :rules="rules">
      <a-form-item name="email">
        <a-input
            v-model:value="formState.email"
            placeholder="name@company.com"
            type="email"
        />
      </a-form-item>
    </a-form>
    <div v-if="message" class="mt-4 text-green-600">{{ message }}</div>
  </a-modal>
</template>

