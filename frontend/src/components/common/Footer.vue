<template>
  <a-layout-footer class="bg-gray-100 p-6 text-center">
    <div class="max-w-7xl mx-auto flex justify-between items-center">
      <span>© 2023 <a href="https://flowbite.com/" class="text-gray-600">Flowbite™</a>. All Rights Reserved.</span>
      <ul class="flex space-x-4 m-0 p-0 list-none">
        <li><router-link to="#" class="text-gray-600">About</router-link></li>
        <li><router-link to="#" class="text-gray-600">Privacy Policy</router-link></li>
        <li><router-link to="#" class="text-gray-600">Licensing</router-link></li>
        <li><router-link to="#" class="text-gray-600">Contact</router-link></li>
      </ul>
    </div>
  </a-layout-footer>
</template>

<script setup lang="ts">
import { Layout } from 'ant-design-vue';
</script>