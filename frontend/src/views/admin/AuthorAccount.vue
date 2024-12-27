<script lang="ts" setup>
import {reactive} from 'vue';

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
<template>
  <div class="max-w-4xl mx-auto p-8 bg-white shadow-lg rounded-lg">
    <h1 class="text-3xl font-semibold text-gray-700 mb-6">Cài đặt thanh toán</h1>
    <p class="text-gray-500 mb-4">Bạn cần cài đặt thông tin thanh toán mới có thể nhận được thanh toán từ hệ thống.</p>
    <form @submit.prevent="submitForm">
      <div class="space-y-4">
        <div>
          <label class="block text-gray-700" for="accountHolder">Tên chủ khoản</label>
          <input id="accountHolder" v-model="formData.accountHolder"
                 class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500"
                 required
                 type="text"/>
        </div>
        <div>
          <label class="block text-gray-700" for="accountNumber">Số tài khoản</label>
          <input id="accountNumber" v-model="formData.accountNumber"
                 class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500"
                 required
                 type="text"/>
        </div>
        <div>
          <label class="block text-gray-700" for="bank">Ngân hàng</label>
          <input id="bank" v-model="formData.bank"
                 class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500"
                 required
                 type="text"/>
        </div>
        <div>
          <label class="block text-gray-700" for="branch">Chi nhánh</label>
          <input id="branch" v-model="formData.branch"
                 class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500"
                 required
                 type="text"/>
        </div>
        <div>
          <label class="block text-gray-700" for="city">Tỉnh thành</label>
          <input id="city" v-model="formData.city"
                 class="mt-1 block w-full p-1 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500"
                 required
                 type="text"/>
        </div>
      </div>
      <div class="flex justify-end mt-4">
        <button
            class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
          Submit
        </button>
      </div>
    </form>
  </div>

</template>


<style scoped>
/* Add any additional custom styling here */
</style>
  