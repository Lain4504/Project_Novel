<template>
    <div class="max-w-7xl mx-auto p-8">
        <Ads class="my-4"/>
        <Breadcrumb :breadcrumbs="[
            { label: 'Home', href: '/' },
            { label: 'Forum', href: '/forum' },
            { label: 'Post', href: '/post', isCurrent: true }
        ]" />
        <form class="max-w-7xl mx-auto my-4">
      <!-- Tiêu đề -->
      <div class="mb-6 ">
        <label for="title" class="block text-md font-semibold text-gray-800 mb-2">Tiêu đề *</label>
        <input type="text" id="title" class="w-full border border-gray-300 rounded-lg py-1 px-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500" placeholder="Nhập tiêu đề bài viết" />
      </div>
  
      <!-- Nội dung -->
      <div class="mb-6">
        <label for="content" class="block text-md font-semibold text-gray-800 mb-2">Nội dung *</label>
        <textarea id="content" class="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500" placeholder="Nhập nội dung bài viết" rows="6"></textarea>
      </div>
  
      <!-- Chuyên mục -->
      <div class="mb-6 grid grid-cols-1 md:grid-cols-2 gap-4">
      <div>
        <label for="category" class="block text-md font-semibold text-gray-800 mb-2">Chọn chuyên mục *</label>
        <select id="category" class="w-full border border-gray-300 rounded-lg py-1 px-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500">
          <option>Thông báo</option>
          <option>Hỏi đáp</option>
          <option>Thông tin</option>
        </select>
      </div>
  
      <!-- Truyền -->
      <div>
        <label for="channel" class="block text-md font-semibold text-gray-800 mb-2">Chọn truyền</label>
        <select id="channel" class="w-full border border-gray-300 rounded-lg py-1 px-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500">
          <option>-- Chọn truyền --</option>
          <option>Truyền 1</option>
          <option>Truyền 2</option>
          <option>Truyền 3</option>
        </select>
      </div>
    </div>
  
      <!-- Nút gửi -->
      <div class="text-right">
        <button class="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 px-6 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500">
          Thêm bài viết
        </button>
      </div>
    </form>
    </div>
  </template>
  
  <script setup lang="ts">
  import Ads from '@/components/home/Banner.vue';
  import Breadcrumb from '@/components/home/Breadcrumb.vue';

  </script>
  
  