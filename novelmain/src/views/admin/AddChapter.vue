<template>
    
    <!-- Main Content -->
    <main class="flex-1 p-6 bg-gray-100 shadow-md">
        <h1>Add Chapter</h1>
        <div>
            <!-- Title Input -->
            <label for="title" class="block text-sm font-medium text-gray-700">Tiêu đề</label>
            <input type="text" id="title" v-model="title"
                class="block w-full px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
        </div>

        <!-- Status Selection -->
        <div class="mt-4">
            <label for="status" class="block text-sm font-medium text-gray-700">Trạng thái</label>
            <select id="status" v-model="status"
                class="block w-full px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
                <option value="completed">Đã hoàn thành</option>
                <option value="incomplete">Chưa hoàn thành</option>
            </select>
        </div>
        <!-- Content Area -->
        <div class="mt-6">
            <textarea v-model="content" rows="10"
                class="w-full px-4 py-2 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                placeholder="Nhập nội dung tại đây..."></textarea>
        </div>
    </main>
</template>
<script setup lang="ts">
import { ref } from 'vue';


const title = ref("");
const status = ref("incomplete");
const content = ref("");
</script>