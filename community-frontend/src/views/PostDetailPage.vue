<template>
  <div class="post-detail-container">
    <div v-if="loading" class="loading">Loading...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="post" class="post-wrapper">
      <div class="post-content">
        <h1 class="post-title">{{ post.title }}</h1>
        <div class="post-meta">
          <span>By {{ post.authorUsername }}</span>
          <span>on {{ formatTime(post.createTime) }}</span>
        </div>
        <pre class="post-body">{{ post.content }}</pre>
      </div>

      <div class="comment-section">
        <h3>Comments ({{ comments.length }})</h3>

        <div v-if="authStore.isAuthenticated" class="comment-form">
          <el-input
            v-model="newComment"
            type="textarea"
            :rows="4"
            placeholder="Add your comment..."
            class="comment-input"
          ></el-input>
          <el-button type="primary" @click="handleCommentSubmit" class="submit-button">
            Submit Comment
          </el-button>
        </div>
        <div v-else class="login-prompt">
          You must be <router-link to="/login">logged in</router-link> to comment.
        </div>

        <div v-if="comments.length > 0" class="comment-list">
          <el-card v-for="comment in comments" :key="comment.id" class="comment-card">
            <p>{{ comment.content }}</p>
            <div class="comment-footer">
              <span>-- {{ comment.authorUsername }}</span>
              <span>{{ formatTime(comment.createTime) }}</span>
            </div>
          </el-card>
        </div>
        <div v-else-if="!authStore.isAuthenticated" class="no-comments">
          There are no comments yet.
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router'; // 引入 useRouter
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { useAuthStore } from '../stores/authStore'; // 引入 authStore

const route = useRoute();
const router = useRouter(); // 获取 router 实例
const authStore = useAuthStore(); // 获取 authStore 实例

const post = ref(null);
const comments = ref([]);
const loading = ref(true);
const error = ref(null);
const postId = route.params.id;

const newComment = ref(''); // 【新增】用于绑定评论输入框的内容

// 获取帖子详情的函数
const fetchPostDetail = async () => {
  try {
    const response = await axios.get(`/api/posts/${postId}`);
    post.value = response.data;
  } catch (err) {
    if (err.response && err.response.status === 404) {
      error.value = 'Sorry, the post you are looking for does not exist.';
    } else {
      error.value = 'An error occurred while fetching the post.';
    }
    throw err;
  }
};

// 获取评论列表的函数
const fetchComments = async () => {
  try {
    const response = await axios.get(`/api/posts/${postId}/comments`);
    comments.value = response.data;
  } catch (error) {
    console.error('Failed to fetch comments:', error);
  }
};

// 【新增】提交评论的函数
const handleCommentSubmit = async () => {
  if (!newComment.value.trim()) {
    ElMessage.error('Comment cannot be empty.');
    return;
  }

  try {
    const api = axios.create({
      headers: {
        'Authorization': `Bearer ${authStore.token}`
      }
    });

    await api.post(`/api/posts/${postId}/comments`, { content: newComment.value });

    ElMessage.success('Comment submitted successfully!');
    newComment.value = ''; // 清空输入框
    await fetchComments(); // 【关键】重新获取评论列表，实现无刷新更新！

  } catch (err) {
    if (err.response && (err.response.status === 401 || err.response.status === 403)) {
      ElMessage.error('Your session has expired. Please log in again.');
      authStore.logout();
      router.push('/login');
    } else {
      ElMessage.error('Failed to submit comment.');
    }
    console.error(err);
  }
};

onMounted(async () => {
  try {
    await Promise.all([fetchPostDetail(), fetchComments()]);
  } catch (err) {
    console.error("Failed to load page data.", err);
  } finally {
    loading.value = false;
  }
});

const formatTime = (time) => {
  if (!time) return '';
  return new Date(time).toLocaleString();
};
</script>

<style scoped>
/* ... post-detail-container 等已有样式保持不变 ... */
.comment-section {
  margin-top: 3rem;
  padding-top: 2rem;
  border-top: 1px solid #ebeef5;
}
.comment-list {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}
.comment-card { text-align: left; }
.comment-footer {
  display: flex;
  justify-content: space-between;
  font-size: 0.8em;
  color: #aaa;
  margin-top: 15px;
}
.no-comments, .login-prompt {
  color: #909399;
  text-align: center;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 4px;
}
.comment-form {
  margin-top: 1rem;
}
.submit-button {
  margin-top: 10px;
  float: right;
}
</style>