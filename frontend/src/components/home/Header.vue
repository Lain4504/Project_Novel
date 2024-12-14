<script setup lang="ts">
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';
import {ref, watch, computed, onMounted, onUnmounted} from 'vue';
import {useStore} from 'vuex';
import {logout} from '@/api/auth';
import NotificationDropdown from '../common/BellNotificationDropdown.vue';
import {getUserProfile} from "@/api/user";

const isAccountMenuOpen = ref(false);
const isCategoryMenuOpen = ref(false);
const isNotificationListOpen = ref(false);
const isMobileMenuOpen = ref(false); // Trạng thái riêng cho menu icon
const categories = [
  "Action", "Fantasy", "Romance",
  "Horror", "Adventure", "Comedy",
];
const userProfile = ref({
  id: '',
  image: '',
  username: '',
});
const fetchUserProfile = async () => {
  try {
    const userProfileData = await getUserProfile(store.getters.getUserId);
    userProfile.value = userProfileData;
    userProfile.value.image = userProfileData.image.path;
  } catch (error) {
    console.error('Error fetching user profile:', error);
  }
};
onMounted(() => {
  fetchUserProfile();
});
watch(isMobileMenuOpen, (newVal) => {
  if (newVal) {
    isAccountMenuOpen.value = false;
    isCategoryMenuOpen.value = false;
    isNotificationListOpen.value = false;
  }
});

watch(isAccountMenuOpen, (newVal) => {
  if (newVal) {
    isMobileMenuOpen.value = false;
    isCategoryMenuOpen.value = false;
    isNotificationListOpen.value = false;
  }
});
watch(isCategoryMenuOpen, (newVal) => {
  if (newVal) {
    isAccountMenuOpen.value = false;
    isNotificationListOpen.value = false;
  }
});
watch(isNotificationListOpen, (newVal) => {
  if (newVal) {
    isAccountMenuOpen.value = false;
    isCategoryMenuOpen.value = false;
  }
});

const store = useStore();
const isAuthenticated = computed(() => store.getters.isAuthenticated || '');

const handleLogout = async () => {
  try {
    const accessToken = store.getters.getToken;
    const refreshToken = store.getters.getRefreshToken;

    if (!accessToken || !refreshToken) {
      console.error('Tokens are missing');
      return;
    }
    await logout({
      refreshToken: refreshToken,
      accessToken: accessToken
    });
    store.commit('clearUser');
  } catch (error) {
    console.error('Logout failed:', error);
    alert('Logout failed. Please try again.');
  }
};

const dropdownMenu = [
  {
    label: 'User Profile',
    icon: 'fa-solid fa-user',
    link: '/account',
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

const closeMenu = () => {
  isAccountMenuOpen.value = false;
  isCategoryMenuOpen.value = false;
  isNotificationListOpen.value = false;
  isMobileMenuOpen.value = false; // Đóng luôn menu icon
};

const unreadNotifications = ref(1);
const notifications = ref([
  {
    id: 1,
    user: "Jese Leos",
    message: 'Hey, what\'s up? All set for the presentation?',
    time: 'a few moments ago',
    iconColor: 'bg-blue-600',
  },
  {
    id: 2,
    user: "Joseph Mcfall",
    message: 'and 5 others started following you.',
    time: '10 minutes ago',
    iconColor: 'bg-gray-900',
  },
]);

const toggleNotificationList = () => {
  isNotificationListOpen.value = !isNotificationListOpen.value;
};

const handleClickOutside = (event: MouseEvent) => {
  const target = event.target as HTMLElement;
  if (!target.closest('.dropdown')) {
    closeMenu();
  }
};

onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
});
</script>

<template>
  <nav class="bg-[#F0EEE5] p-4 shadow-md relative">
    <div class="max-w-[90rem] mx-auto flex items-center justify-between">
      <div class="flex items-center space-x-4 md:space-x-0">
        <button @click.stop="isMobileMenuOpen = !isMobileMenuOpen"
                class="text-black md:hidden w-6 h-6 hover:text-gray-600 transition-colors">
          <font-awesome-icon icon="fa-solid fa-bars"/>
        </button>
        <router-link to="/" class="text-black text-xl font-semibold">
          LOGO
        </router-link>
      </div>

      <div class="hidden md:flex space-x-3 items-center relative">
        <div class="relative dropdown">
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
              <router-link to="" class="col-span-3 text-center text-blue-500 text-sm hover:underline">
                <font-awesome-icon icon="fa-solid fa-arrow-up-right-from-square"/>
              </router-link>
            </div>
          </transition>
        </div>

        <router-link to="/post-forum" class="text-black text-sm hover:underline transition-all duration-300">Forum
        </router-link>
        <router-link to="#" class="text-black text-sm hover:underline transition-all duration-300">Support</router-link>
        <router-link to="#" class="text-black text-sm hover:underline transition-all duration-300">Ranking</router-link>

        <div class="relative flex items-center">
          <input type="text" placeholder="Search by author or name..."
                 class="p-[0.4rem] rounded-full placeholder:text-sm placeholder:pl-1 bg-gray-100 text-black focus:outline-none focus:ring-1 focus:ring-[#889b6c] transition-all duration-300">
          <font-awesome-icon icon="fa-solid fa-magnifying-glass"
                             class="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-700 cursor-pointer"/>
        </div>

        <div v-if="isAuthenticated" class="flex items-center space-x-3">
          <div class="relative dropdown">
            <router-link to="/dashboard"
                         class="flex items-center text-black hover:underline transition-all duration-300 text-sm">
              <font-awesome-icon :icon="['far', 'pen-to-square']" size="lg" class="mr-1"/>
              Writting
            </router-link>
          </div>

          <div class="relative dropdown">
            <div @click="toggleNotificationList" class="relative">
              <font-awesome-icon icon="fa-regular fa-bell" size="xl"
                                 class="cursor-pointer text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-pulse focus:outline-none"/>
              <span
                  class="absolute -right-1 -bottom-1 w-4 text-center leading-4 bg-black text-white aspect-square rounded-full text-[8px]">
                {{ unreadNotifications }}
              </span>
            </div>

            <transition name="fade">
              <div v-if="isNotificationListOpen"
                   class="absolute right-0 top-full mt-2 w-80 bg-white shadow-lg rounded-lg border border-gray-200 z-20">
                <NotificationDropdown :notifications="notifications"/>
              </div>
            </transition>
          </div>

          <div class="relative dropdown">
            <img
                class="w-10 h-10 rounded-full border-2 border-gray-50 transition-transform duration-200 hover:scale-110 hover:border-blue-500 cursor-pointer"
                :src="userProfile.image" alt="" @click="isAccountMenuOpen = !isAccountMenuOpen"/>
            <transition name="fade">
              <div v-if="isAccountMenuOpen"
                   class="absolute right-0 mt-2 w-[10rem] bg-white shadow-lg rounded-lg border border-gray-200 text-sm z-10">
                <div v-for="item in dropdownMenu" :key="item.label" @click="closeMenu"
                     class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <div v-if="item.label === 'Logout'" @click.prevent="handleLogout"
                       class="cursor-pointer flex items-center w-full">
                    <font-awesome-icon :icon="item.icon" class="mr-2"/>
                    {{ item.label }}
                  </div>
                  <router-link v-else v-if="item.link" :to="item.link" class="flex items-center w-full">
                    <font-awesome-icon :icon="item.icon" class="mr-2"/>
                    {{ item.label }}
                  </router-link>
                </div>
              </div>
            </transition>
          </div>
        </div>

        <div v-else>
          <router-link to="/login" class="text-black text-sm hover:underline transition-all duration-300">Login
          </router-link>
          <router-link to="/register" class="text-black text-sm ml-4 hover:underline transition-all duration-300">
            Register
          </router-link>
        </div>
      </div>

      <div class="flex items-center md:hidden">
        <div v-if="isAuthenticated" class="flex items-center space-x-4">
          <div class="relative dropdown">
            <router-link to="/dashboard"
                         class="flex items-center text-black hover:underline transition-all duration-300 text-sm">
              <font-awesome-icon :icon="['far', 'pen-to-square']" size="lg" class="mr-1"/>
              Writting
            </router-link>
          </div>

          <div class="relative dropdown">
            <div @click="toggleNotificationList" class="relative">
              <font-awesome-icon icon="fa-regular fa-bell" size="xl"
                                 class="cursor-pointer text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-pulse focus:outline-none"/>
              <span
                  class="absolute -right-1 -bottom-1 w-4 text-center leading-4 bg-black text-white aspect-square rounded-full text-[8px]">
                {{ unreadNotifications }}
              </span>
            </div>

            <transition name="fade">
              <div v-if="isNotificationListOpen"
                   class="absolute right-0 top-full mt-2 w-80 bg-white shadow-lg rounded-lg border border-gray-200 z-20">
                <NotificationDropdown :notifications="notifications"/>
              </div>
            </transition>
          </div>
          <div class="relative dropdown">
            <img
                class="w-10 h-10 rounded-full border-2 border-gray-50 transition-transform duration-200 hover:scale-110 hover:border-blue-500 cursor-pointer"
                :src="userProfile.image" @click="isAccountMenuOpen = !isAccountMenuOpen"/>
            <transition name="fade">
              <div v-if="isAccountMenuOpen"
                   class="absolute right-0 mt-2 w-[10rem] bg-white shadow-lg rounded-lg border border-gray-200 text-sm z-10">
                <div v-for="item in dropdownMenu" :key="item.label" @click="closeMenu"
                     class="flex items-center px-4 py-2 text-black hover:bg-gray-100 hover:underline transition-all duration-300">
                  <div v-if="item.label === 'Logout'" @click.prevent="handleLogout"
                       class="cursor-pointer flex items-center w-full">
                    <font-awesome-icon :icon="item.icon" class="mr-2"/>
                    {{ item.label }}
                  </div>
                  <router-link v-else v-if="item.link" :to="item.link" class="flex items-center w-full">
                    <font-awesome-icon :icon="item.icon" class="mr-2"/>
                    {{ item.label }}
                  </router-link>
                </div>
              </div>
            </transition>
          </div>
        </div>

        <div v-else class="flex items-center space-x-4">
          <router-link to="/login" class="text-black text-sm hover:underline transition-all duration-300">
            Login
          </router-link>
          <router-link to="/register" class="text-black text-sm hover:underline transition-all duration-300">
            Register
          </router-link>
        </div>
      </div>
    </div>

    <transition name="fade">
      <div v-if="isMobileMenuOpen" class="md:hidden fixed top-16 left-0 w-full h-full bg-black bg-opacity-50 z-50">
        <div class="bg-white text-black p-4 space-y-4">
          <div class="relative">
            <input type="text" placeholder="Search..."
                   class="p-[0.4rem] pr-10 rounded-full placeholder:text-sm w-full bg-gray-100 text-black focus:outline-none focus:ring-1 focus:ring-[#889b6c] transition-all duration-300">
            <font-awesome-icon icon="fa-solid fa-magnifying-glass"
                               class="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-700 cursor-pointer"/>
          </div>
          <div>
            <button @click.stop="isCategoryMenuOpen = !isCategoryMenuOpen"
                    class="block w-full text-sm hover:underline text-left transition-all">
              Category
            </button>
            <transition name="fade">
              <div v-if="isCategoryMenuOpen" class="grid grid-cols-3 gap-4 mt-2 p-4 bg-gray-100 rounded-lg">
                <div v-for="(category, index) in categories.slice(0, 9)" :key="index"
                     class="text-black text-sm hover:underline transition-all duration-300">
                  {{ category }}
                </div>
                <router-link to="" class="col-span-3 text-center text-blue-500 text-sm hover:underline">
                  <font-awesome-icon icon="fa-solid fa-arrow-up-right-from-square"/>
                </router-link>
              </div>
            </transition>
          </div>
          <router-link to="/post-forum" class="block text-sm hover:underline transition-all duration-300">Forum
          </router-link>
          <router-link to="#" class="block text-sm hover:underline transition-all duration-300">Support</router-link>
          <router-link to="#" class="block text-sm hover:underline transition-all duration-300">Ranking</router-link>
        </div>
      </div>
    </transition>
  </nav>
</template>