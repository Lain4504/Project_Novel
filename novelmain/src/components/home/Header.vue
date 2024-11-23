<script setup lang="ts">
import { 
    MenuOutlined, 
    SearchOutlined, 
    UserOutlined 
} from '@ant-design/icons-vue';
import { ref, watch } from 'vue';

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
</script>

<template>
    <nav class="bg-white p-4 shadow-md relative">
      <!-- Navbar container -->
      <div class="max-w-[90rem] mx-auto flex items-center justify-between">
        <!-- Mobile Menu Button (hamburger) -->
        <button 
          @click="isMenuOpen = !isMenuOpen" 
          class="text-black md:hidden mr-4 w-6 h-6 hover:text-gray-600 transition-colors"
        >
          <MenuOutlined />
        </button>
  
        <!-- Logo -->
        <RouterLink to="/" class="text-black text-xl font-semibold">
          LOGO
        </RouterLink>
  
        <!-- Desktop Menu -->
        <div class="hidden md:flex space-x-3 items-center relative">
          <!-- Danh mục dropdown -->
          <div class="relative">
            <button 
              @click="isCategoryMenuOpen = !isCategoryMenuOpen" 
              class="text-black text-sm hover:underline transition-all duration-300"
            >
              Category
            </button>
            <transition name="fade">
              <div 
                v-if="isCategoryMenuOpen" 
                class="absolute top-full mt-2 w-[20rem] bg-white shadow-lg rounded-lg border border-gray-200 grid grid-cols-3 gap-4 p-4 z-10"
              >
                <div 
                  v-for="(category, index) in categories.slice(0, 9)" 
                  :key="index" 
                  class="text-black text-sm hover:underline transition-all duration-300"
                >
                  {{ category }}
                </div>
                <button class="col-span-3 text-center text-blue-500 text-sm hover:underline">
                  View more
                </button>
              </div>
            </transition>
          </div>
          <a href="#" class="text-black text-sm hover:underline transition-all duration-300">Forum</a>
          <a href="#" class="text-black text-sm hover:underline transition-all duration-300">Support</a>
          <a href="#" class="text-black text-sm hover:underline transition-all duration-300">Ranking</a>
          <!-- Search bar -->
          <div class="relative">
            <input 
              type="text" 
              placeholder="Search..." 
              class="p-[0.4rem] rounded-full placeholder:text-sm bg-gray-100 text-black focus:outline-none focus:ring-1 focus:ring-[#889b6c] transition-all duration-300">
            <SearchOutlined class="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-400 cursor-pointer" />
          </div>
          <!-- Login/Register -->
              <RouterLink to="/login" class="text-black text-sm hover:underline transition-all duration-300">Login</RouterLink> 
              <RouterLink to="/register" class="text-black text-sm ml-4 hover:underline transition-all duration-300">Register</RouterLink>
        </div>
  
        <!-- Account Dropdown (only on mobile) -->
        <div class="relative ml-4 md:hidden">
          <button 
            @click="isAccountMenuOpen = !isAccountMenuOpen" 
            class="text-black w-6 h-6 hover:text-gray-600 transition-colors"
          >
            <UserOutlined />
          </button>
          <!-- Dropdown Menu -->
          <transition name="fade">
            <div 
              v-if="isAccountMenuOpen" 
              class="absolute right-0 mt-2 w-[9rem] bg-white shadow-lg rounded-lg border border-gray-200 text-sm z-10"
            >
              <RouterLink to="/login" class="block px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">Login</RouterLink>
              <RouterLink to="/register" class="block px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">Register</RouterLink>
            </div>
          </transition>
        </div>
      </div>
  
      <!-- Mobile Dropdown Menu (overlay) -->
      <transition name="fade">
        <div v-if="isMenuOpen" class="md:hidden fixed top-16 left-0 w-full h-full bg-black bg-opacity-50 z-50">
          <div class="bg-white text-black p-4 space-y-4">
            <!-- Search Bar for Mobile -->
            <div class="relative">
              <input 
                type="text" 
                placeholder="Search..." 
                class="p-[0.4rem] pr-10 rounded-full placeholder:text-sm w-full bg-gray-100 text-black focus:outline-none focus:ring-1 focus:ring-[#889b6c] transition-all duration-300">
              <SearchOutlined class="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 cursor-pointer" />
            </div>
            <div>
              <button 
                @click="isCategoryMenuOpen = !isCategoryMenuOpen"
                class="block w-full text-sm hover:underline text-left transition-all"
              >
                Category
              </button>
              <transition name="fade">
                <div 
                  v-if="isCategoryMenuOpen" 
                  class="grid grid-cols-3 gap-4 mt-2 p-4 bg-gray-100 rounded-lg"
                >
                  <div 
                    v-for="(category, index) in categories.slice(0, 9)" 
                    :key="index" 
                    class="text-black text-sm hover:underline transition-all duration-300"
                  >
                    {{ category }}
                  </div>
                  <button class="col-span-3 text-center text-blue-500 text-sm hover:underline">
                    View more
                  </button>
                </div>
              </transition>
            </div>
            <a href="#" class="block text-sm hover:underline transition-all duration-300">Forum</a>
            <a href="#" class="block text-sm hover:underline transition-all duration-300">Support</a>
            <a href="#" class="block text-sm hover:underline transition-all duration-300">Ranking</a>
          </div>
        </div>
      </transition>
    </nav>
</template>

<style scoped>
.fade-enter-active, .fade-leave-active {
    transition: opacity 0.3s ease-in-out;
}
.fade-enter-from, .fade-leave-to {
    opacity: 0;
}

/* Đảm bảo menu mobile phủ lên phần còn lại mà không che thanh header */
.md:hidden.fixed {
    position: fixed;
    top: 4rem; /* Dịch menu xuống dưới header */
}
</style>
