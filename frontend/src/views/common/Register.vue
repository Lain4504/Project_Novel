<script lang="ts" setup>
import {message} from 'ant-design-vue';
import {LockOutlined, MailOutlined} from '@ant-design/icons-vue';
import {ref} from 'vue';
import {useStore} from 'vuex';
import {useRouter} from 'vue-router';
import {register} from '@/api/user';
import ConfirmModal from '@/components/common/ConfirmModal.vue';
import type {RuleObject} from 'ant-design-vue/es/form/interface';

const store = useStore();
const router = useRouter();
const form = ref();

const formState = ref({
  email: '',
  password: '',
  confirmPassword: ''
});

const showModal = ref(false);

const rules: { [key: string]: RuleObject | RuleObject[] } = {
  email: [
    {required: true, message: 'Please input your email!'},
    {type: 'email', message: 'Please enter a valid email address!'}
  ],
  password: [
    {required: true, message: 'Please input your password!'},
    {min: 8, message: 'Password must be at least 8 characters!'},
    {
      pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d@$!%*?&]{8,}$/,
      message: 'Password must contain at least one uppercase letter, one lowercase letter, and one number!'
    }
  ],
  confirmPassword: [
    {required: true, message: 'Please confirm your password!'},
    {
      validator(_: RuleObject, value: string, callback: (error?: string) => void) {
        if (!value || formState.value.password === value) {
          callback();
        } else {
          callback('The two passwords do not match!');
        }
      }
    }
  ]
};

const handleFinish = async (values: any) => {
  try {
    const response = await register({
      email: values.email,
      password: values.password
    });
    showModal.value = true;
  } catch (error: any) {
    if (error.response) {
      message.error(error.response.data.message || 'Registration failed. Please try again.');
    } else if (error.request) {
      message.error('No response from server. Please try again.');
    } else {
      message.error('An unexpected error occurred. Please try again.');
    }
  }
};

const handleConfirm = () => {
  showModal.value = false;
  router.push('/login');
};
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-[#f0f2f5] px-4">
    <div class="w-full max-w-md bg-white border border-gray-300 shadow-lg p-6 rounded-lg">
      <h2 class="text-center text-2xl font-bold mb-8">Register</h2>

      <a-form
          ref="form"
          :model="formState"
          :rules="rules"
          scrollToFirstError
          @finish="handleFinish"
      >
        <a-form-item name="email">
          <a-input
              v-model:value="formState.email"
              placeholder="Email"
              size="large"
          >
            <template #prefix>
              <MailOutlined class="text-gray-400"/>
            </template>
          </a-input>
        </a-form-item>

        <a-form-item name="password">
          <a-input-password
              v-model:value="formState.password"
              placeholder="Password"
              size="large"
          >
            <template #prefix>
              <LockOutlined class="text-gray-400"/>
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item name="confirmPassword">
          <a-input-password
              v-model:value="formState.confirmPassword"
              placeholder="Confirm Password"
              size="large"
          >
            <template #prefix>
              <LockOutlined class="text-gray-400"/>
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-button class="w-full" html-type="submit" size="large" type="primary">
            Register
          </a-button>
        </a-form-item>

        <div class="text-center mt-4">
          <span class="text-gray-600">Already have an account? </span>
          <router-link class="text-primary hover:text-[#18A058]" to="/login">
            Login
          </router-link>
        </div>
      </a-form>
    </div>
    <ConfirmModal
        :visible="showModal"
        content="Your registration was successful. Please confirm to proceed to the login page."
        title="Registration Successful"
        @close="handleConfirm"
    />
  </div>
</template>

<style scoped>
.ant-input-affix-wrapper {
  background-color: #fff !important;
}

.ant-form-item-explain-error {
  font-size: 12px;
  margin-top: 4px;
}
</style>