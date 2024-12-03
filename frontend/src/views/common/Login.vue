<script setup lang="ts">
import { computed, ref } from 'vue';
import { RouterLink } from 'vue-router';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { login, getMyInfo } from '@/api/user'
const email = ref('');
const password = ref('');
const passwordVisible = ref(false);
const store = useStore();
const router = useRouter();
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const isEmailValid = computed(() => emailRegex.test(email.value));
import { inject } from 'vue';
const showAlert = inject('showAlert');
const showNotification = (type: string, message: string) => {
  showAlert(type, message);  // Call the global showAlert function
};
const togglePasswordVisibility = () => {
  passwordVisible.value = !passwordVisible.value;
};

const handleLogin = async () => {
  try {
    const response = await login({
      email: email.value,
      password: password.value
    });
    console.log(response);
    store.commit('setToken', response.token);
    const userData = await getMyInfo();
    store.commit('setUser', userData);
    console.log('User from store:', store.state.user);
    store.commit('setRefreshToken', response.refreshToken);
    console.log('Token from store:', store.state.token);
    console.log('Refresh Token from store:', store.state.refreshToken);
    showNotification('success', 'Login successful. Redirecting to home page...');
    setTimeout(() => {
      router.push('/');
    }, 1000);
  } catch (error: any) {
    if (error.response) {
      showNotification('danger', error.response.data.message || 'Login failed. Please try again.');
    } else if (error.request) {
      showNotification('danger', 'No response from server. Please try again.');
    } else {
      showNotification('danger', 'An unexpected error occurred. Please try again.');
    }
  }
};
const handleGoogleLogin = () => {
  showNotification('', 'In development. Please use email and password to login.');

};
</script>
<template>
  <section class="bg-[#e7f5dc] min-h-screen flex items-center justify-center px-4 sm:px-6 lg:px-8">
    <div class="bg-white flex flex-col md:flex-row rounded-2xl shadow-lg max-w-4xl w-full overflow-hidden">
      <!-- Left Side (Image or Intro Text) -->
      <div class="hidden md:block md:w-1/2 bg-[#b6c99b] text-white p-10">
        <h2 class="text-2xl font-bold mb-5">Welcome Back!</h2>
        <p class="text-sm">
          Join our platform and explore exclusive content. Login now to continue.
        </p>
        <div class="mt-96 relative z-10">
          <div class="bubble-effect"></div>
        </div>
      </div>

      <!-- Right Side (Login Form) -->
      <div class="w-full md:w-1/2 p-6 sm:p-10 max-w-md md:max-w-lg mx-auto">
        <h2 class="text-3xl font-bold text-center text-gray-800">Login</h2>
        <p class="text-sm text-center text-gray-600 mt-2">Welcome back! Please login to your account.</p>

        <form @submit.prevent="handleLogin" class="mt-8 space-y-6">
          <!-- Email -->
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
            <input v-model="email" type="email" id="email" name="email" placeholder="Enter your email" required
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm" />
            <p v-if="!isEmailValid && email" class="text-sm text-red-500 mt-1">
              Please enter a valid email address.
            </p>
          </div>

          <!-- Password -->
          <div class="relative">
            <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
            <input v-model="password" :type="passwordVisible ? 'text' : 'password'" id="password" name="password"
              placeholder="Enter your password" required
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm" />
            <!-- Custom Toggle Icon -->
            <span @click="togglePasswordVisibility"
              class="absolute right-3 bottom-[0.45rem] text-gray-500 cursor-pointer">
              <font-awesome-icon :icon="passwordVisible ? 'fa-regular fa-eye' : 'fa-regular fa-eye-slash'" />
            </span>
          </div>

          <div class="flex justify-between items-center">
            <div class="flex items-center">
              <input type="checkbox" id="remember" class="mr-2" />
              <label for="remember" class="text-sm text-[#98a77c]">
                Remember me
              </label>
            </div>
            <RouterLink to="/forgot-password" class="text-sm text-[#98a77c] hover:underline">
              Forgot password?
            </RouterLink>
          </div>

          <!-- Submit Button -->
          <div>
            <button type="submit"
              class="w-full bg-[#889b6c] text-white py-2 px-4 rounded-md hover:bg-[#728156] focus:ring-2 focus:ring-[#889b6c] focus:outline-none">
              Login
            </button>
          </div>

          <!-- Alternative Login -->
          <div class="flex items-center justify-between mt-4">
            <div class="flex-grow border-t border-gray-300"></div>
            <span class="text-gray-500 text-sm mx-4">or</span>
            <div class="flex-grow border-t border-gray-300"></div>
          </div>

          <div class="flex items-center justify-center space-x-2 mt-4">
            <button @click="handleGoogleLogin" type="button"
              class="w-full flex items-center justify-center bg-gray-50 border text-gray-700 py-2 px-4 rounded-md hover:bg-gray-100 focus:ring-2 focus:ring-gray-500 focus:outline-none">
              <!-- Google Login Button -->
              <font-awesome-icon :icon="['fab', 'google']" class="mr-2 text-red-600" />
              Login with Google
            </button>
          </div>
        </form>

        <p class="text-center text-sm text-gray-600 mt-6">
          Don't have an account?
          <RouterLink to="/register" class="text-[#98a77c] hover:underline">
            Sign up
          </RouterLink>
        </p>
      </div>
    </div>
  </section>
</template>