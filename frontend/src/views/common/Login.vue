<script lang="ts" setup>
import {Button, Checkbox, Form, Input, message} from 'ant-design-vue';
import {GoogleOutlined, LockOutlined, UserOutlined} from '@ant-design/icons-vue';
import {ref} from 'vue';
import {useStore} from 'vuex';
import {useRouter} from 'vue-router';
import {login} from '@/api/auth';
import {getMyInfo} from '@/api/user';
import ForgotPasswordModal from '@/components/common/ForgotPasswordModal.vue';
import {RuleObject} from 'ant-design-vue/es/form';

const store = useStore();
const router = useRouter();
const isModalVisible = ref(false);
const form = ref();

const formState = ref({
  email: '',
  password: '',
  remember: false
});

const rules: { [k: string]: RuleObject | RuleObject[] } = {
  email: [
    {required: true, message: 'Please input your email!'},
    {type: 'email', message: 'Please enter a valid email address!'}
  ],
  password: [
    {required: true, message: 'Please input your password!'},
  ]
};

const handleFinish = async (values: any) => {
  try {
    const response = await login({
      email: values.email,
      password: values.password
    });
    store.commit('setToken', response.token);
    const userData = await getMyInfo();
    store.commit('setUser', userData);
    store.commit('setRefreshToken', response.refreshToken);

    message.success('Login successful. Redirecting to home page...');

    setTimeout(() => {
      router.push('/');
    }, 1000);
  } catch (error: any) {
    if (error.response) {
      message.error(error.response.data.message || 'Login failed. Please try again.');
    } else if (error.request) {
      message.error('No response from server. Please try again.');
    } else {
      message.error('An unexpected error occurred. Please try again.');
    }
  }
};

const handleGoogleLogin = () => {
  message.info('In development. Please use email and password to login.');
};

const openModal = () => {
  isModalVisible.value = true;
};
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 px-4">
    <div class="w-full max-w-md bg-white border border-gray-300 shadow-lg p-6 rounded-lg">
      <h2 class="text-center text-2xl font-bold mb-8">Login</h2>

      <Form
          ref="form"
          :model="formState"
          :rules="rules"
          @finish="handleFinish"
      >
        <Form.Item name="email">
          <Input
              v-model:value="formState.email"
              placeholder="Email"
              size="large"
          >
            <template #prefix>
              <UserOutlined class="text-gray-400"/>
            </template>
          </Input>
        </Form.Item>

        <Form.Item name="password">
          <Input.Password
              v-model:value="formState.password"
              placeholder="Password"
              size="large"
          >
            <template #prefix>
              <LockOutlined class="text-gray-400"/>
            </template>
          </Input.Password>
        </Form.Item>

        <div class="flex justify-between mb-4">
          <Form.Item :wrapper-col="{ span: 24 }" class="mb-0" name="remember">
            <Checkbox v-model:checked="formState.remember">
              Remember me
            </Checkbox>
          </Form.Item>
          <a class="text-primary hover:text-primary-dark" @click="openModal">
            Forgot password?
          </a>
        </div>

        <Form.Item>
          <Button class="w-full" html-type="submit" size="large" type="primary">
            Log in
          </Button>
        </Form.Item>

        <div class="flex items-center justify-center my-4">
          <div class="flex-grow border-t border-gray-300"></div>
          <span class="px-4 text-gray-500">or</span>
          <div class="flex-grow border-t border-gray-300"></div>
        </div>

        <Button
            class="w-full"
            size="large"
            @click="handleGoogleLogin"
        >
          <GoogleOutlined/>
          Login with Google
        </Button>

        <div class="text-center mt-4">
          <span class="text-gray-600">Don't have an account? </span>
          <router-link class="text-primary hover:text-primary-dark" to="/register">
            Sign up
          </router-link>
        </div>
      </Form>
    </div>

    <ForgotPasswordModal
        :isModalVisible="isModalVisible"
        @update:isModalVisible="isModalVisible = $event"
    />
  </div>
</template>

<style scoped>
.ant-input-affix-wrapper {
  background-color: #fff !important;
}
</style>