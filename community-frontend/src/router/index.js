import { createRouter, createWebHistory } from 'vue-router';

import HomePage from '../views/HomePage.vue';
import LoginPage from '../views/LoginPage.vue';
import RegisterPage from '../views/RegisterPage.vue';
import CreatePostPage from '../views/CreatePostPage.vue';
import PostDetailPage from '../views/PostDetailPage.vue'; // 1. 引入详情页组件

const routes = [
  { path: '/', name: 'Home', component: HomePage }, 
  { path: '/login', name: 'Login', component: LoginPage },
  { path: '/register', name: 'Register', component: RegisterPage },
  { path: '/create-post', name: 'CreatePost', component: CreatePostPage },
  // 2. 添加动态路由配置
  // ':id' 是一个动态段，它会匹配 /posts/ 后面的任何字符串
  // 并将这个字符串作为参数 'id' 传递给组件
  { path: '/posts/:id', name: 'PostDetail', component: PostDetailPage }, 
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;