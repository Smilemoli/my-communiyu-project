<template>
  <div class="app-container">
    <header class="app-header">
      <nav>
        <router-link to="/">Home</router-link> |

        <span v-if="!authStore.isAuthenticated">
          <router-link to="/login">Login</router-link> |
          <router-link to="/register">Register</router-link>
        </span>
        <span v-else>
          <a @click="handleLogout" href="#" class="logout-link">Logout</a>
        </span>
        
      </nav>
    </header>

    <main class="app-main">
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup>
// 1. 引入所有必要的模块
import { useAuthStore } from './stores/authStore';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

// 2. 获取 store 和 router 的实例
const authStore = useAuthStore();
const router = useRouter();

// 3. 定义退出登录的处理函数
const handleLogout = () => {
  // 调用 store 中的 logout action
  authStore.logout();
  // 弹出成功提示
  ElMessage.success('You have been logged out.');
  // 跳转回登录页面
  router.push('/login');
};
</script>

<style>
/* 全局样式 */
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  margin: 0;
  background-color: #f0f2f5;
}

/* 应用容器样式 */
.app-container {
  max-width: 1200px;
  margin: 0 auto;
}

/* 头部导航栏样式 */
.app-header {
  background-color: white;
  padding: 1rem 2rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.app-header nav {
  font-size: 1.1rem;
  display: flex;
  align-items: center;
  gap: 15px; /* 链接之间的间距 */
}

.app-header nav a {
  font-weight: bold;
  color: #2c3e50;
  text-decoration: none;
}

/* 当前激活路由链接的样式 */
.app-header nav a.router-link-exact-active {
  color: #42b983;
}

/* Logout 链接的特殊样式 */
.logout-link {
  cursor: pointer; /* 让鼠标变成手形 */
}
.logout-link:hover {
  color: #409eff; /* 鼠标悬浮时变色 */
}

/* 主内容区域样式 */
.app-main {
  padding: 0 2rem;
}
</style>