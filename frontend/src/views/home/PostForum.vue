<template>
    <div class="max-w-7xl mx-auto">
        <Ads class="my-4" />
        <Breadcrumb :breadcrumbs="[
            { label: 'Home', href: '/' },
            { label: 'Forum', href: '/post-forum', isCurrent: true },
        ]" />
        <div class="p-4 min-h-screen">
            <!-- Header -->
            <div class="mb-6">
                <!-- Title -->
                <h1 class="text-xl font-semibold text-gray-800 mb-2">Thảo luận</h1>

                <!-- Dropdown and Add Button -->
                <div class="flex flex-col md:flex-row justify-between items-center">
                    <!-- Dropdown -->
                    <div class="w-full md:w-1/3 mb-4 md:mb-0">
                        <select
                            class="block w-full px-3 py-2 text-gray-700 bg-[#F8F8F7] border border-gray-300 rounded shadow-sm focus:outline-none focus:ring focus:ring-green-300">
                            <option>Chuyên mục</option>
                        </select>
                    </div>

                    <!-- Add Button -->
                    <button
                        class="flex items-center justify-center w-10 h-10 bg-[#C96442] text-white rounded-full hover:bg-[#BA5B38]">
                        <font-awesome-icon :icon="['fas', 'plus']" class="text-lg" />
                    </button>

                </div>
            </div>

            <table class="min-w-full bg-[#F8F8F7] shadow rounded-lg overflow-hidden">
                <thead>
                    <tr class="bg-gray-100 text-left text-sm font-semibold text-gray-600 uppercase">
                        <th class="px-4 py-3">Chủ đề</th>
                        <!-- Ẩn cột "Chuyên mục" trên màn hình nhỏ, hiển thị trên màn hình lớn -->
                        <th class="px-4 py-3 hidden md:table-cell">Chuyên mục</th>
                        <th class="px-4 py-3 hidden md:table-cell">Bình luận</th>
                        <th class="px-4 py-3 hidden md:table-cell">Lượt xem</th>
                        <th class="px-4 py-3">Gần nhất</th>
                        <th class="px-4 py-3">Người đăng cuối</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, index) in data" :key="index"
                        class="border-t hover:bg-gray-100 transition text-sm">
                        <td class="px-4 py-3 flex items-center">
                            <span class="text-black font-medium">
                                <i v-if="item.isImportant" class="fas fa-star text-yellow-400 mr-2"></i>
                                {{ item.title }}
                            </span>
                        </td>
                        <!-- Hiển thị "Chuyên mục" dưới "Chủ đề" khi màn hình nhỏ hơn md -->
                        <td class="px-4 py-3 block md:hidden">
                            <span :class="{
                                'text-green-500': item.category === 'Thảo luận',
                                'text-red-500': item.category === 'Thông báo'
                            }" class="font-semibold">
                                {{ item.category }}
                            </span>
                        </td>
                        <!-- Cột "Chuyên mục" hiển thị bình thường trên màn hình lớn -->
                        <td class="px-4 py-3 text-gray-700 hidden md:table-cell">{{ item.category }}</td>
                        <td class="px-4 py-3 text-gray-700 hidden md:table-cell">{{ item.comments }}</td>
                        <td class="px-4 py-3 text-gray-700 hidden md:table-cell">{{ item.views }}</td>
                        <td class="px-4 py-3 text-gray-500">{{ item.recent }}</td>
                        <td class="px-4 py-3 flex items-center space-x-2">
                            <img :src="item.lastUser.avatar" alt="Avatar" class="w-8 h-8 rounded-full" />
                            <span class="text-gray-700">{{ item.lastUser.name }}</span>
                        </td>
                    </tr>
                </tbody>
            </table>




            <!-- Pagination -->
            <div class="flex justify-center mt-4">
                <button class="px-3 py-1 mx-1 text-sm text-gray-400 rounded hover:bg-[#BA5B38]"
                    :class="{ 'cursor-not-allowed': currentPage === 1 }" :disabled="currentPage === 1"
                    @click="goToPage(1)">
                    Đầu
                </button>
                <button v-for="page in visiblePages" :key="page"
                    class="px-3 py-1 mx-1 text-sm rounded hover:bg-[#BA5B38] hover:text-[#F8F8F7]" :class="{
                        'bg-[#C96442] text-white': page === currentPage,
                        'text-[#BA5B38]': page !== currentPage
                    }" @click="goToPage(page)">
                    {{ page }}
                </button>
                <button class="px-3 py-1 mx-1 text-sm text-gray-400 rounded hover:bg-[#BA5B38]"
                    :class="{ 'cursor-not-allowed': currentPage === totalPages }" :disabled="currentPage === totalPages"
                    @click="goToPage(totalPages)">
                    Cuối
                </button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import Ads from "@/components/home/Banner.vue";
import { ref, computed } from 'vue';
import Breadcrumb from '@/components/home/Breadcrumb.vue';
const data = [
    {
        title: "Thảo luận cho tác giả",
        category: "Thảo luận",
        comments: 14302,
        views: "1.236.974",
        recent: "36 phút",
        isImportant: true,
        lastUser: { name: "user1", avatar: "https://via.placeholder.com/40" },
    },
    {
        title: "Góp ý và báo lỗi",
        category: "Thông báo",
        comments: 5561,
        views: "650.647",
        recent: "12 giờ",
        isImportant: true,
        lastUser: { name: "user2", avatar: "https://via.placeholder.com/40" },
    },
];

// Pagination State
const currentPage = ref(1);
const totalPages = 10;

const visiblePages = computed(() => {
    const pages = [];
    const maxVisible = 5;
    let start = Math.max(1, currentPage.value - Math.floor(maxVisible / 2));
    let end = Math.min(totalPages, start + maxVisible - 1);

    if (end - start + 1 < maxVisible) {
        start = Math.max(1, end - maxVisible + 1);
    }

    for (let i = start; i <= end; i++) {
        pages.push(i);
    }

    return pages;
});

const goToPage = (page: number) => {
    if (page >= 1 && page <= totalPages) {
        currentPage.value = page;
    }
};
</script>

<style>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css');
</style>
