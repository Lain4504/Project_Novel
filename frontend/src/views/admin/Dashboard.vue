<script lang="ts" setup>
import {onBeforeUnmount, onMounted, ref} from 'vue';
import NotificationDropdown from '@/components/common/BellNotificationDropdown.vue';
import {getUserProfile} from "@/api/user";
import store from "@/store";
import {getNotificationByUserId} from "@/api/notification";
import {logout} from "@/api/auth";
import router from "@/router";

enum Routes {
  NewNovel = '/create-novel',
  ExistingNovels = '/novels-of-author',
  Analytics = '/analytics',
  AdminNotification = '/system-notification',
  FAQ = '/faq',
  PostCategory = '/post-category-for-author',
  BookCategory = '/novel-category-for-author',
  BookManagement = '/novel-list',
  PostManagement = '/post-list',
  BookCategoryManagement = '/novel-category-list',
  PostCategoryManagement = '/post-category-list',
  AdsManagement = '/ads-admin',
  UserManagement = '/user-admin',
  AdminDashboard = '/admin-dashboard',
}

const showDropDown = ref<boolean>(false);
const showSide = ref<boolean>(true);
const isMobile = ref<boolean>(false);
const showMobileSide = ref<boolean>(false);
const isDropdownOpen = ref<boolean>(false);
const notifications = ref([]);
const unreadNotifications = ref(0);
const fetchNotifications = async () => {
  try {
    const userId = store.getters.getUserId;
    const page = 1;
    const size = 5;
    const notificationData = await getNotificationByUserId(userId, page, size);
    notifications.value = notificationData.data;
    unreadNotifications.value = notificationData.data.length;
  } catch (error) {
    console.error('Error fetching notifications:', error);
  }
};
onMounted(() => {
  fetchNotifications();
});

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
    label: 'Notification',
    isParent: true,
    children: [
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
    router.push('/');
  } catch (error) {
    console.error('Logout failed:', error);
    alert('Logout failed. Please try again.');
  }
};
const dropdownItems = [
  {label: 'Sign out', action: handleLogout}, // Thêm mục Sign out
];
const isNotificationListOpen = ref(false);

// Phương thức toggle
const toggleNotificationList = () => {
  isNotificationListOpen.value = !isNotificationListOpen.value;
};
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
</script>
<template>
  <div class="w-screen h-screen flex">
    <!-- Sidebar for larger screens -->
    <div v-show="showSide && !isMobile"
         class="w-[260px] h-full bg-white text-black border-r border-gray-300 overflow-hidden">
      <div class="h-[50px] bg-white flex justify-start items-center border-b border-gray-300">
        <div class="px-[20px]">
          <h3 class="font-bold text-xl">Admin Panel</h3>
        </div>
      </div>
      <div class="h-[calc(100vh-50px)] bg-white overflow-y-auto custom-scrollbar">
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
                               active-class="bg-gray-300 text-black font-semibold"
                               class="inline-flex relative items-center py-[10px] px-[10px] w-full text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:bg-gray-100 hover:text-black">
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
                  class="text-sm text-gray-700 font-semibold cursor-pointer flex justify-between items-center py-2 px-3 bg-gray-200 rounded-md"
                  @click="toggleDropdown">
                <div class="flex items-center space-x-2">
                  <font-awesome-icon class="mx-2 my-[0.2rem]" icon="fa-solid fa-bars-progress" size="lg"/>
                  <span>Advanced Features</span>
                </div>
                <span :class="{ 'rotate-180': isDropdownOpen }"
                      class="transform transition-transform duration-300">
    ▼
  </span>
              </div>

              <div v-if="isDropdownOpen" class="mt-2 flex flex-col space-y-1">
                <router-link v-for="item in featureMenuItems" :key="item.to" :to="item.to"
                             active-class="bg-gray-300 text-black font-semibold"
                             class="inline-flex items-center py-2 px-3 text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:bg-gray-100 hover:text-black"
                             @click="handleItemClick">
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
      <div class="w-[250px] h-full bg-white text-gray-700 overflow-y-auto">
        <div class="p-4">
          <div class="flex justify-between items-center">
            <h3 class="font-bold text-xl">Admin Panel</h3>
            <button
                class="text-black transition-transform duration-200 hover:scale-125 focus:scale-150 active:animate-pulse focus:outline-none"
                @click="toggleSideBar">
              <font-awesome-icon icon="fa-solid fa-xmark" size="lg"/>
            </button>
          </div>
          <div class="space-y-4 mt-3">
            <div class="space-y-2">
              <div v-for="item in MenuItems" :key="item.label">

                <div class="text-sm text-gray-700 font-semibold">{{ item.label }}</div>
                <router-link v-for="subItem in item.children" :key="subItem.to" :to="subItem.to"
                             active-class="bg-gray-300 text-black font-semibold"
                             class="inline-flex relative items-center py-2 px-3 w-full text-sm font-medium rounded-md transition duration-500 ease-in-out hover:bg-gray-100 hover:text-black"
                             @click="toggleSideBar">
                  <font-awesome-icon :icon="subItem.icon" class="mr-2"/>
                  {{ subItem.label }}
                </router-link>
              </div>
            </div>
            <div class="space-y-2">
              <!-- Tiêu đề chính của dropdown -->
              <div
                  class="text-xs text-gray-700 font-semibold cursor-pointer flex justify-between items-center py-2 px-3 bg-gray-200 rounded-md"
                  @click="toggleDropdown">
                <div class="flex items-center space-x-2">
                  <font-awesome-icon class="mx-2 my-[0.2rem]" icon="fa-solid fa-bars-progress" size="lg"/>
                  <span>Advanced Features</span>
                </div>
                <span :class="{ 'rotate-180': isDropdownOpen }"
                      class="transform transition-transform duration-300">▼</span>
              </div>
              <!-- Menu items (dropdown content) -->
              <div v-if="isDropdownOpen" class="mt-2 flex flex-col space-y-1">
                <router-link v-for="item in featureMenuItems" :key="item.to" :to="item.to"
                             active-class="bg-gray-300 text-black font-semibold"
                             class="inline-flex items-center py-2 px-3 text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:bg-gray-100 hover:text-black"
                             @click="handleItemClick">
                  {{ item.label }}
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="flex-1 flex flex-col h-screen bg-white overflow-hidden">
      <div
          class="h-[50px] top-0 sticky bg-white flex items-center shadow-sm px-[20px] w-full py-[10px] z-10 border-b border-gray-300 justify-between">
        <!-- Toggle Sidebar Button -->
        <div
            class="cursor-pointer w-[30px] text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-custom-pulse focus:outline-none"
            @click="toggleSideBar">
          <font-awesome-icon icon="fa-solid fa-bars" size="xl"/>

        </div>

        <!-- Dropdown Menu -->
        <div class="relative flex items-center space-x-4 cursor-pointer">

          <div class="relative">
            <router-link class="flex items-center text-black hover:underline transition-all duration-300 text-sm"
                         to="/">
              <!-- Writer Icon -->
              <font-awesome-icon :icon="['fas', 'house']" size="lg"/>
            </router-link>
          </div>
          <div class="relative mr-1">
            <div class="relative" @click="toggleNotificationList">

              <font-awesome-icon
                  class="cursor-pointer w-[30px] text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-pulse focus:outline-none"
                  icon="fa-regular fa-bell"
                  size="xl"/>
              <span
                  className='absolute right-[-5px] bottom-[-5px] w-4 text-center leading-4 bg-black text-white aspect-square rounded-full text-[8px]'>
                                {{ unreadNotifications }}
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
              :src="userProfile.image"
              alt=""
              class="w-10 h-10 rounded-full border-2 border-gray-50 ml-[-4px] transition-transform duration-200 hover:scale-110 hover:border-blue-500"
              @click="toggleDropdownMenu"/>
          <transition name="dropdown-fade">
            <div v-show="showDropDown"
                 class="absolute top-full mt-2 right-0 z-10 w-44 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
              <div class="py-1 text-left" role="none">
                <router-link v-for="item in dropdownItems" :key="item.label" :to="item.to ? item.to : ''"
                             class="text-gray-700 block px-4 py-2 text-sm hover:bg-gray-100" role="menuitem"
                             @click="item.action ? item.action() : null">
                  {{ item.label }}
                </router-link>
              </div>
            </div>
          </transition>
        </div>
      </div>
      <!-- Content Area -->
      <div class="flex-1 bg-white p-[20px] overflow-y-auto">
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
  scrollbar-color: rgba(0, 0, 0, 0.2) transparent;
}

.custom-scrollbar::-webkit-scrollbar {
  width: 8px;
}

.custom-scrollbar::-webkit-scrollbar-track {
  background: transparent;
}

.custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.2);
  border-radius: 4px;
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
  background-color: rgba(0, 0, 0, 0.3);
}
</style>
