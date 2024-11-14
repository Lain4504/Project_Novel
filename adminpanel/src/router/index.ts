import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Dashboard from '../pages/Dashboard.vue';
const routes: Array<RouteRecordRaw> = [
    {
      name: 'dashboard',
      path: '/',
      component: Dashboard,
    }
];

// Tạo router
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
