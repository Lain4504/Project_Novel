<script lang="ts" setup>
import {computed, onMounted, ref, watchEffect} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import {logout} from '../../api/auth';
import {getNotificationByUserId} from '../../api/notification';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import store from "../../store";

interface Notification {
  id: number;
  user: string;
  message: string;
  time: string;
  iconColor: string;
  createdDate: string;
  title: string;
  content: string;
  image: string;
}

const truncateContent = (content: string) => {
  return content.length > 70 ? content.slice(0, 70) + '...' : content;
};

const notifications = ref<Notification[]>([]);
const unreadNotifications = ref(0);
const isMobileMenuOpen = ref(false);

const isAuthenticated = computed(() => store.getters.isAuthenticated);
const router = useRouter();
const route = useRoute();
const userImage = store.getters.getUserImage;

const fetchNotifications = async () => {
  try {
    const userId = store.getters.getUserId;
    const notificationData = await getNotificationByUserId(userId, 1, 5);
    notifications.value = notificationData.data;
    unreadNotifications.value = notificationData.data.length;
  } catch (error) {
    console.error('Error fetching notifications:', error);
  }
};

onMounted(() => {
  if (isAuthenticated.value) {
    fetchNotifications();
  }
});

watchEffect(() => {
  const handleResize = () => {
    isMobileMenuOpen.value = window.innerWidth < 768 ? isMobileMenuOpen.value : false;
  };

  window.addEventListener('resize', handleResize);

  return () => {
    window.removeEventListener('resize', handleResize);
  };
});

const handleLogout = async () => {
  try {
    const {getToken, getRefreshToken} = store.getters;
    const accessToken = getToken;
    const refreshToken = getRefreshToken;
    if (!accessToken || !refreshToken) {
      console.error('Tokens are missing');
      return;
    }
    await logout({refreshToken, accessToken});
    store.commit('clearUser');
  } catch (error) {
    console.error('Logout failed:', error);
  }
};

const menuItems = [
  {key: 'forum', label: 'Thảo luận', path: '/post-forum', icon: 'fa-regular fa-comments'},
  {key: 'categories', label: 'Thể loại', path: '/categories', icon: 'fa-brands fa-app-store'},
  {key: 'search', label: 'Tìm kiếm', path: '/search', icon: 'fa-solid fa-magnifying-glass'},
  {key: 'ranking', label: 'Xếp hạng', path: '/ranking', icon: 'fa-solid fa-trophy'},
];

const dropdownMenu = [
  {key: '1', label: 'Tài khoản', icon: 'fa-solid fa-user', action: () => router.push({name: 'account', params: {id: store.getters.getUserId}})},
  {key: '2', label: 'Bookmark', icon: 'fa-solid fa-bookmark', action: () => router.push('/list/bookmark')},
  {key: '3', label: 'Thư viện', icon: 'fa-solid fa-book', action: () => router.push('/library')},
  {key: '4', label: 'Cài đặt cá nhân', icon: 'fa-solid fa-gear', action: () => router.push('/user-profile')},
  {key: '5', label: 'Đăng xuất', icon: 'fa-solid fa-right-from-bracket', action: handleLogout},
];

const handleMenuItemClick = (action: Function) => {
  action();
  isMobileMenuOpen.value = false;
};
</script>

<template>
  <a-layout-header class="sticky top-0 bg-white border-b z-50 border-gray-200 p-0 h-auto">
    <div class="max-w-9xl mx-auto px-4 sm:px-6 h-16 flex items-center justify-between">
      <div class="flex items-center justify-between">
        <button shape="circle" class="md:hidden flex items-center mr-2" @click="isMobileMenuOpen = true">
          <font-awesome-icon :icon="['fas', 'bars']" style="font-size: 20px"/>
        </button>
        <router-link to="/" class="flex items-center">
          <h1 class="text-xl mb-0 text-[#18A058] flex items-center">HawkNovel</h1>
        </router-link>
      </div>
      <a-menu mode="horizontal" class="hidden md:flex flex-1 justify-center border-none bg-transparent"
              :selectedKeys="[route.path]">
        <a-menu-item v-for="item in menuItems" :key="item.key">
          <router-link :to="item.path">
            <span><font-awesome-icon :icon="item.icon"/></span>
            {{ item.label }}
          </router-link>
        </a-menu-item>
      </a-menu>
      <div class="flex items-center space-x-4">
        <template v-if="isAuthenticated">
          <button
              class="flex items-center space-x-2 border border-[#18A058] p-2 rounded-md bg-white hover:bg-[#E7F5EE] hover:text-gray-700 transition duration-300 ease-in-out"
              @click="router.push('/novels-of-author')">
            <FontAwesomeIcon :icon="['far', 'pen-to-square']"/>
            <span class="hidden sm:inline text-sm">Đăng tiểu thuyết</span>
          </button>
          <a-dropdown placement="bottomRight" :trigger="['click']">
            <template #overlay>
              <a-menu>
                <a-menu-item key="header" disabled>
                  <span class="font-medium text-lg text-gray-700 text-center block">Thông báo của bạn</span>
                </a-menu-item>
                <a-menu-divider class="border-gray-200"/>
                <a-menu-item v-for="notification in notifications" :key="notification.id" class="hover:bg-gray-100">
                  <router-link to="/" class="flex items-center space-x-3 py-2">
                    <a-avatar :src="notification.image" loading="lazy"/>
                    <div>
                      <div class="text-gray-800 font-medium text-sm">{{ truncateContent(notification.content) }}</div>
                      <div class="text-xs text-gray-500">{{ notification.createdDate }}</div>
                    </div>
                  </router-link>
                </a-menu-item>
                <a-menu-divider class="border-gray-200"/>
                <a-menu-item key="footer">
                  <router-link to="/notification-list"
                               class="flex items-center text-[#18A058] justify-center font-medium hover:underline">
                    <a-button type="link" class="flex items-center">
                      <font-awesome-icon :icon="['far', 'eye']" class="text-md mr-2"/>
                      Xem tất cả
                    </a-button>
                  </router-link>
                </a-menu-item>
              </a-menu>
            </template>
            <a-badge :count="unreadNotifications">
              <button>
                <font-awesome-icon :icon="['far', 'bell']" class="text-xl"/>
              </button>
            </a-badge>
          </a-dropdown>
          <a-dropdown placement="bottomRight" :trigger="['click']">
            <template #overlay>
              <a-menu>
                <a-menu-item v-for="item in dropdownMenu" :key="item.key" class="no-underline"
                             @click="handleMenuItemClick(item.action)">
                  <span class="mr-2"><font-awesome-icon :icon="item.icon"/></span>
                  {{ item.label }}
                </a-menu-item>
              </a-menu>
            </template>
            <button>
            <a-avatar :src="userImage" alt="userImage" size="large" loading="lazy"/>
            </button>
          </a-dropdown>
        </template>
        <template v-else>
          <button class="text-sm text-[#18A058]" @click="router.push('/login')">Đăng nhập</button>
          <button class="text-sm text-[#18A058]" @click="router.push('/register')">Tạo tài khoản</button>
        </template>
      </div>
    </div>
    <a-drawer placement="left" :visible="isMobileMenuOpen" @close="isMobileMenuOpen = false" :width="250">
      <a-menu mode="inline" :selectedKeys="[route.path]">
        <a-menu-item v-for="item in menuItems" :key="item.key">
          <router-link :to="item.path" @click="isMobileMenuOpen = false">
            <span><font-awesome-icon :icon="item.icon"/></span>
            {{ item.label }}
          </router-link>
        </a-menu-item>
      </a-menu>
    </a-drawer>
  </a-layout-header>
</template>

<style scoped>
/* Add any necessary styles here */
</style>