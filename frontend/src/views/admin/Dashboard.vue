<script setup lang="ts">
import {ref, onMounted, onBeforeUnmount} from 'vue';

enum Routes {
  NewNovel = '/new-novel',
  ExistingNovels = '/existing-novels',
  Analytics = '/analytics',
  Support = '/support',
  AdminNotification = '/admin-notification',
  Account = '/author-account',
  Payment = '/payment',
  FAQ = '/faq',
  PostCategory = '/post-category-for-author',
  BookCategory = '/novel-category-for-author',
  BookManagement = '/book-admin',
  PostManagement = '/post-admin',
  BookCategoryManagement = '/novel-category-list',
  PostCategoryManagement = '/post-category-list',
  AdsManagement = '/ads-admin',
  UserManagement = '/user-admin',
  AdminDashboard = '/admin-dashboard',
}

import NotificationDropdown from '@/components/common/BellNotificationDropdown.vue';

const showDropDown = ref<boolean>(false);
const showSide = ref<boolean>(true);
const isMobile = ref<boolean>(false);
const showMobileSide = ref<boolean>(false);
const isDropdownOpen = ref<boolean>(false);

const toggleDropdown = (): void => {
  isDropdownOpen.value = !isDropdownOpen.value;
};
// Toggle dropdown menu
const toggleDropdownMenu = (): void => {
  showDropDown.value = !showDropDown.value;
}
const handleItemClick = (): void => {
  isDropdownOpen.value = false;
};

const toggleSideBar = (): void => {
  if (isMobile.value) {
    showMobileSide.value = !showMobileSide.value;
  } else {
    showSide.value = !showSide.value;
  }
  showDropDown.value = false;
};

const handleResize = (): void => {
  isMobile.value = window.innerWidth <= 1280;
  if (isMobile.value) {
    showSide.value = false;
  } else {
    showMobileSide.value = false;
  }
};

onMounted(() => {
  handleResize();
  window.addEventListener('resize', handleResize);
});

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize);
});
const MenuItems = [
  {
    label: 'My Book',
    isParent: true, // Indicates it's a parent item
    children: [
      {to: Routes.NewNovel, label: 'Add Novel', icon: 'fa-solid fa-square-plus'},
      {to: Routes.ExistingNovels, label: 'Existed Novel', icon: 'fa-solid fa-book'},
      {to: Routes.Analytics, label: 'Analytics', icon: 'fa-solid fa-chart-pie'},
    ]
  },
  {
    label: 'Notification and Support',
    isParent: true,
    children: [
      {to: Routes.Support, label: 'Support', icon: 'fa-solid fa-phone'},
      {to: Routes.AdminNotification, label: 'Notification', icon: 'fa-solid fa-bullhorn'},
    ]
  },
  {
    label: 'Information',
    isParent: true,
    children: [
      {to: Routes.FAQ, label: 'FAQ', icon: 'fa-solid fa-circle-question'},
      {to: Routes.BookCategory, label: 'Book Category', icon: 'fa-solid fa-book-open'},
      {to: Routes.PostCategory, label: 'Post Category', icon: 'fa-solid fa-newspaper'},
    ]
  }
];

const featureMenuItems = [
  {
    to: Routes.BookManagement,
    label: 'Book Management',
  },
  {
    to: Routes.PostManagement,
    label: 'Post Management',
  },
  {
    to: Routes.BookCategoryManagement,
    label: 'Book Category Management',
  },
  {
    to: Routes.PostCategoryManagement,
    label: 'Post Category Management',
  },
  {
    to: Routes.AdsManagement,
    label: 'Ads Management',
  },
  {
    to: Routes.UserManagement,
    label: 'User Management',
  },
  {
    to: Routes.AdminDashboard,
    label: 'Admin Dashboard',
  }
];
const dropdownItems = [
  {to: Routes.Account, label: 'Account'},
  {to: Routes.Payment, label: 'Payment'},
  {label: 'Sign out'},
];

const isNotificationListOpen = ref(false);
const unreadNotifications = ref(1); // Số thông báo chưa đọc
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
]); // Danh sách thông báo mẫu

// Phương thức toggle
const toggleNotificationList = () => {
  isNotificationListOpen.value = !isNotificationListOpen.value;
};
</script>
<template>
  <div class="w-screen h-screen flex">
    <!-- Sidebar for larger screens -->
    <div class="w-[260px] h-full bg-gray-200 text-black overflow-hidden" v-show="showSide && !isMobile">
      <div class="h-[50px] bg-[#F0EEE5] flex justify-start items-center">
        <div class="px-[20px]">
          <h3 class="font-bold text-xl">Admin Panel</h3>
        </div>
      </div>
      <div class="h-[calc(100vh-50px)] bg-[#F0EEE5] overflow-y-auto custom-scrollbar">
        <div class="py-[10px] px-[20px]">
          <div class="space-y-2">
            <!-- My Book Group -->
            <div class="space-y-2">
              <div v-for="item in MenuItems" :key="item.label">
                <!-- Parent Item (My Book) -->
                <div class="text-sm text-gray-700 font-semibold mb-2">{{ item.label }}</div>

                <!-- Child Items (Menu Links) -->
                <div v-if="item.isParent" class="flex flex-col justify-between">
                  <router-link v-for="subItem in item.children" :key="subItem.to" :to="subItem.to"
                               class="inline-flex relative items-center py-[10px] px-[10px] w-full text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:underline"
                               active-class="bg-[#D4A27F] text-gray-800 font-semibold">
                    <font-awesome-icon :icon="subItem.icon" class="mr-2"/>
                    {{ subItem.label }}
                  </router-link>
                </div>
              </div>
            </div>
            <!-- Dropdown menu -->
            <div class="space-y-2 mt-4">
              <!-- Tiêu đề chính của dropdown -->
              <div
                  class="text-sm text-gray-700 font-semibold cursor-pointer flex justify-between items-center py-2 px-3 bg-[#C96442] rounded-md"
                  @click="toggleDropdown">
                <div class="flex items-center space-x-2">
                  <font-awesome-icon icon="fa-solid fa-bars-progress" size="lg" class="mx-2 my-[0.2rem]"/>
                  <span>Advanced Features</span>
                </div>
                <span :class="{ 'rotate-180': isDropdownOpen }"
                      class="transform transition-transform duration-300">
    ▼
  </span>
              </div>

              <div v-if="isDropdownOpen" class="mt-2 flex flex-col space-y-1">
                <router-link v-for="item in featureMenuItems" :key="item.to" :to="item.to"
                             class="inline-flex items-center py-2 px-3 text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:underline"
                             active-class="bg-[#D4A27F] text-gray-800 font-semibold" @click="handleItemClick">
                  {{ item.label }}
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Mobile overlay sidebar (remains the same) -->
    <div v-show="showMobileSide" class="fixed inset-0 z-20 bg-gray-900 bg-opacity-50">
      <div class="w-[250px] h-full bg-[#F0EEE5] text-gray-700 overflow-y-auto">
        <div class="p-4">
          <div class="flex justify-between items-center">
            <h3 class="font-bold text-xl">Admin Panel</h3>
            <button @click="toggleSideBar"
                    class="text-black transition-transform duration-200 hover:scale-125 focus:scale-150 active:animate-pulse focus:outline-none">
              <font-awesome-icon icon="fa-solid fa-xmark" size="lg"/>
            </button>
          </div>
          <div class="space-y-4 mt-3">
            <div class="space-y-2">
              <div v-for="item in MenuItems" :key="item.label">

                <div class="text-sm text-gray-700 font-semibold">{{ item.label }}</div>
                <router-link v-for="subItem in item.children" :key="subItem.to" :to="subItem.to"
                             class="inline-flex relative items-center py-2 px-3 w-full text-sm font-medium rounded-md transition duration-500 ease-in-out"
                             active-class="bg-[#D4A27F] text-gray-800 font-semibold" @click="toggleSideBar">
                  <font-awesome-icon :icon="subItem.icon" class="mr-2"/>
                  {{ subItem.label }}
                </router-link>
              </div>
            </div>
            <div class="space-y-2">
              <!-- Tiêu đề chính của dropdown -->
              <div
                  class="text-xs text-gray-700 font-semibold cursor-pointer flex justify-between items-center py-2 px-3 bg-[#C96442] rounded-md"
                  @click="toggleDropdown">
                <div class="flex items-center space-x-2">
                  <font-awesome-icon icon="fa-solid fa-bars-progress" size="lg" class="mx-2 my-[0.2rem]"/>
                  <span>Advanced Features</span>
                </div>
                <span :class="{ 'rotate-180': isDropdownOpen }" class="transform transition-transform duration-300">▼</span>
              </div>
              <!-- Menu items (dropdown content) -->
              <div v-if="isDropdownOpen" class="mt-2 flex flex-col space-y-1">
                <router-link v-for="item in featureMenuItems" :key="item.to" :to="item.to"
                             class="inline-flex items-center py-2 px-3 text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:bg-gray-100"
                             active-class="bg-gray-300 text-gray-800 font-semibold" @click="handleItemClick">
                  {{ item.label }}
                </router-link>
              </div>
            </div>
            </div>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="flex-1 flex flex-col h-screen bg-gray-400 overflow-hidden">
      <div
          class="h-[50px] top-0 sticky bg-[#F0EEE5] flex items-center shadow-sm px-[20px] w-full py-[10px] z-10 border-b justify-between">
        <!-- Toggle Sidebar Button -->
        <div
            class="cursor-pointer w-[30px] text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-custom-pulse focus:outline-none"
            @click="toggleSideBar">
          <font-awesome-icon icon="fa-solid fa-bars" size="xl"/>

        </div>

        <!-- Dropdown Menu -->
        <div class="relative flex items-center space-x-4 cursor-pointer">

          <div class="relative">
            <router-link to="/"
                         class="flex items-center text-black hover:underline transition-all duration-300 text-sm">
              <!-- Writer Icon -->
              <font-awesome-icon :icon="['fas', 'house']" size="lg"/>
            </router-link>
          </div>
          <div class="relative mr-1">
            <div @click="toggleNotificationList" class="relative">

              <font-awesome-icon icon="fa-regular fa-bell" size="xl"
                                 class="cursor-pointer w-[30px] text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-pulse focus:outline-none"/>
              <span
                  className='absolute right-[-5px] bottom-[-5px] w-4 text-center leading-4 bg-black text-white aspect-square rounded-full text-[8px]'>
                                1
                            </span>
            </div>
            <!-- Notification List -->
            <transition name="fade">
              <div v-if="isNotificationListOpen"
                   class="absolute right-0 top-full mt-2 w-80 bg-white shadow-lg rounded-lg border border-gray-200 z-20">
                <!-- Include AuthorNotification component -->
                <NotificationDropdown :notifications="notifications"/>
              </div>
            </transition>
          </div>
          <img
              class="w-10 h-10 rounded-full border-2 border-gray-50 ml-[-4px] transition-transform duration-200 hover:scale-110 hover:border-blue-500"
              src="\src\assets\logo.jpg" alt="" @click="toggleDropdownMenu"/>
          <transition name="dropdown-fade">
            <div v-show="showDropDown"
                 class="absolute top-full mt-2 right-0 z-10 w-44 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
              <div class="py-1 text-left" role="none">
                <router-link v-for="item in dropdownItems" :key="item.label" :to="item.to ? item.to : ''"
                             class="text-gray-700 block px-4 py-2 text-sm hover:bg-[#F8F8F7]" role="menuitem">
                  {{ item.label }}
                </router-link>
              </div>
            </div>
          </transition>
        </div>
      </div>
      <!-- Content Area -->
      <div class="flex-1 bg-[#F5F4EF] p-[20px] overflow-y-auto">
        <div class="h-full border-gray-300">
          <RouterView/>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Custom scrollbar for sidebar */
.custom-scrollbar {
  scrollbar-width: thin;
  scrollbar-color: rgba(0,0,0,0.2) transparent;
}

.custom-scrollbar::-webkit-scrollbar {
  width: 8px;
}

.custom-scrollbar::-webkit-scrollbar-track {
  background: transparent;
}

.custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: rgba(0,0,0,0.2);
  border-radius: 4px;
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
  background-color: rgba(0,0,0,0.3);
}
</style>
