<script lang="ts" setup>
import {message} from 'ant-design-vue';
import {ref} from 'vue';
import {useStore} from 'vuex';
import {useRouter} from 'vue-router';
import {login} from '../../api/auth';
import {getMyInfo, getUserProfile} from '../../api/user';
import ForgotPasswordModal from '../../components/auth/ForgotPasswordModal.vue';
import type { RuleObject } from 'ant-design-vue/es/form';
import bannerUrl from '../../assets/banner.webp';

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
    const userProfileData = await getUserProfile(userData.id);
    store.commit('setUser', {...userData, image: userProfileData.image.path});
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
  <a-layout class="relative min-h-screen bg-cover bg-center" :style="{ background: `url(${bannerUrl})`}">
    <div class="absolute inset-0 bg-black bg-opacity-75 z-1"></div>
    <a-layout-content class="relative z-2 flex flex-col justify-center items-center p-6">
      <div class="text-center mb-8">
        <a-typography-title
            style="
            font-size: 2em;
            color: #fff;
            filter: drop-shadow(0.05em 0.05em black);
          "
        >
          HawkNovel
        </a-typography-title>
      </div>
      <section class="w-full max-w-md bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-center text-2xl font-bold mb-8">Login</h2>
        <a-form
            ref="form"
            :model="formState"
            :rules="rules"
            @finish="handleFinish"
        >
          <a-form-item name="email">
            <a-input
                v-model:value="formState.email"
                placeholder="Email"
                size="large"
            >
              <template #prefix>
                <font-awesome-icon :icon="['far', 'envelope']" class="text-gray-400"/>
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
                <font-awesome-icon :icon="['fas', 'lock']" class="text-gray-400"/>
              </template>
            </a-input-password>
          </a-form-item>

          <div class="flex justify-between mb-4">
            <a-form-item :wrapper-col="{ span: 24 }" class="mb-0" name="remember">
              <a-checkbox v-model:checked="formState.remember">
                Remember me
              </a-checkbox>
            </a-form-item>
            <a class="text-primary hover:text-[#18A058]" @click="openModal">
              Forgot password?
            </a>
          </div>

          <a-form-item>
            <a-button class="w-full" html-type="submit" size="large" type="primary">
              Log in
            </a-button>
          </a-form-item>

          <div class="flex items-center justify-center my-4">
            <div class="flex-grow border-t border-gray-300"></div>
            <span class="px-4 text-gray-500">or</span>
            <div class="flex-grow border-t border-gray-300"></div>
          </div>

          <a-button
              class="w-full"
              size="large"
              @click="handleGoogleLogin"
          >
            <font-awesome-icon :icon="['fab', 'google']" class="mr-2"/>
            Login with Google
          </a-button>

          <div class="text-center mt-4">
            <span class="text-gray-600">Don't have an account? </span>
            <router-link class="text-primary hover:text-[#18A058]" to="/register">
              Sign up
            </router-link>
          </div>
        </a-form>
      </section>

      <ForgotPasswordModal
          :isModalVisible="isModalVisible"
          @update:isModalVisible="isModalVisible = $event"
      />
    </a-layout-content>
  </a-layout>
</template>