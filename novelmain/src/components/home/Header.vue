<script setup lang="ts">
import {
  MenuOutlined,
  SearchOutlined,
  BellOutlined
} from '@ant-design/icons-vue';
import { ref, watch, computed } from 'vue';
import { useStore } from 'vuex';

// Trạng thái cho menu trên mobile
const isMenuOpen = ref(false);

// Trạng thái cho dropdown tài khoản
const isAccountMenuOpen = ref(false);

// Trạng thái cho dropdown danh mục
const isCategoryMenuOpen = ref(false);

// Danh sách danh mục mẫu
const categories = [
  "Category 1", "Category 2", "Category 3",
  "Category 4", "Category 5", "Category 6",
  "Category 7", "Category 8", "Category 9",
  "Category 10", "Category 11", "Category 12",
];

// Đồng bộ trạng thái giữa các menu
watch(isMenuOpen, (newVal) => {
  if (newVal) {
    isAccountMenuOpen.value = false;
    isCategoryMenuOpen.value = false;
  }
});

watch(isAccountMenuOpen, (newVal) => {
  if (newVal) {
    isMenuOpen.value = false;
    isCategoryMenuOpen.value = false;
  }
});
const store = useStore();
const isAuthenticated = computed(() => store.getters.isAuthenticated || '');
</script>

<template>
  <nav class="bg-white p-4 shadow-md relative">
    <!-- Navbar container -->
    <div class="max-w-[90rem] mx-auto flex items-center justify-between">
      <!-- Mobile Menu Button (hamburger) + LOGO -->
      <div class="flex items-center space-x-4 md:space-x-0">
        <!-- Mobile Menu Button -->
        <button @click="isMenuOpen = !isMenuOpen"
          class="text-black md:hidden mr-4 w-6 h-6 hover:text-gray-600 transition-colors">
          <MenuOutlined />
        </button>

        <!-- Logo -->
        <RouterLink to="/" class="text-black text-xl font-semibold">
          LOGO
        </RouterLink>
      </div>

      <!-- Desktop Menu -->
      <div class="hidden md:flex space-x-3 items-center relative">
        <!-- Danh mục dropdown -->
        <div class="relative">
          <button @click="isCategoryMenuOpen = !isCategoryMenuOpen"
            class="text-black text-sm hover:underline transition-all duration-300">
            Category
          </button>
          <transition name="fade">
            <div v-if="isCategoryMenuOpen"
              class="absolute top-full mt-2 w-[20rem] bg-white shadow-lg rounded-lg border border-gray-200 grid grid-cols-3 gap-4 p-4 z-10">
              <div v-for="(category, index) in categories.slice(0, 9)" :key="index"
                class="text-black text-sm hover:underline transition-all duration-300">
                {{ category }}
              </div>
              <button class="col-span-3 text-center text-blue-500 text-sm hover:underline">
                View more
              </button>
            </div>
          </transition>
        </div>

        <!-- Forum, Support, Ranking -->
        <RouterLink to="#" class="text-black text-sm hover:underline transition-all duration-300">Forum</RouterLink>
        <RouterLink to="#" class="text-black text-sm hover:underline transition-all duration-300">Support</RouterLink>
        <RouterLink to="#" class="text-black text-sm hover:underline transition-all duration-300">Ranking</RouterLink>

        <!-- Search bar -->
        <div class="relative flex items-center">
          <input type="text" placeholder="Search..."
            class="p-[0.4rem] rounded-full placeholder:text-sm bg-gray-100 text-black focus:outline-none focus:ring-1 focus:ring-[#889b6c] transition-all duration-300">
          <SearchOutlined class="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-400 cursor-pointer" />
        </div>

        <!-- Bell và Avatar -->
        <div v-if="isAuthenticated" class="flex items-center space-x-4">
          <div class="relative">
            <RouterLink to="/writter"
              class="flex items-center text-black hover:underline transition-all duration-300 text-sm">
              <!-- Writer Icon -->
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                class="bi bi-pencil mr-2" viewBox="0 0 16 16">
                <path
                  d="M12.146.354a2 2 0 0 1 2.828 2.828l-9.293 9.293a.5.5 0 0 1-.146.105l-3 1a.5.5 0 0 1-.605-.605l1-3a.5.5 0 0 1 .105-.146l9.293-9.293a2 2 0 0 1 2.828 2.828z" />
              </svg>
              Writter
            </RouterLink>
          </div>
          <!-- Bell -->
          <div class="relative">

            <BellOutlined style="font-size: 24px;"
              class="cursor-pointer text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-pulse focus:outline-none" />
            <span
              class="absolute -right-1 -bottom-1 w-4 text-center leading-4 bg-black text-white aspect-square rounded-full text-[8px]">
              1
            </span>
          </div>
         
          <!-- Account Dropdown -->
          <div class="relative">
            <img
              class="w-10 h-10 rounded-full border-2 border-gray-50 transition-transform duration-200 hover:scale-110 hover:border-blue-500 cursor-pointer"
              src="\src\assets\logo.jpg" @click="isAccountMenuOpen = !isAccountMenuOpen" />
            <transition name="fade">
              <div v-if="isAccountMenuOpen"
                class="absolute right-0 mt-2 w-[10rem] bg-white shadow-lg rounded-lg border border-gray-200 text-sm z-10">
                <RouterLink to="/user-profile"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- User Profile Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-person mr-2" viewBox="0 0 16 16">
                    <path
                      d="M8 8a3 3 0 1 0-3-3 3 3 0 0 0 3 3zM8 0a8 8 0 1 0 8 8A8 8 0 0 0 8 0zM1 8a7 7 0 1 1 14 0 7 7 0 0 1-14 0z" />
                  </svg>
                  User Profile
                </RouterLink>

                <RouterLink to="/bookmark"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- Bookmark Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-bookmark mr-2" viewBox="0 0 16 16">
                    <path d="M2 4v10l5-3 5 3V4H2z" />
                  </svg>
                  Bookmark
                </RouterLink>

                <RouterLink to="/library"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- Library Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-house-door mr-2" viewBox="0 0 16 16">
                    <path
                      d="M6.5 0L0 6v9.5A1.5 1.5 0 0 0 1.5 17h13A1.5 1.5 0 0 0 16 15.5V6l-6.5-6zM1 7.412l5.5-5.5L12 7.412V14h-2V8H6v6H4V7.412z" />
                  </svg>
                  Library
                </RouterLink>

                <RouterLink to="/settings"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- Settings Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-gear mr-2" viewBox="0 0 16 16">
                    <path d="M8 4a4 4 0 1 0 4 4 4 4 0 0 0-4-4zM8 14a6 6 0 1 1 6-6 6 6 0 0 1-6 6z" />
                  </svg>
                  Setting
                </RouterLink>

                <RouterLink to="/history"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- History Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-gear mr-2" viewBox="0 0 16 16">
                    <path d="M8 4a4 4 0 1 0 4 4 4 4 0 0 0-4-4zM8 14a6 6 0 1 1 6-6 6 6 0 0 1-6 6z" />
                  </svg>
                  Transaction History
                </RouterLink>
                <RouterLink to="/logout"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- Logout Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-door-open mr-2" viewBox="0 0 16 16">
                    <path
                      d="M11 0a1 1 0 0 1 1 1v7.586l2.707-2.707a1 1 0 1 1 1.414 1.414l-4 4a1 1 0 0 1-1.414 0l-4-4a1 1 0 1 1 1.414-1.414L10 8.586V1a1 1 0 0 1 1-1zM3 0a1 1 0 0 1 1 1v11a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V1a1 1 0 0 1 1-1h1z" />
                  </svg>
                  Logout
                </RouterLink>
              </div>
            </transition>
          </div>
        </div>

        <div v-else>
          <RouterLink to="/login" class="text-black text-sm hover:underline transition-all duration-300">Login
          </RouterLink>
          <RouterLink to="/register" class="text-black text-sm ml-4 hover:underline transition-all duration-300">
            Register</RouterLink>
        </div>
      </div>


      <!-- Mobile Account Section -->
      <div class="flex items-center md:hidden">
        <!-- Bell and Account for Authenticated Users -->
        <div v-if="isAuthenticated" class="flex items-center space-x-4">
          <div class="relative">
            <RouterLink to="/writter"
              class="flex items-center text-black hover:underline transition-all duration-300 text-sm">
              <!-- Writer Icon -->
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                class="bi bi-pencil mr-2" viewBox="0 0 16 16">
                <path
                  d="M12.146.354a2 2 0 0 1 2.828 2.828l-9.293 9.293a.5.5 0 0 1-.146.105l-3 1a.5.5 0 0 1-.605-.605l1-3a.5.5 0 0 1 .105-.146l9.293-9.293a2 2 0 0 1 2.828 2.828z" />
              </svg>
              Writter
            </RouterLink>

          </div>
          <!-- Bell icon remains the same -->
          <div class="relative mr-4">
            <BellOutlined style="font-size: 24px;"
              class="cursor-pointer text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-pulse focus:outline-none" />
            <span
              class="absolute -right-1 -bottom-1 w-4 text-center leading-4 bg-black text-white aspect-square rounded-full text-[8px]">
              1
            </span>
          </div>
         
          <!-- Account Dropdown remains the same -->
          <div class="relative">
            <img
              class="w-10 h-10 rounded-full border-2 border-gray-50 transition-transform duration-200 hover:scale-110 hover:border-blue-500 cursor-pointer"
              src="\src\assets\logo.jpg" @click="isAccountMenuOpen = !isAccountMenuOpen" />

            <transition name="fade">
              <div v-if="isAccountMenuOpen"
                class="absolute right-0 mt-2 w-[10rem] bg-white shadow-lg rounded-lg border border-gray-200 text-sm z-10">
                <RouterLink to="/user-profile"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- User Profile Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-person mr-2" viewBox="0 0 16 16">
                    <path
                      d="M8 8a3 3 0 1 0-3-3 3 3 0 0 0 3 3zM8 0a8 8 0 1 0 8 8A8 8 0 0 0 8 0zM1 8a7 7 0 1 1 14 0 7 7 0 0 1-14 0z" />
                  </svg>
                  User Profile
                </RouterLink>

                <RouterLink to="/bookmark"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- Bookmark Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-bookmark mr-2" viewBox="0 0 16 16">
                    <path d="M2 4v10l5-3 5 3V4H2z" />
                  </svg>
                  Bookmark
                </RouterLink>

                <RouterLink to="/library"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- Library Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-house-door mr-2" viewBox="0 0 16 16">
                    <path
                      d="M6.5 0L0 6v9.5A1.5 1.5 0 0 0 1.5 17h13A1.5 1.5 0 0 0 16 15.5V6l-6.5-6zM1 7.412l5.5-5.5L12 7.412V14h-2V8H6v6H4V7.412z" />
                  </svg>
                  Library
                </RouterLink>

                <RouterLink to="/settings"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- Settings Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-gear mr-2" viewBox="0 0 16 16">
                    <path d="M8 4a4 4 0 1 0 4 4 4 4 0 0 0-4-4zM8 14a6 6 0 1 1 6-6 6 6 0 0 1-6 6z" />
                  </svg>
                  Setting
                </RouterLink>

                <RouterLink to="/history"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- History Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-gear mr-2" viewBox="0 0 16 16">
                    <path d="M8 4a4 4 0 1 0 4 4 4 4 0 0 0-4-4zM8 14a6 6 0 1 1 6-6 6 6 0 0 1-6 6z" />
                  </svg>
                  Transaction History
                </RouterLink>
                <RouterLink to="/logout"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <!-- Logout Icon -->
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-door-open mr-2" viewBox="0 0 16 16">
                    <path
                      d="M11 0a1 1 0 0 1 1 1v7.586l2.707-2.707a1 1 0 1 1 1.414 1.414l-4 4a1 1 0 0 1-1.414 0l-4-4a1 1 0 1 1 1.414-1.414L10 8.586V1a1 1 0 0 1 1-1zM3 0a1 1 0 0 1 1 1v11a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V1a1 1 0 0 1 1-1h1z" />
                  </svg>
                  Logout
                </RouterLink>
              </div>
            </transition>
          </div>
        </div>

        <!-- Login/Register for Mobile -->
        <div v-else class="flex items-center space-x-4">
          <RouterLink to="/login" class="text-black text-sm hover:underline transition-all duration-300">
            Login
          </RouterLink>
          <RouterLink to="/register" class="text-black text-sm hover:underline transition-all duration-300">
            Register
          </RouterLink>
        </div>
      </div>
    </div>

    <!-- Mobile Dropdown Menu (overlay) -->
    <transition name="fade">
      <div v-if="isMenuOpen" class="md:hidden fixed top-16 left-0 w-full h-full bg-black bg-opacity-50 z-50">
        <div class="bg-white text-black p-4 space-y-4">
          <!-- Search Bar for Mobile -->
          <div class="relative">
            <input type="text" placeholder="Search..."
              class="p-[0.4rem] pr-10 rounded-full placeholder:text-sm w-full bg-gray-100 text-black focus:outline-none focus:ring-1 focus:ring-[#889b6c] transition-all duration-300">
            <SearchOutlined class="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 cursor-pointer" />
          </div>
          <div>
            <button @click="isCategoryMenuOpen = !isCategoryMenuOpen"
              class="block w-full text-sm hover:underline text-left transition-all">
              Category
            </button>
            <transition name="fade">
              <div v-if="isCategoryMenuOpen" class="grid grid-cols-3 gap-4 mt-2 p-4 bg-gray-100 rounded-lg">
                <div v-for="(category, index) in categories.slice(0, 9)" :key="index"
                  class="text-black text-sm hover:underline transition-all duration-300">
                  {{ category }}
                </div>
                <button class="col-span-3 text-center text-blue-500 text-sm hover:underline">
                  View more
                </button>
              </div>
            </transition>
          </div>
          <RouterLink to="#" class="block text-sm hover:underline transition-all duration-300">Forum</RouterLink>
          <RouterLink to="#" class="block text-sm hover:underline transition-all duration-300">Support</RouterLink>
          <RouterLink to="#" class="block text-sm hover:underline transition-all duration-300">Ranking</RouterLink>
        </div>
      </div>
    </transition>
  </nav>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease-in-out;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* Đảm bảo menu mobile phủ lên phần còn lại mà không che thanh header */
.md:hidden.fixed {
  position: fixed;
  top: 4rem;
  /* Dịch menu xuống dưới header */
}

.user-email {
  max-width: 100%;
  /* Hoặc một giá trị chiều rộng cụ thể như 200px */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
