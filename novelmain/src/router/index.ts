import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Dashboard from '../pages/Dashboard.vue';
import Analysis from '../pages/Analysis.vue';
import AddNovel from '../pages/AddNovel.vue';
import ExistedNovel from '../pages/ExistedNovel.vue';
import Login from '../pages/Login.vue';
import Register from '../pages/Register.vue';
import Support from '../pages/Support.vue';
import SystemNotification from '../pages/SystemNotification.vue';
import Home from '../pages/Home.vue';
const routes: Array<RouteRecordRaw> = [
  {
    name: 'dashboard',
    path: '/dashboard',
    component: Dashboard,
    children: [
      {
        name: 'analytics',
        path: '/analytics',
        component: Analysis,
      },
      {
        name: 'newnovel',
        path: '/new-novel',
        component: AddNovel,
      },
      {
        name: 'existednovels',
        path: '/existing-novels',
        component: ExistedNovel,
      },
      {
        name: 'support',
        path: '/support',
        component: Support,
      },
      {
        name: 'adminnotification',
        path: '/admin-notification',
        component: SystemNotification,
      }
    ]
  },
  {
    name: 'login',
    path: '/login',
    component: Login,
  },
  {
    name: 'register',
    path: '/register',
    component: Register,
  },
  {
    name: 'home',
    path: '/',
    component: Home,
  },
];

// Tạo router
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
