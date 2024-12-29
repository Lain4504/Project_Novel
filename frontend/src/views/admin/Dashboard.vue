<script lang="ts" setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { BellOutlined, CloseOutlined, EyeOutlined, LogoutOutlined, MenuOutlined, SettingOutlined } from '@ant-design/icons-vue';
import { notification } from 'ant-design-vue';
import { getUserProfile } from '@/api/user';
import { getNotificationByUserId } from '@/api/notification';
import { logout } from '@/api/auth';
import { useStore } from 'vuex';

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

interface UserProfile {
  id: string;
  image: string;
  username: string;
}

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

const router = useRouter();
const store = useStore();
const showSide = ref(false);
const collapsed = ref(false);
const isMobile = ref(false);
const notifications = ref<Notification[]>([]);
const unreadNotifications = ref(0);
const userProfile = ref<UserProfile>({
  id: '',
  image: '',
  username: '',
});

const isAdmin = computed(() => {
  return store.getters.getUserRole.includes('ADMIN');
});

const menuItems = computed(() => {
  const items = [
    {
      key: 'my-book',
      label: 'My Book',
      children: [
        { key: Routes.NewNovel, label: 'Add Novel' },
        { key: Routes.ExistingNovels, label: 'Existed Novel' },
        { key: Routes.Analytics, label: 'Analytics' },
      ],
    },
    {
      key: 'notification',
      label: 'Notification',
      children: [
        { key: Routes.AdminNotification, label: 'Notification' },
      ],
    },
    {
      key: 'information',
      label: 'Information',
      children: [
        { key: Routes.FAQ, label: 'FAQ' },
        { key: Routes.BookCategory, label: 'Book Category' },
        { key: Routes.PostCategory, label: 'Post Category' },
      ],
    },
  ];

  if (isAdmin.value) {
    items.push({
      key: 'admin',
      label: 'Admin',
      children: [
        { key: Routes.BookManagement, label: 'Book Management' },
        { key: Routes.PostManagement, label: 'Post Management' },
        { key: Routes.BookCategoryManagement, label: 'Book Category Management' },
        { key: Routes.PostCategoryManagement, label: 'Post Category Management' },
        { key: Routes.AdsManagement, label: 'Ads Management' },
        { key: Routes.UserManagement, label: 'User Management' },
        { key: Routes.AdminDashboard, label: 'Admin Dashboard' },
      ],
    });
  }

  return items;
});

const handleMenuClick = ({ key }: { key: string }) => {
  router.push(key);
  if (isMobile.value) {
    showSide.value = false;
  }
};

const toggleSidebar = () => {
  showSide.value = !showSide.value;
};

const handleResize = () => {
  isMobile.value = window.innerWidth <= 1280;
  if (!isMobile.value) {
    showSide.value = false;
  }
};

const fetchNotifications = async () => {
  try {
    const userId = store.getters.getUserId;
    const response = await getNotificationByUserId(userId, 1, 5);
    notifications.value = response.data;
    unreadNotifications.value = response.data.filter((n: Notification) => !n.read).length;
  } catch (error) {
    notification.error({
      message: 'Error',
      description: 'Failed to fetch notifications',
    });
  }
};

const fetchUserProfile = async () => {
  try {
    const response = await getUserProfile(store.getters.getUserId);
    userProfile.value = {
      ...response,
      image: response.image?.path || '',
    };
  } catch (error) {
    notification.error({
      message: 'Error',
      description: 'Failed to fetch user profile',
    });
  }
};

const handleLogout = async () => {
  try {
    const accessToken = store.getters.getToken;
    const refreshToken = store.getters.getRefreshToken;

    if (!accessToken || !refreshToken) {
      throw new Error('Missing tokens');
    }

    await logout({ refreshToken, accessToken });
    store.commit('clearUser');
    router.push('/');
  } catch (error) {
    notification.error({
      message: 'Error',
      description: 'Logout failed',
    });
  }
};

const drawerWidth = computed(() => (isMobile.value ? '100%' : 256));

const truncateContent = (content: string) => {
  return content.length > 70 ? content.slice(0, 70) + '...' : content;
};

onMounted(() => {
  handleResize();
  fetchUserProfile();
  fetchNotifications();
  window.addEventListener('resize', handleResize);
});

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize);
});
</script>
<template>
  <a-layout class="h-screen">
    <a-drawer
        v-if="isMobile"
        v-model:visible="showSide"
        :closable="true"
        placement="left"
        :width="drawerWidth"
        :class="{ 'fixed inset-0 z-50 w-full': isMobile }"
    >
      <template #close-icon>
        <CloseOutlined style="font-size: 24px"/>
      </template>
      <div class="h-[30px] flex items-center px-4 border-b border-gray-300 bg-white">
        <h1 class="font-bold text-xl text-[#18A058]">HawkNovel</h1>
      </div>
      <a-menu theme="light" mode="inline" :items="menuItems" @click="handleMenuClick"/>
    </a-drawer>
    <a-layout-sider
        v-else
        v-model:collapsed="collapsed"
        :width="256"
        :collapsed-width="80"
        style="background: #FFFFFF"
    >
        <h1 class="font-bold text-xl text-[#18A058] my-4 ml-4">HawkNovel</h1>
      <a-menu theme="light" mode="inline" :items="menuItems" @click="handleMenuClick"/>
    </a-layout-sider>
    <a-layout>
      <a-layout-header class="flex justify-between items-center shadow-sm px-4" style="background: #FFFFFF">
        <MenuOutlined class="cursor-pointer " style="font-size: 20px" @click="toggleSidebar"/>
        <div class="flex items-center space-x-4">
          <a-dropdown placement="bottomRight" :trigger="['click']">
            <template #overlay>
              <a-menu>
                <a-menu-item key="header" disabled>
                  <span class="font-medium text-lg text-gray-700 text-center block">Thông báo của bạn</span>
                </a-menu-item>
                <a-menu-divider class="border-gray-200"/>
                <a-menu-item v-for="notification in notifications" :key="notification.id" class="hover:bg-gray-100">
                  <router-link to="/" class="flex items-center space-x-3 py-2">
                    <a-avatar :src="notification.image"/>
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
                      <EyeOutlined class="mr-2"/>
                      Xem tất cả
                    </a-button>
                  </router-link>
                </a-menu-item>
              </a-menu>
            </template>
            <a-badge :count="unreadNotifications">
              <button>
                <BellOutlined class="text-xl" style="font-size: 24px"/>
              </button>
            </a-badge>
          </a-dropdown>
          <a-dropdown placement="bottomRight" :trigger="['click']">
            <a-avatar size="large" :src="userProfile.image || ''" alt=""/>
            <template #overlay>
              <a-menu>
                <a-menu-item key="logout" @click="handleLogout">
                  <SettingOutlined/>
                  Cài đặt tài khoản
                </a-menu-item>
                <a-menu-item key="logout" @click="handleLogout">
                  <LogoutOutlined/>
                  Đăng xuất
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </a-layout-header>
      <a-layout-content class="p-4">
        <router-view/>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>