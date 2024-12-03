<template>
    <div class="max-w-4xl mx-auto p-8 bg-white shadow-lg rounded-lg">
      <h1 class="text-3xl font-semibold text-gray-700 mb-6">Cài đặt thanh toán</h1>
      <p class="text-gray-500 mb-4">Bạn cần cài đặt thông tin thanh toán mới có thể nhận được thanh toán từ hệ thống.</p>
      <form @submit.prevent="submitForm">
        <div class="space-y-4">
          <div>
            <label for="accountHolder" class="block text-gray-700">Tên chủ khoản</label>
            <input v-model="formData.accountHolder" type="text" id="accountHolder" class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500" required />
          </div>
          <div>
            <label for="accountNumber" class="block text-gray-700">Số tài khoản</label>
            <input v-model="formData.accountNumber" type="text" id="accountNumber" class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500" required />
          </div>
          <div>
            <label for="bank" class="block text-gray-700">Ngân hàng</label>
            <input v-model="formData.bank" type="text" id="bank" class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500" required />
          </div>
          <div>
            <label for="branch" class="block text-gray-700">Chi nhánh</label>
            <input v-model="formData.branch" type="text" id="branch" class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500" required />
          </div>
          <div>
            <label for="city" class="block text-gray-700">Tỉnh thành</label>
            <input v-model="formData.city" type="text" id="city" class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500" required />
          </div>
        </div>
        <div class="flex justify-end mt-4">
          <button class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
            Submit
          </button>
        </div>
      </form>
  </div>
      
  </template>
  
  <script setup lang="ts">
  import { reactive } from 'vue';
  
  const formData = reactive({
    accountHolder: '',
    accountNumber: '',
    bank: '',
    branch: '',
    city: ''
  });
  
  const submitForm = () => {
    console.log(formData);
  };
  </script>
  
  <style scoped>
  /* Add any additional custom styling here */
  </style>
  