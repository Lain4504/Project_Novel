<script lang="ts" setup>
import {computed, onMounted, ref, watchEffect} from 'vue';
import {useStore} from 'vuex';
import {useRoute, useRouter} from 'vue-router';
import {logout} from '@/api/auth';
import {getNotificationByUserId} from '@/api/notification';
import {
  AppstoreOutlined,
  BellOutlined,
  BookOutlined,
  EditOutlined,
  EyeOutlined,
  LogoutOutlined,
  MenuOutlined,
  MessageOutlined,
  ReadOutlined,
  SearchOutlined,
  SettingOutlined,
  TrophyOutlined,
  UserOutlined
} from '@ant-design/icons-vue';

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

const store = useStore();
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
  {key: 'forum', label: 'Thảo luận', path: '/post-forum', icon: MessageOutlined},
  {key: 'categories', label: 'Thể loại', path: '/categories', icon: AppstoreOutlined},
  {key: 'search', label: 'Tìm kiếm', path: '/search', icon: SearchOutlined},
  {key: 'ranking', label: 'Xếp hạng', path: '/ranking', icon: TrophyOutlined},
];

const dropdownMenu = [
  {
    key: '1',
    label: 'Tài khoản',
    icon: UserOutlined,
    action: () => router.push({name: 'account', params: {id: store.getters.getUserId}})
  },
  {key: '2', label: 'Bookmark', icon: BookOutlined, action: () => router.push('/list/bookmark')},
  {key: '3', label: 'Thư viện', icon: ReadOutlined, action: () => router.push('/library')},
  {key: '4', label: 'Cài đặt cá nhân', icon: SettingOutlined, action: () => router.push('/user-profile')},
  {key: '5', label: 'Đăng xuất', icon: LogoutOutlined, action: handleLogout},
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
        <a-button type="link" shape="circle" class="md:hidden flex items-center" @click="isMobileMenuOpen = true">
          <MenuOutlined/>
        </a-button>
        <router-link to="/" class="flex items-center">
          <h1 class="text-xl mb-0 text-[#18A058] flex items-center">HawkNovel</h1>
        </router-link>
      </div>
      <a-menu mode="horizontal" class="hidden md:flex flex-1 justify-center border-none bg-transparent"
              :selectedKeys="[route.path]">
        <a-menu-item v-for="item in menuItems" :key="item.key">
          <router-link :to="item.path">
            <span><component :is="item.icon"/></span>
            {{ item.label }}
          </router-link>
        </a-menu-item>
      </a-menu>
      <div class="flex items-center space-x-4">
        <template v-if="isAuthenticated">
          <button
              class="flex items-center space-x-2 border border-[#18A058] p-2 rounded-md bg-white hover:bg-[#E7F5EE] hover:text-gray-700 transition duration-300 ease-in-out"
              @click="router.push('/novels-of-author')">
            <EditOutlined style="font-size: 20px"/>
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
            <template #overlay>
              <a-menu>
                <a-menu-item v-for="item in dropdownMenu" :key="item.key" class="no-underline"
                             @click="handleMenuItemClick(item.action)">
                  <span class="mr-2"><component :is="item.icon"/></span>
                  {{ item.label }}
                </a-menu-item>
              </a-menu>
            </template>
            <button>
            <a-avatar :src="userImage" alt="userImage" size="large"/>
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
            <span><component :is="item.icon"/></span>
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