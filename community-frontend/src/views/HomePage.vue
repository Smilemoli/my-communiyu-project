<template>
  <div class="home-container">
    <div class="header">
      <h1>Welcome to our Community!</h1>
      <el-button v-if="authStore.isAuthenticated" type="primary" @click="goToCreatePost">
        Create New Post
      </el-button>
    </div>

    <div class="post-list">
      <el-card v-for="post in posts" :key="post.id" class="post-card">
        <template #header>
          <div class="card-header">
            <router-link :to="`/posts/${post.id}`" class="post-title-link">
              {{ post.title }}
            </router-link>
          </div>
        </template>
        <div class="card-body">
          <span>Posted by: {{ post.authorUsername }}</span>
          <span class="post-time">{{ formatTime(post.createTime) }}</span>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'; // 引入 ref 和 onMounted
import { useAuthStore } from '../stores/authStore';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { ElMessage } from 'element-plus';

const authStore = useAuthStore();
const router = useRouter();

// 创建一个响应式变量来存储帖子列表
const posts = ref([]);

// 获取帖子列表的函数
const fetchPosts = async () => {
  try {
    const response = await axios.get('/api/posts');
    posts.value = response.data;
  } catch (error) {
    ElMessage.error('Failed to fetch posts.');
    console.error(error);
  }
};

// onMounted 是一个生命周期钩子，它会在组件被挂载到页面上时执行
// 我们在这里调用 fetchPosts 来保证一进入首页就加载帖子列表
onMounted(() => {
  fetchPosts();
});

// 跳转到创建帖子页面的函数
const goToCreatePost = () => {
  router.push('/create-post');
};

// 一个简单的时间格式化函数
const formatTime = (time) => {
  if (!time) return '';
  // 将ISO 8601格式的字符串转换为Date对象，然后格式化
  return new Date(time).toLocaleString();
};
</script>

<style scoped>
.home-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.post-list {
  display: flex;
  flex-direction: column;
  gap: 15px; /* 卡片之间的间距 */
}
.post-card {
  text-align: left;
}
.card-header {
  font-size: 1.2em;
}
.card-body {
  display: flex;
  justify-content: space-between;
  color: #888;
  font-size: 0.9em;
}

/* 帖子标题链接的样式 */
.post-title-link {
  text-decoration: none; /* 去掉下划线 */
  color: #303133; /* 设置默认颜色 */
  transition: color 0.3s; /* 添加颜色变化的过渡效果 */
}

/* 鼠标悬浮在标题上时的样式 */
.post-title-link:hover {
  color: #409eff; /* Element Plus 的主题蓝色 */
}
</style>