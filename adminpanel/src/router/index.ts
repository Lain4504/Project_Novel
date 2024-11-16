import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Dashboard from '../pages/Dashboard.vue';
import UserManagement from '../pages/UserManagement.vue';
import Analysis from '../pages/Analysis.vue';
import AddNovel from '../pages/AddNovel.vue';
import ExistedNovel from '../pages/ExistedNovel.vue';
import Login from '../pages/Login.vue';
import Register from '../pages/Register.vue';
const routes: Array<RouteRecordRaw> = [
  {
    name: 'dashboard',
    path: '/',
    component: Dashboard,
    children: [
      {
        name: 'analysis',
        path: '/analysis',
        component: Analysis,
      },
      {
        name: 'usermanagement',
        path: '/user-management',
        component: UserManagement,
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
  }
];

// Tạo router
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
