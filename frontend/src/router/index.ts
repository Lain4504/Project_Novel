import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import store from '@/store';

// Import các component
import Dashboard from '@/views/admin/Dashboard.vue';
import Analysis from '@/views/admin/Analysis.vue';
import AddNovel from '@/views/admin/AddNovel.vue';
import ExistedNovel from '@/views/admin/NovelAuthorList.vue';
import Support from '@/views/admin/Support.vue';
import SystemNotification from '@/views/admin/SystemNotification.vue';
import Login from '@/views/common/Login.vue';
import Register from '@/views/common/Register.vue';
import Home from '@/views/home/Home.vue';
import Chapter from '@/views/home/Chapter.vue';
import NovelDetail from '@/views/home/NovelDetail.vue';
import Account from '@/views/home/Account.vue';
import Tab from '@/views/home/TabSwitch.vue';
import BookMark from '@/components/home/BookMark.vue';
import ReadingList from '@/components/home/RecentlyReadingList.vue';
import UserProfile from '@/views/home/UserProfileSetting.vue';
import Libarary from '@/views/home/Libarary.vue';
import History from '@/views/home/TransactionHistory.vue';
import AdvancedManagement from '@/views/admin/AdvancedManagement.vue';
import EditNovel from '@/views/admin/NovelAuthorManagement.vue';
import FAQ from '@/views/common/FAQ.vue';
import Payment from '@/views/admin/Payment.vue';
import AuthorAccount from '@/views/admin/AuthorAccount.vue';
import PostForum from '@/views/home/PostForum.vue';
import PostDetail from '@/views/home/PostDetail.vue';
import PostCreateForm from '@/views/home/PostCreateForm.vue';
import Activation from "@/views/home/Activation.vue";
const routes: Array<RouteRecordRaw> = [
  { name: 'dashboard', path: '/dashboard', component: Dashboard, 
    children: [
      { name: 'analytics', path: '/analytics', component: Analysis },
      { name: 'newnovel', path: '/new-novel', component: AddNovel },
      { name: 'existednovels', path: '/existing-novels', component: ExistedNovel },
      { name: 'support', path: '/support', component: Support },
      { name: 'adminnotification', path: '/admin-notification', component: SystemNotification },
      { name: 'editnovel', path: '/edit-novel', component: EditNovel },
      { name: 'payment', path: '/payment', component: Payment },
      { name: 'authoraccount', path: '/author-account', component: AuthorAccount }
    ]
  },
  { name: 'login', path: '/login', component: Login },
  { name: 'register', path: '/register', component: Register },
  { name: 'home', path: '/', component: Home },
  { name: 'chapter', path: '/novel', component: Chapter },
  { name: 'noveldetail', path: '/noveldetail', component: NovelDetail },
  { name: 'member', path: '/member', component: Account },
  {
    name: 'list',
    path: '/list',
    component: Tab,
    children: [
      {
        path: '', // Mặc định khi không có URL con
        redirect: '/list/readinglist', // Chuyển hướng đến 'readinglist' khi không có URL con
      },
      {
        name: 'bookmark',
        path: 'bookmark',
        component: BookMark,
        props: { type: 'bookmark' }
      },
      {
        name: 'readinglist',
        path: 'readinglist',
        component: ReadingList,
        props: { type: 'readinglist' }
      }
    ]
  },
  { name: 'userprofile', path: '/user-profile', component: UserProfile },
  { name: 'library', path: '/library', component: Libarary },
  { name: 'history', path: '/history', component: History },
  { name: 'advancedmanagement', path: '/advanced-management', component: AdvancedManagement},
  { name: 'faq', path: '/faq', component: FAQ },
  { name: 'postforum', path: '/post-forum', component: PostForum },
  { name: 'postdetail', path: '/post-detail', component: PostDetail },
  { name: 'postcreateform', path: '/post-create-form', component: PostCreateForm },
  { name: 'activation', path: '/activation/:token', component: Activation }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

// Thêm Navigation Guard
router.beforeEach((to, from, next) => {
  const isAuthenticated = store.getters.isAuthenticated;

  // Nếu người dùng đã đăng nhập, chặn truy cập login và register
  if (isAuthenticated && (to.name === 'login' || to.name === 'register')) {
    next({ name: 'home' }); // Điều hướng đến trang Home
  } else {
    next(); // Cho phép truy cập
  }
});

export default router;
