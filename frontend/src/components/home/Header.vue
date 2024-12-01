<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref, watch, computed } from 'vue';
import { useStore } from 'vuex';
import { logout } from '../../api/user';
import router from '../../router';

// Trạng thái cho menu trên mobile
const isMenuOpen = ref(false);

// Trạng thái cho dropdown tài khoản
const isAccountMenuOpen = ref(false);

// Trạng thái cho dropdown danh mục
const isCategoryMenuOpen = ref(false);

// Danh sách danh mục mẫu
const categories = [
  "Action", "Fantasy", "Romance",
  "Horror", "Adventure", "Comedy",

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
watch(isCategoryMenuOpen, (newVal) => {
  if (newVal) {
    isAccountMenuOpen.value = false;
  }
});

const store = useStore();
const isAuthenticated = computed(() => store.getters.isAuthenticated || '');

const handleLogout = async () => {
  try {
    // Get token and refresh token from Vuex store
    const accessToken = store.getters.getToken;
    const refreshToken = store.getters.getRefreshToken;

    if (!accessToken || !refreshToken) {
      console.error('Tokens are missing');
      return;
    }

    // Call the logout API and pass both token and refreshToken
    const result = await logout({
      refreshtoken: refreshToken,
      accessToken: accessToken
    });

    if (result) {
      // Clear the authentication state (e.g., remove token and user info)
      store.commit('clearUser'); // Adjust to your Vuex store mutation for logout
    }
  } catch (error) {
    console.error('Logout failed:', error);
    alert('Logout failed. Please try again.');
  }
};
const dropdownMenu = [
  {
    label: 'User Profile',
    icon: 'fa-solid fa-user',
    link: '/member',
  },
  {
    label: 'Bookmark',
    icon: 'fa-solid fa-bookmark',
    link: '/list/bookmark',
  },
  {
    label: 'Library',
    icon: 'fa-solid fa-book',
    link: '/library',
  },
  {
    label: 'Setting',
    icon: 'fa-solid fa-gear',
    link: '/user-profile',
  },
  {
    label: 'Transaction History',
    icon: 'fa-solid fa-money-bill',
    link: '/history',
  },
  {
    label: 'Logout',
    icon: 'fa-solid fa-right-from-bracket',
    action: handleLogout,
  },
];

// Hàm đóng menu khi chọn một mục trong dropdown
const closeMenu = () => {
  isMenuOpen.value = false;
  isAccountMenuOpen.value = false;
  isCategoryMenuOpen.value = false;
};


</script>


<template>
  <nav class="bg-white p-4 shadow-md relative">
    <!-- Navbar container -->
    <div class="max-w-[90rem] mx-auto flex items-center justify-between">
      <!-- Mobile Menu Button (hamburger) + LOGO -->
      <div class="flex items-center space-x-4 md:space-x-0">
        <!-- Mobile Menu Button -->
        <button @click="isMenuOpen = !isMenuOpen"
          class="text-black md:hidden w-6 h-6 hover:text-gray-600 transition-colors">
          <font-awesome-icon icon="fa-solid fa-bars" />
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
              <RouterLink to="" class="col-span-3 text-center text-blue-500 text-sm hover:underline">
                <font-awesome-icon icon="fa-solid fa-arrow-up-right-from-square" />
              </RouterLink>
            </div>
          </transition>
        </div>

        <!-- Forum, Support, Ranking -->
        <RouterLink to="#" class="text-black text-sm hover:underline transition-all duration-300">Forum</RouterLink>
        <RouterLink to="#" class="text-black text-sm hover:underline transition-all duration-300">Support</RouterLink>
        <RouterLink to="#" class="text-black text-sm hover:underline transition-all duration-300">Ranking</RouterLink>

        <!-- Search bar -->
        <div class="relative flex items-center">
          <input type="text" placeholder="Search by author or name..."
            class="p-[0.4rem] rounded-full placeholder:text-sm placeholder:pl-1 bg-gray-100 text-black focus:outline-none focus:ring-1 focus:ring-[#889b6c] transition-all duration-300">
          <font-awesome-icon icon="fa-solid fa-magnifying-glass"
            class="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-700 cursor-pointer" />
        </div>
        <!-- Bell và Avatar -->
        <div v-if="isAuthenticated" class="flex items-center space-x-3">
          <div class="relative">
            <RouterLink to="/dashboard"
              class="flex items-center text-black hover:underline transition-all duration-300 text-sm">
              <!-- Writer Icon -->
              <font-awesome-icon :icon="['far', 'pen-to-square']" size="lg" class="mr-1" />
              Writting
            </RouterLink>
          </div>
          <!-- Bell -->
          <div class="relative">
            <font-awesome-icon icon="fa-regular fa-bell" size="xl"
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
                <div v-for="item in dropdownMenu" :key="item.label" @click="closeMenu"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">

                  <!-- If the item is "Logout", call the handleLogout function directly -->
                  <div v-if="item.label === 'Logout'" @click.prevent="handleLogout"
                    class="cursor-pointer flex items-center w-full">
                    <font-awesome-icon :icon="item.icon" class="mr-2" />
                    {{ item.label }}
                  </div>

                  <!-- For other items, use RouterLink as usual -->
                  <router-link v-else :to="item.link" class="flex items-center w-full">
                    <font-awesome-icon :icon="item.icon" class="mr-2" />
                    {{ item.label }}
                  </router-link>
                </div>
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
            <RouterLink to="/dashboard"
              class="flex items-center text-black hover:underline transition-all duration-300 text-sm">
              <!-- Writer Icon -->
              <font-awesome-icon :icon="['far', 'pen-to-square']" size="lg" class="mr-1" />
              Writting
            </RouterLink>

          </div>
          <!-- Bell icon remains the same -->
          <div class="relative mr-4">
            <font-awesome-icon icon="fa-regular fa-bell" size="xl"
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

                <div v-for="item in dropdownMenu" :key="item.label" @click="closeMenu"
                  class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">

                  <!-- If the item is "Logout", trigger handleLogout directly -->
                  <div v-if="item.label === 'Logout'" @click.prevent="handleLogout"
                    class="cursor-pointer flex items-center w-full">
                    <font-awesome-icon :icon="item.icon" class="mr-2" />
                    {{ item.label }}
                  </div>

                  <!-- For other items, use RouterLink -->
                  <router-link v-else :to="item.link" class="flex items-center w-full">
                    <font-awesome-icon :icon="item.icon" class="mr-2" />
                    {{ item.label }}
                  </router-link>
                </div>
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
            <font-awesome-icon icon="fa-solid fa-magnifying-glass"
              class="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-700 cursor-pointer" />
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
                <RouterLink to="" class="col-span-3 text-center text-blue-500 text-sm hover:underline">
                  <font-awesome-icon icon="fa-solid fa-arrow-up-right-from-square" />
                </RouterLink>
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
