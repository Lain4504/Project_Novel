<template>
   <nav
  class="inline-flex items-center px-3 py-2 text-gray-700 border-gray-200 rounded-lg  dark:bg-gray-800 dark:border-gray-700"
  aria-label="Breadcrumb"
>
  <ol class="inline-flex items-center space-x-1 md:space-x-2 rtl:space-x-reverse">
    <li v-for="(breadcrumb, index) in breadcrumbs" :key="index" class="inline-flex items-center">
      <div v-if="index === 0">
        <a
          :href="breadcrumb.href"
          class="inline-flex items-center text-sm font-medium text-gray-700 hover:text-blue-600 dark:text-gray-400 dark:hover:text-white"
        >
          <font-awesome-icon :icon="['fas', 'house']" class="w-3 h-3 mr-2" />
          {{ breadcrumb.label }}
        </a>
      </div>
      <div v-else>
        <div class="flex items-center">
          <font-awesome-icon :icon="['fas', 'chevron-right']" class="w-3 h-3 rtl:rotate-180 block text-gray-400" />
          <a
            v-if="!breadcrumb.isCurrent"
            :href="breadcrumb.href"
            class="ms-1 text-sm font-medium text-gray-700 hover:text-blue-600 md:ms-2 dark:text-gray-400 dark:hover:text-white"
          >
            {{ breadcrumb.label }}
          </a>
          <span
            v-else
            class="ms-1 text-sm font-medium text-gray-500 md:ms-2 dark:text-gray-400"
          >
            {{ breadcrumb.label }}
          </span>
        </div>
      </div>
    </li>
  </ol>
</nav>

  </template>
  
  <script setup lang="ts">
  interface Breadcrumb {
    label: string;
    href: string;
    isCurrent?: boolean;
  }
  
  defineProps<{
    breadcrumbs: Breadcrumb[];
  }>();
  </script>
  