<template>
    <div class="max-w-7xl mx-auto">
        <Ads class="my-4" />
        <Breadcrumb :breadcrumbs="[
            { label: 'Home', href: '/' },
            { label: 'Forum', href: '/forum' },
            { label: 'Post', href: '/post', isCurrent: true }
        ]" />
        <div class=" bg-white p-6 mt-6 rounded-lg shadow-md">
            <!-- Header -->
            <div class="flex items-center justify-between mb-6">
                <div class="flex items-center">
                    <img src="https://via.placeholder.com/40" alt="Avatar" class="w-10 h-10 rounded-full mr-3">
                    <div class="font-semibold text-lg">User <span class="text-gray-500 text-sm">Administrators</span>
                    </div>
                </div>
                <div class="text-sm text-gray-500">3 năm trước</div>
            </div>

            <!-- Title of the Post -->
            <h1 class="text-3xl font-bold text-gray-800 mb-4">Thảo luận cho tác giả OLN</h1>

            <!-- Content Body -->
            <div class="content-body mb-6">
                <!-- Content here -->
            </div>

            <!-- Comment Section -->
            <div class="mt-6">
                <h2 class="text-xl font-semibold text-gray-800">14302 Bình luận</h2>
                <textarea class="w-full p-3 mt-4 border rounded-lg shadow-sm" v-model="comment"
                    placeholder="Thêm bình luận..."></textarea>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import Ads from '@/components/home/Ads.vue';
import Breadcrumb from '@/components/common/Breadcrumb.vue';
// State for storing the comment input
const comment = ref('');
</script>

<style scoped>
.content-body {
    font-size: 1rem;
    line-height: 1.75;
}
</style>