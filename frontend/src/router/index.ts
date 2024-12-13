import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import store from '@/store';

// Import các component
import Dashboard from '@/views/admin/Dashboard.vue';
import Analysis from '@/views/admin/Analysis.vue';
import AddNovel from '@/views/admin/AddNovel.vue';
import NovelAuthorList from '@/views/admin/NovelAuthorList.vue';
import Support from '@/views/admin/Support.vue';
import SystemNotification from '@/views/admin/SystemNotification.vue';
import Login from '@/views/common/Login.vue';
import Register from '@/views/common/Register.vue';
import Home from '@/views/home/Home.vue';
import Chapter from '@/views/home/Chapter.vue';
import NovelDetail from '@/views/home/NovelDetail.vue';
import Account from '@/views/home/Account.vue';
import TabSwitch from '@/views/home/TabSwitch.vue';
import BookMark from '@/components/home/BookMark.vue';
import RecentlyReadingList from '@/components/home/RecentlyReadingList.vue';
import UserProfileSetting from '@/views/home/UserProfileSetting.vue';
import Library from '@/views/home/Library.vue';
import TransactionHistory from '@/views/home/TransactionHistory.vue';
import NovelAuthorManagement from '@/views/admin/NovelAuthorManagement.vue';
import FAQ from '@/views/common/FAQ.vue';
import Payment from '@/views/admin/Payment.vue';
import AuthorAccount from '@/views/admin/AuthorAccount.vue';
import PostForum from '@/views/home/PostForum.vue';
import PostDetail from '@/views/home/PostDetail.vue';
import PostCreateForm from '@/views/home/PostCreateForm.vue';
import Activation from "@/views/home/Activation.vue";
import PostUpdateForm from "@/views/home/PostUpdateForm.vue";
import PostCategoryNew from "@/components/admin/PostCategoryNew.vue";
import PostCategoryList from "@/views/admin/PostCategoryList.vue";
import PostCategoryEdit from "@/components/admin/PostCategoryEdit.vue";
import PostList from "@/views/admin/PostList.vue";
import NovelCategoryNew from "@/components/admin/NovelCategoryNew.vue";
import NovelCategoryList from "@/views/admin/NovelCategoryList.vue";
import NovelCategoryEdit from "@/components/admin/NovelCategoryEdit.vue";
import NovelList  from "@/views/admin/NovelList.vue";
import NovelCategoryForAuthor from "@/views/admin/NovelCategoryForAuthor.vue";
import PostCategoryForAuthor from "@/views/admin/PostCategoryForAuthor.vue";
import FilterByCategory from "@/views/home/FilterByCategory.vue";
import SearchAdvanced from "@/views/home/SearchAdvanced.vue";
import NotifcationList from "@/views/home/NotifcationList.vue";
const routes: Array<RouteRecordRaw> = [
  { name: 'dashboard', path: '/dashboard', component: Dashboard, 
    children: [
      { name: 'analytics', path: '/analytics', component: Analysis },
      { name: 'createNovel', path: '/create-novel', component: AddNovel },
      { name: 'novelOfAuthorList', path: '/novels-of-author', component: NovelAuthorList },
      { name: 'support', path: '/support', component: Support },
      { name: 'systemNotification', path: '/system-notification', component: SystemNotification },
      { name: 'novelAuthorManagement', path: '/novel-author-management/:id', component: NovelAuthorManagement, props: true },
      { name: 'payment', path: '/payment', component: Payment },
      { name: 'authorAccount', path: '/author-account', component: AuthorAccount },
      { name: 'createPostCategory', path: '/create-post-category', component: PostCategoryNew },
      { name: 'postCategoryList', path: '/post-category-list', component: PostCategoryList },
      { name: 'updatePostCategory', path: '/update-post-category/:id', component: PostCategoryEdit, props: true },
      { name: 'postList', path: '/post-list', component: PostList },
      { name: 'createNovelCategory', path: '/create-novel-category', component: NovelCategoryNew },
      { name: 'novelCategoryList', path: '/novel-category-list', component: NovelCategoryList },
      { name: 'updateNovelCategory', path: '/update-novel-category/:id', component: NovelCategoryEdit, props: true },
      { name: 'novelList', path: '/novel-list', component: NovelList },
      { name: 'novelCategoryForAuthor', path: '/novel-category-for-author', component: NovelCategoryForAuthor},
      { name: 'postCategoryForAuthor', path: '/post-category-for-author', component: PostCategoryForAuthor},

    ]
  },
  { name: 'login', path: '/login', component: Login },
  { name: 'register', path: '/register', component: Register },
  { name: 'home', path: '/', component: Home },
  { name: 'chapter', path: '/novel/:id', component: Chapter},
  { name: 'noveldetail', path: '/noveldetail/:id', component: NovelDetail},
  { name: 'account', path: '/account', component: Account },
  {
    name: 'list',
    path: '/list',
    component: TabSwitch,
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
        component: RecentlyReadingList,
        props: { type: 'readinglist' }
      }
    ]
  },
  { name: 'userprofile', path: '/user-profile', component: UserProfileSetting },
  { name: 'library', path: '/library', component: Library },
  { name: 'history', path: '/history', component: TransactionHistory },
  { name: 'faq', path: '/faq', component: FAQ },
  { name: 'postforum', path: '/post-forum', component: PostForum, },
  { name: 'postdetail', path: '/post/:id', component: PostDetail, props: true },
  { name: 'postcreateform', path: '/post-create-form', component: PostCreateForm },
  { name: 'postupdateform', path: '/post-update-form/:id', component: PostUpdateForm, props: true },
  { name: 'activation', path: '/activation/:token', component: Activation },
  { name: 'filterbycategory', path: '/filter-by-category', component: FilterByCategory},
  { name: 'searchadvanced', path: '/search-advanced', component: SearchAdvanced},
  { name: 'notificationlist', path: '/notification-list', component: NotifcationList},
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
