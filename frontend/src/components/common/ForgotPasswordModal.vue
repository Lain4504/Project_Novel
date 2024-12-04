<!-- ForgotPasswordModal.vue -->
<template>
  <div v-if="isModalVisible" id="forgot-password-modal" tabindex="-1" aria-hidden="true"
       class="overflow-y-auto fixed inset-0 z-50 flex items-center justify-center bg-gray-800 bg-opacity-50">
    <div class="relative p-4 w-full max-w-md max-h-full flex items-center justify-center">
      <div class="relative bg-white rounded-lg shadow dark:bg-gray-700 w-full">
        <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
          <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
            Quên mật khẩu
          </h3>
          <button @click="closeModal" type="button"
                  class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
            <font-awesome-icon :icon="['fas', 'times']" size="xl"/>
            <span class="sr-only">Close modal</span>
          </button>
        </div>
        <div class="p-4 md:p-5">
          <form class="space-y-4" action="#">
            <div>
              <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Nhập lại email của
                bạn</label>
              <input type="email" name="email" id="email"
                     class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
                     placeholder="name@company.com" required/>
            </div>
            <button type="submit"
                    class="w-full text-white bg-[#C15E3C] hover:bg-[#d76843] focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
              Gửi yêu cầu khôi phục mật khẩu
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">

import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

const props = defineProps({
  isModalVisible: Boolean,
});

const emit = defineEmits(['update:isModalVisible']);

const closeModal = () => {
  emit('update:isModalVisible', false); // Đóng modal
};
</script>
