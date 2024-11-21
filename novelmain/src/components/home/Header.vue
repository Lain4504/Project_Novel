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
  "Thể loại 1", "Thể loại 2", "Thể loại 3",
  "Thể loại 4", "Thể loại 5", "Thể loại 6",
  "Thể loại 7", "Thể loại 8", "Thể loại 9",
  "Thể loại 10", "Thể loại 11", "Thể loại 12",
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
        <div class="text-black text-xl font-semibold">
          LOGO
        </div>
  
        <!-- Desktop Menu -->
        <div class="hidden md:flex space-x-3 items-center relative">
          <!-- Danh mục dropdown -->
          <div class="relative">
            <button 
              @click="isCategoryMenuOpen = !isCategoryMenuOpen" 
              class="text-black text-sm hover:underline transition-all duration-300"
            >
              Danh mục
            </button>
            <transition name="fade">
              <div 
                v-if="isCategoryMenuOpen" 
                class="absolute top-full mt-2 w-[20rem] bg-white shadow-lg rounded-lg border border-gray-200 grid grid-cols-3 gap-4 p-4"
              >
                <div 
                  v-for="(category, index) in categories.slice(0, 9)" 
                  :key="index" 
                  class="text-black text-sm hover:underline transition-all duration-300"
                >
                  {{ category }}
                </div>
                <button class="col-span-3 text-center text-blue-500 text-sm hover:underline">
                  Xem thêm
                </button>
              </div>
            </transition>
          </div>
          <a href="#" class="text-black text-sm hover:underline transition-all duration-300">Thảo luận</a>
          <a href="#" class="text-black text-sm hover:underline transition-all duration-300">Hỗ trợ</a>
          <a href="#" class="text-black text-sm hover:underline transition-all duration-300">Bảng xếp hạng</a>
          <!-- Search bar -->
          <div class="relative">
            <input 
              type="text" 
              placeholder="Tìm kiếm..." 
              class="p-[0.4rem] rounded-full placeholder:text-sm bg-gray-100 text-black focus:outline-none focus:ring-1 focus:ring-[#889b6c] transition-all duration-300">
            <SearchOutlined class="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-400 cursor-pointer" />
          </div>
          <!-- Login/Register -->
              <RouterLink to="/login" class="text-black text-sm hover:underline transition-all duration-300">Đăng nhập</RouterLink> 
              <RouterLink to="/register" class="text-black text-sm ml-4 hover:underline transition-all duration-300">Đăng ký</RouterLink>
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
              class="absolute right-0 mt-2 w-[9rem] bg-white shadow-lg rounded-lg border border-gray-200 text-sm"
            >
              <a href="#" class="block px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">Đăng nhập</a>
              <a href="#" class="block px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">Đăng ký</a>
              <a href="#" class="block px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">Tài khoản</a>
              <a href="#" class="block px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">Lịch sử</a>
              <a href="#" class="block px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">Đánh dấu</a>
              <a href="#" class="block px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">Kệ sách</a>
              <a href="#" class="block px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">Nạp tiền</a>
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
                placeholder="Tìm kiếm..." 
                class="p-[0.4rem] pr-10 rounded-full placeholder:text-sm w-full bg-gray-100 text-black focus:outline-none focus:ring-1 focus:ring-[#889b6c] transition-all duration-300">
              <SearchOutlined class="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 cursor-pointer" />
            </div>
            <div>
              <button 
                @click="isCategoryMenuOpen = !isCategoryMenuOpen"
                class="block w-full text-sm hover:underline text-left transition-all"
              >
                Danh mục
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
                    Xem thêm
                  </button>
                </div>
              </transition>
            </div>
            <a href="#" class="block text-sm hover:underline transition-all duration-300">Thảo luận</a>
            <a href="#" class="block text-sm hover:underline transition-all duration-300">Hỗ trợ</a>
            <a href="#" class="block text-sm hover:underline transition-all duration-300">Bảng xếp hạng</a>
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
