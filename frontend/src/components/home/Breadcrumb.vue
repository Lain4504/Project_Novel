<script setup lang="ts">
import { Breadcrumb } from 'ant-design-vue';
import { defineProps } from 'vue';

interface BreadcrumbItem {
  label: string;
  to: any;
  isCurrent?: boolean;
}

defineProps<{
  breadcrumbs: BreadcrumbItem[];
}>();
</script>

<template>
  <Breadcrumb>
    <Breadcrumb.Item v-for="(breadcrumb, index) in breadcrumbs" :key="index">
      <router-link v-if="!breadcrumb.isCurrent" :to="breadcrumb.to">
        {{ breadcrumb.label }}
      </router-link>
      <span v-else>{{ breadcrumb.label }}</span>
    </Breadcrumb.Item>
  </Breadcrumb>
</template>

<style scoped>
/* Add any custom styles if needed */
</style>