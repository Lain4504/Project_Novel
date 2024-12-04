<script setup lang="ts">
import { ref } from 'vue';
import Tiptap from '@/components/common/Tiptap.vue';

const title = ref("");
const status = ref("incomplete");
const content = ref("");
const isPaid = ref(false); // Biến để xác định có trả phí hay không
const price = ref(0); // Giá tiền
</script>

<template>
    <!-- Main Content -->
    <main class="flex-1 p-6 bg-[#F8F8F7] shadow-md">
        <h1 class="my-2">Add Chapter</h1>

        <!-- Title Input -->
        <div>
            <label for="title" class="block text-sm font-medium text-gray-700">Tiêu đề</label>
            <input type="text" id="title" v-model="title"
                class="block w-full px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
        </div>

        <!-- Status Selection -->
        <div class="mt-4">
            <label for="status" class="block text-sm font-medium text-gray-700">Trạng thái</label>
            <select id="status" v-model="status"
                class="block w-1/2 px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
                <option value="completed">Đã hoàn thành</option>
                <option value="incomplete">Chưa hoàn thành</option>
            </select>
        </div>


        <!-- Paid or Free -->
        <div class="mt-4">
            <span class="block text-sm font-medium text-gray-700 ">Chọn loại</span>
            <div class="mt-2">
                <label class="inline-flex items-center">
                    <input type="radio" v-model="isPaid" :value="true" class="form-radio text-blue-500" />
                    <span class="ml-2">Trả phí</span>
                </label>
                <label class="inline-flex items-center ml-6">
                    <input type="radio" v-model="isPaid" :value="false" class="form-radio text-blue-500" />
                    <span class="ml-2">Không trả phí</span>
                </label>
            </div>
        </div>

        <!-- Price Input -->
        <div v-if="isPaid" class="mt-4 w-1/2">
            <label for="price" class="block text-sm font-medium text-gray-700">Giá tiền</label>
            <input type="number" id="price" v-model="price"
                class="block w-full px-4 py-2 mt-1 text-gray-900 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                placeholder="Nhập giá tiền" />
        </div>


        <!-- Content Area -->
        <div class="mt-4">
            <label for="content" class="block text-sm font-medium text-gray-700">Nội dung</label>
            <Tiptap v-model="content" class="mt-1" />
        </div>
        <div class="flex justify-end mt-4">
            <button
                class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
                Submit
            </button>
        </div>
    </main>
</template>

