<script setup lang="ts">
import {ref, computed, watch, inject} from 'vue';
import {RouterLink} from 'vue-router';
import {register} from '@/api/user';
import {useStore} from 'vuex';
import {useRouter} from 'vue-router';
import ConfirmModal from "@/components/common/ConfirmModal.vue";

const showAlert = inject('showAlert');

// Refs cho input
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const passwordVisible = ref(false);
const confirmPasswordVisible = ref(false);
const store = useStore();
const router = useRouter();
const showModal = ref(false);
// Kiểm tra định dạng email
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const isEmailValid = computed(() => emailRegex.test(email.value));

// Kiểm tra độ phức tạp của mật khẩu
const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d).{8,}$/;
const isPasswordValid = computed(() => passwordRegex.test(password.value));

// Kiểm tra mật khẩu và xác nhận mật khẩu có khớp không
const isPasswordMatched = computed(() => password.value === confirmPassword.value);

// Toggle password visibility
const togglePasswordVisibility = () => {
  passwordVisible.value = !passwordVisible.value;
};
const toggleConfirmPasswordVisibility = () => {
  confirmPasswordVisible.value = !confirmPasswordVisible.value;
};
watch(password, () => {
  confirmPassword.value = '';  // Reset confirmPassword when password changes
});
const showNotification = (type: string, message: string) => {
  showAlert(type, message);  // Call the global showAlert function
};
// Handle registration
const handleRegister = async () => {
  try {
    const response = await register({
      email: email.value,
      password: password.value
    });
    console.log(response);
    showModal.value = true;  // Show modal after successful registration
  } catch (error: any) {
    if (error.response) {
      showNotification('danger', error.response.data.message || 'Registration failed. Please try again.');
    } else if (error.request) {
      showNotification('danger', 'No response from server. Please try again.');
    } else {
      showNotification('danger', 'An unexpected error occurred. Please try again.');
    }
  }
};
// Slider logic
const slides = ref([
  {id: 1, img: 'https://placehold.co/600x400', content: 'Content for Slide 1'},
  {id: 2, img: 'https://placehold.co/600x400', content: 'Content for Slide 2'},
  {id: 3, img: 'https://placehold.co/600x400', content: 'Content for Slide 3'},
  {id: 4, img: 'https://placehold.co/600x400', content: 'Content for Slide 4'},
  {id: 5, img: 'https://placehold.co/600x400', content: 'Content for Slide 5'},
]);
const currentSlide = ref(0);

// Computed to get the active slide
const activeSlide = computed(() => slides.value[currentSlide.value]);

// Method to go to a specific slide
const goToSlide = (index: number) => {
  currentSlide.value = index;
};
</script>

<template>
  <section class="bg-[#F5F4EF] min-h-screen flex items-center justify-center px-4 sm:px-6 lg:px-8">
    <div class="bg-white flex flex-col md:flex-row rounded-2xl shadow-lg max-w-4xl w-full overflow-hidden">
      <!-- Left Side -->
      <div class="hidden lg:block lg:w-1/2 bg-[#F0EEE5] text-gray-700 p-10">
        <h2 class="text-2xl font-bold mb-5">Welcome to Our Platform!</h2>
        <p class="text-sm mb-5">
          Sign up to join our community and explore exclusive content.
        </p>
        <!-- Slider -->
        <div class="relative w-full overflow-hidden rounded-lg">
          <!-- Content (Outside of Image) -->
          <div class="mb-4 p-4 shadow">
            <p class="text-gray-700 text-sm">{{ activeSlide.content }}</p>
          </div>
          <!-- Active Image -->
          <img :src="activeSlide.img" alt="" class="block w-full h-56 object-cover rounded-lg"/>
        </div>
        <!-- Dots -->
        <div class="flex justify-center mt-4 space-x-3">
          <button v-for="(slide, index) in slides" :key="slide.id" @click="goToSlide(index)"
                  class="w-2 h-2 rounded-full" :class="{
              'bg-gray-700': currentSlide === index,
              'bg-gray-300': currentSlide !== index,
            }"></button>
        </div>
      </div>
      <!-- Right Side -->
      <div class="w-full md:w-1/2 p-6 sm:p-10 max-w-md md:max-w-lg mx-auto">
        <h2 class="text-3xl font-bold text-center text-gray-800">Register</h2>
        <p class="text-sm text-center text-gray-600 mt-2">Join us and explore the platform!</p>
        <form @submit.prevent="handleRegister" class="mt-8 space-y-6">
          <!-- Email -->
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
            <input v-model="email" type="email" id="email" name="email" placeholder="Enter your email" required
                   class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm"/>
            <p v-if="!isEmailValid && email" class="text-sm text-red-500 mt-1">
              Please enter a valid email address.
            </p>
          </div>
          <!-- Password -->
          <div class="relative flex flex-col">
            <label for="password" class="block text-sm font-medium text-gray-700">Password</label>

            <div class="relative">
              <input v-model="password" :type="passwordVisible ? 'text' : 'password'" id="password" name="password"
                     placeholder="Enter your password" required
                     class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm"/>
              <span @click="togglePasswordVisibility"
                    class="absolute right-3 top-1/2 bottom-[0.05rem] transform -translate-y-1/2 text-gray-500 cursor-pointer">
                <font-awesome-icon :icon="passwordVisible ? 'fa-regular fa-eye' : 'fa-regular fa-eye-slash'"/>
              </span>
            </div>

            <p v-if="!isPasswordValid && password" class="text-sm text-red-500 mt-1">
              Password must be at least 8 characters long, contain an uppercase letter, and a number.
            </p>
          </div>

          <!-- Confirm Password -->
          <div class="relative flex flex-col">
            <label for="confirm-password" class="block text-sm font-medium text-gray-700">Confirm Password</label>

            <div class="relative">
              <input v-model="confirmPassword" :type="confirmPasswordVisible ? 'text' : 'password'"
                     id="confirm-password" name="confirm-password" placeholder="Re-enter your password" required
                     :disabled="!isPasswordValid"
                     class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm"/>
              <span @click="toggleConfirmPasswordVisibility"
                    class="absolute right-3 top-1/2 bottom-[0.05rem] transform -translate-y-1/2 text-gray-500 cursor-pointer">
                <font-awesome-icon :icon="confirmPasswordVisible  ? 'fa-regular fa-eye' : 'fa-regular fa-eye-slash'"/>
              </span>
            </div>

            <p v-if="!isPasswordMatched && confirmPassword" class="text-sm text-red-500 mt-1">
              Passwords do not match.
            </p>
          </div>


          <!-- Submit Button -->
          <div>
            <button type="submit"
                    class="w-full bg-[#C15E3C] text-white py-2 px-4 rounded-md hover:bg-[#d76843] focus:ring-2 focus:ring-[#889b6c] focus:outline-none"
                    :disabled="!isEmailValid || !isPasswordValid || !isPasswordMatched">
              Sign Up
            </button>
          </div>
          <!-- Alternative Login -->
          <div class="flex items-center justify-between mt-4">
            <div class="flex-grow border-t border-gray-300"></div>
            <span class="text-gray-500 text-sm mx-4">or</span>
            <div class="flex-grow border-t border-gray-300"></div>
          </div>
          <div class="flex items-center justify-center space-x-2 mt-4">
            <button type="button"
                    class="w-full flex items-center justify-center bg-gray-50 border text-gray-700 py-2 px-4 rounded-md hover:bg-gray-100 focus:ring-2 focus:ring-gray-500 focus:outline-none">
              <font-awesome-icon :icon="['fab', 'google']" class="mr-2 text-red-600"/>
              Sign Up with Google
            </button>
          </div>
        </form>
        <p class="text-center text-sm text-gray-600 mt-6">
          Already have an account?
          <RouterLink to="/login" class="text-[#C15E3C] hover:underline">Login</RouterLink>
        </p>
      </div>
    </div>
    <ConfirmModal
        v-if="showModal"
        title="Registration Successful"
        content="You have successfully registered! Please check your email for verification."
        confirmText="OK"
        cancelText=""
        @close="showModal = false"
    />
  </section>
</template>
