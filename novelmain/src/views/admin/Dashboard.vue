<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';


enum Routes {
    NewNovel = '/new-novel',
    ExistingNovels = '/existing-novels',
    Analytics = '/analytics',
    Support = '/support',
    AdminNotification = '/admin-notification',
    Account = '/account',
    Payment = '/payment',
    Rules = '/rules',
    Tutorial = '/tutorial',
    BookCategory = '/book-category',
    PostCategory = '/post-category',
}

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
    isMobile.value = window.innerWidth <= 768;
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
const bookMenuItems = [
    { to: Routes.NewNovel, label: 'Add Novel', icon: 'fa-solid fa-plus' },
    { to: Routes.ExistingNovels, label: 'Existed Novel', icon: 'fa-solid fa-book' },
    { to: Routes.Analytics, label: 'Analytics', icon: 'fa-solid fa-chart-pie' },
];


const supportMenuItems = [
    { to: Routes.Support, label: 'Support', icon: 'fa-solid fa-phone' },
    { to: Routes.AdminNotification, label: 'Notification', icon: 'fa-solid fa-bullhorn' },
];

const informationMenuItems = [
    { to: Routes.Rules, label: 'Rule', icon: 'fa-solid fa-scale-balanced' },
    { to: Routes.Tutorial, label: 'Tutorial', icon: 'fa-solid fa-question' },
];
const featureMenuItems = [
    { to: Routes.BookCategory, label: 'Book Category', },
    { to: Routes.PostCategory, label: 'Post Category' }
];
const dropdownItems = [
    { to: Routes.Account, label: 'Account' },
    { to: Routes.Payment, label: 'Payment' },
    { label: 'Sign out' },
];
</script>

<template>
    <div class="w-screen h-screen flex">
        <!-- Sidebar for larger screens -->
        <div class="w-[270px] h-full bg-gray-200 text-black" v-show="showSide && !isMobile">
            <div class="h-[50px] bg-gray-100 flex justify-start items-center">
                <div class="px-[20px]">
                    <h3 class="font-bold text-xl">Admin Panel</h3>
                </div>
            </div>
            <div class="h-[calc(100vh-50px)] bg-gray-100 py-[20px]">
                <div class="flex flex-col justify-between h-full px-[20px]">
                    <!-- Management and Book Groups -->
                    <div class="space-y-2">
                        <div class="space-y-2 py-1">
                            <RouterLink to="admin-management">
                                <div
                                    class="text-md text-gray-700 font-semibold flex items-center hover:underline hover:text-gray-900">
                                    <font-awesome-icon icon="fa-solid fa-bars-progress" class="mr-2"  />Admin Management
                                </div>
                            </RouterLink>
                        </div>
                        <!-- My Book Group -->
                        <div class="space-y-2">
                            <div class="text-sm text-gray-700 font-semibold">My Book</div>
                            <div class="flex flex-col justify-between">
                                <RouterLink v-for="item in bookMenuItems" :key="item.to" :to="item.to"
                                    class="inline-flex relative items-center py-[10px] px-[10px] w-full text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:underline"
                                    active-class="bg-[#e7f5dc] text-gray-800 font-semibold">
                                    <font-awesome-icon :icon="item.icon" class="mr-2" /> {{ item.label }}
                                </RouterLink>
                            </div>
                        </div>
                        <div class="space-y-2">
                            <div class="text-sm text-gray-700 font-semibold">Notification and Support</div>
                            <div class="flex flex-col justify-between">
                                <RouterLink v-for="item in supportMenuItems" :key="item.to" :to="item.to"
                                    class="inline-flex relative items-center py-[10px] px-[10px] w-full text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:underline"
                                    active-class="bg-[#e7f5dc] text-gray-800 font-semibold">
                                    <font-awesome-icon :icon="item.icon" class="mr-2" /> {{ item.label }}
                                </RouterLink>
                            </div>
                        </div>
                        <div class="space-y-2">
                            <div class="text-sm text-gray-700 font-semibold">information</div>
                            <div class="flex flex-col justify-between">
                                <RouterLink v-for="item in informationMenuItems" :key="item.to" :to="item.to"
                                    class="inline-flex relative items-center py-[10px] px-[10px] w-full text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:underline"
                                    active-class="bg-[#e7f5dc] text-gray-800 font-semibold">
                                    <font-awesome-icon :icon="item.icon" class="mr-2" /> {{ item.label }}
                                </RouterLink>
                            </div>
                        </div>
                        <!-- Dropdown menun -->
                        <div class="space-y-2">
                            <!-- Tiêu đề chính của dropdown -->
                            <div class="text-sm text-gray-100 font-semibold cursor-pointer flex items-center justify-between py-2 px-3 bg-[#b6c99b] rounded-md"
                                @click="toggleDropdown">
                                Features
                                <span :class="{ 'rotate-180': isDropdownOpen }"
                                    class="transform transition-transform duration-300">
                                    ▼
                                </span>
                            </div>
                            <div v-if="isDropdownOpen" class="mt-2 flex flex-col space-y-1">
                                <RouterLink v-for="item in featureMenuItems" :key="item.to" :to="item.to"
                                    class="inline-flex items-center py-2 px-3 text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:underline"
                                    active-class="bg-[#e7f5dc] text-gray-800 font-semibold" @click="handleItemClick">
                                    {{ item.label }}
                                </RouterLink>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Mobile overlay sidebar -->
        <div v-show="showMobileSide" class="fixed inset-0 z-20 bg-gray-900 bg-opacity-50">
            <div class="w-[250px] h-full bg-gray-100 text-gray-700 p-4">
                <div class="flex justify-between items-center">
                    <h3 class="font-bold text-xl">Admin Panel</h3>
                    <button @click="toggleSideBar"
                        class="text-black transition-transform duration-200 hover:scale-125 focus:scale-150 active:animate-pulse focus:outline-none">
                        <font-awesome-icon icon="fa-solid fa-xmark" size="xl" />
                    </button>
                </div>
                <div class="space-y-4 mt-6">
                    <div class="space-y-2 py-1">
                        <div class="text-md text-gray-700 font-semibold flex items-center">
                            <font-awesome-icon icon="fa-solid fa-bars-progress" class="mr-2" />
                            Admin Management
                        </div>
                    </div>
                    <!-- My Book Links -->
                    <div class="space-y-2">
                        <div class="text-sm text-gray-700 font-semibold">My Book</div>
                        <RouterLink v-for="item in bookMenuItems" :key="item.to" :to="item.to"
                            class="inline-flex relative items-center py-2 px-3 w-full text-sm font-medium rounded-md transition duration-500 ease-in-out"
                            active-class="bg-[#e7f5dc] text-gray-800 font-semibold" @click="toggleSideBar">
                            <font-awesome-icon :icon="item.icon" class="mr-2" />
                            {{ item.label }}
                        </RouterLink>
                    </div>
                    <div class="space-y-2">
                        <div class="text-sm text-gray-700 font-semibold">Notification and Support</div>
                        <RouterLink v-for="item in supportMenuItems" :key="item.to" :to="item.to"
                            class="inline-flex relative items-center py-2 px-3 w-full text-sm font-medium rounded-md transition duration-500 ease-in-out"
                            active-class="bg-[#e7f5dc] text-gray-800 font-semibold" @click="toggleSideBar">
                            <font-awesome-icon :icon="item.icon" class="mr-2" />
                            {{ item.label }}
                        </RouterLink>
                    </div>
                    <div class="space-y-2">
                        <div class="text-sm text-gray-700 font-semibold">Information</div>
                        <RouterLink v-for="item in informationMenuItems" :key="item.to" :to="item.to"
                            class="inline-flex relative items-center py-2 px-3 w-full text-sm font-medium rounded-md transition duration-500 ease-in-out"
                            active-class="bg-[#e7f5dc] text-gray-800 font-semibold" @click="toggleSideBar">
                            <font-awesome-icon :icon="item.icon" class="mr-2" />
                            {{ item.label }}
                        </RouterLink>
                    </div>
                    <div class="space-y-2">
                        <!-- Tiêu đề chính của dropdown -->
                        <div class="text-xs text-gray-700 font-semibold cursor-pointer flex items-center justify-between py-2 px-3 bg-[#b6c99b] rounded-md"
                            @click="toggleDropdown">
                            Features
                            <span :class="{ 'rotate-180': isDropdownOpen }"
                                class="transform transition-transform duration-300">
                                ▼
                            </span>
                        </div>

                        <!-- Menu items (dropdown content) -->
                        <div v-if="isDropdownOpen" class="mt-2 flex flex-col space-y-1">
                            <RouterLink v-for="item in featureMenuItems" :key="item.to" :to="item.to"
                                class="inline-flex items-center py-2 px-3 text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out hover:bg-gray-100"
                                active-class="bg-gray-300 text-gray-800 font-semibold" @click="handleItemClick">
                                {{ item.label }}
                            </RouterLink>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Main Content -->
        <div class="flex-1 h-full bg-gray-400 relative content-area">
            <div
                class="h-[50px] navbar bg-gray-100 flex items-center shadow-sm px-[20px] w-full py-[10px] z-10 border-b justify-between">
                <!-- Toggle Sidebar Button -->
                <div class="cursor-pointer w-[30px] text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-custom-pulse focus:outline-none"
                    @click="toggleSideBar">
                    <font-awesome-icon icon="fa-solid fa-bars" size="xl" />

                </div>

                <!-- Dropdown Menu -->
                <div class="relative flex items-center space-x-4 cursor-pointer">
                    <div class="relative">
            <RouterLink to="/"
              class="flex items-center text-black hover:underline transition-all duration-300 text-sm">
              <!-- Writer Icon -->
              <font-awesome-icon :icon="['fas', 'house']" size="lg" />
            </RouterLink>
          </div>
                    <div class="relative mr-2">
                        <font-awesome-icon icon="fa-regular fa-bell" size="xl"  class="cursor-pointer w-[30px] text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-pulse focus:outline-none" />
                        <span
                            className='absolute right-[-5px] bottom-[-5px] w-4 text-center leading-4 bg-black text-white aspect-square rounded-full text-[8px]'>
                            1
                        </span>
                    </div>
                    <img class="w-10 h-10 rounded-full border-2 border-gray-50 ml-[-4px] transition-transform duration-200 hover:scale-110 hover:border-blue-500"
                        src="\src\assets\logo.jpg" alt="" @click="toggleDropdownMenu" />
                    <transition name="dropdown-fade">
                        <div v-show="showDropDown"
                            class="absolute top-full mt-2 right-0 z-10 w-44 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                            <div class="py-1 text-left" role="none">
                                <RouterLink v-for="item in dropdownItems" :key="item.label" :to="item.to ? item.to : ''"
                                    class="text-gray-700 block px-4 py-2 text-sm hover:bg-[#e7f5dc]" role="menuitem">
                                    {{ item.label }}
                                </RouterLink>
                            </div>
                        </div>
                    </transition>
                </div>
            </div>
            <!-- Content Area -->
            <div class="table-container h-full bg-gray-50 p-[20px]">
                <div class="border border-gray-300 rounded-md p-[20px] h-full">
                    <RouterView />
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* Animation for dropdown */
.dropdown-fade-enter-active,
.dropdown-fade-leave-active {
    transition: opacity 0.3s ease, transform 0.3s ease;
}

.dropdown-fade-enter,
.dropdown-fade-leave-to

/* .dropdown-fade-leave-active in <2.1.8 */
    {
    opacity: 0;
    transform: translateY(10px);
}

/* Cố định navbar */
.navbar {
    position: sticky;
    top: 0;
    z-index: 10;
    /* Đảm bảo navbar luôn nằm trên các phần tử khác */
    background-color: #f1f1f1;
    /* Màu nền của navbar */
}

/* Cung cấp không gian cho nội dung */
.content-area {
    height: calc(100vh - 50px);
    /* Đảm bảo nội dung chiếm hết chiều cao còn lại */
    overflow-y: auto;
    /* Cho phép cuộn dọc khi nội dung vượt quá kích thước */
}

/* Thiết lập các kiểu cho bảng */
.table-container {
    width: 100%;
    overflow-x: auto;
    /* Cho phép cuộn ngang nếu bảng quá rộng */
}
</style>
