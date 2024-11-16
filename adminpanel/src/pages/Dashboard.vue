<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import MenuOutlined from '@ant-design/icons-vue/MenuOutlined';
import BellOutlined from '@ant-design/icons-vue/BellOutlined';
import CloseOutlined from '@ant-design/icons-vue/CloseOutlined';
const showDropDown = ref(false);
const showSide = ref(true);
const isMobile = ref(false);
const showMobileSide = ref(false);

// Toggle main sidebar
const toggleSideBar = () => {
    if (isMobile.value) {
        showMobileSide.value = !showMobileSide.value;
    } else {
        showSide.value = !showSide.value;
    }
    // Close the dropdown when toggling sidebar
    showDropDown.value = false;
}

// Toggle dropdown menu
const toggleDropdownMenu = () => {
    showDropDown.value = !showDropDown.value;
}

const handleResize = () => {
    isMobile.value = window.innerWidth <= 768;
    if (isMobile.value) {
        showSide.value = false; // Auto-close main sidebar on mobile
    } else {
        showMobileSide.value = false; // Auto-close mobile sidebar on larger screens
    }
}

onMounted(() => {
    handleResize();
    window.addEventListener('resize', handleResize);
});

onBeforeUnmount(() => {
    window.removeEventListener('resize', handleResize);
});

const menuItems = [
    { to: '/analysis', label: 'Dashboard' },
    { to: '/user-management', label: 'User Management' },
];

const booksMenuItems = [
    { to: '/new-novel', label: 'Add Novel' },
    { to: '/existing-novels', label: 'Existed Novel' },
];

const dropdownItems = [
    { label: 'Account', href: '#' },
    { label: 'Payment', href: '#' },
    { label: 'Support', href: '#' },
    { label: 'Sign out', href: '#' },
];
</script>
<template>
    <div class="w-screen h-screen flex">
        <!-- Sidebar for larger screens -->
        <div class="w-[270px] h-full bg-gray-200 text-white" v-show="showSide && !isMobile">
            <div class="h-[50px] bg-gray-900 flex justify-start items-center">
                <div class="px-[20px]">
                    <h3 class="font-bold text-xl">Admin Panel</h3>
                </div>
            </div>
            <div class="h-[calc(100vh-50px)] bg-gray-800 py-[20px]">
                <div class="flex flex-col justify-between h-full px-[20px]">
                    <!-- Management and Book Groups -->
                    <div class="space-y-2">
                        <!-- Management Group -->
                        <div class="space-y-2">
                            <div class="text-xs text-gray-400 font-semibold">Management</div>
                            <div class="flex flex-col justify-between">
                                <RouterLink v-for="item in menuItems" :key="item.to" :to="item.to"
                                    class="inline-flex relative items-center py-[10px] px-[10px] w-full text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out"
                                    active-class="bg-gray-300 text-gray-800 font-semibold">
                                    {{ item.label }}
                                </RouterLink>
                            </div>
                        </div>

                        <!-- My Book Group -->
                        <div class="space-y-2">
                            <div class="text-xs text-gray-400 font-semibold">My Book</div>
                            <div class="flex flex-col justify-between">
                                <RouterLink v-for="item in booksMenuItems" :key="item.to" :to="item.to"
                                    class="inline-flex relative items-center py-[10px] px-[10px] w-full text-sm font-medium rounded-md border-gray-200 transition duration-500 ease-in-out"
                                    active-class="bg-gray-300 text-gray-800 font-semibold">
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
            <div class="w-[250px] h-full bg-gray-800 text-white p-4">
                <div class="flex justify-between items-center">
                    <h3 class="font-bold text-xl">Admin Panel</h3>
                    <button @click="toggleSideBar"
                        class="text-white transition-transform duration-200 hover:scale-125 focus:scale-150 active:animate-pulse focus:outline-none">
                        <CloseOutlined style="font-size: 18px;"/>
                    </button>
                </div>
                <div class="space-y-4 mt-6">
                    <!-- Management Links -->
                    <div class="space-y-2">
                        <div class="text-xs text-gray-400 font-semibold">Management</div>
                        <RouterLink v-for="item in menuItems" :key="item.to" :to="item.to"
                            class="inline-flex relative items-center py-2 px-3 w-full text-sm font-medium rounded-md transition duration-500 ease-in-out"
                            active-class="bg-gray-700 text-gray-300 font-semibold" @click="toggleSideBar">
                            {{ item.label }}
                        </RouterLink>
                    </div>

                    <!-- My Book Links -->
                    <div class="space-y-2">
                        <div class="text-xs text-gray-400 font-semibold">My Book</div>
                        <RouterLink v-for="item in booksMenuItems" :key="item.to" :to="item.to"
                            class="inline-flex relative items-center py-2 px-3 w-full text-sm font-medium rounded-md transition duration-500 ease-in-out"
                            active-class="bg-gray-700 text-gray-300 font-semibold" @click="toggleSideBar">
                            {{ item.label }}
                        </RouterLink>
                    </div>
                </div>
            </div>
        </div>
        <!-- Main Content -->
        <div class="flex-1 h-full bg-gray-400 relative content-area">
            <div class="h-[50px] navbar bg-gray-100 flex items-center shadow-sm px-[20px] w-full py-[10px] z-10 border-b justify-between">
                <!-- Toggle Sidebar Button -->
                <div class="cursor-pointer w-[30px] text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-custom-pulse focus:outline-none"
                    @click="toggleSideBar">
                    <MenuOutlined style="font-size: 24px;" />
                </div>

                <!-- Dropdown Menu -->
                <div class="relative flex items-center space-x-4 cursor-pointer">
                    <div class="relative mr-4">
                        <BellOutlined style="font-size: 24px;" class="cursor-pointer w-[30px] text-gray-700 hover:text-black transition-transform duration-200 hover:scale-110 focus:scale-125 active:animate-pulse focus:outline-none" />
                        <span class="absolute top-0 right-0 h-2 w-2 rounded-full bg-red-500"></span>
                    </div>
                    <img class="w-10 h-10 rounded-full border-2 border-gray-50 ml-[-4px] transition-transform duration-200 hover:scale-110 hover:border-blue-500"
                        src="\src\assets\logo.jpg" alt="" @click="toggleDropdownMenu" />
                    <transition name="dropdown-fade">
                        <div v-show="showDropDown"
                            class="absolute top-full mt-2 right-0 z-10 w-44 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                            <div class="py-1 text-left" role="none">
                                <a v-for="item in dropdownItems" :key="item.label" :href="item.href"
                                    class="text-gray-700 block px-4 py-2 text-sm" role="menuitem">
                                    {{ item.label }}
                                </a>
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
    z-index: 10; /* Đảm bảo navbar luôn nằm trên các phần tử khác */
    background-color: #f1f1f1; /* Màu nền của navbar */
}

/* Cung cấp không gian cho nội dung */
.content-area {
    height: calc(100vh - 50px); /* Đảm bảo nội dung chiếm hết chiều cao còn lại */
    overflow-y: auto; /* Cho phép cuộn dọc khi nội dung vượt quá kích thước */
}

/* Thiết lập các kiểu cho bảng */
.table-container {
    width: 100%;
    overflow-x: auto; /* Cho phép cuộn ngang nếu bảng quá rộng */
}

</style>
