<template>
  <div class="create-post-container">
    <h2>Create a New Post</h2>
    <el-form :model="form" label-position="top">
      <el-form-item label="Title">
        <el-input v-model="form.title" placeholder="Enter the title"></el-input>
      </el-form-item>
      <el-form-item label="Content">
        <el-input
          v-model="form.content"
          type="textarea"
          :rows="10"
          placeholder="Enter the content"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">Publish Post</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/authStore';

const router = useRouter();
const authStore = useAuthStore();

const form = reactive({
  title: '',
  content: '',
});

const handleSubmit = async () => {
  if (!form.title || !form.content) {
    ElMessage.error('Title and content cannot be empty.');
    return;
  }

  try {
    // 创建一个包含认证头的axios实例
    const api = axios.create({
      headers: {
        'Authorization': `Bearer ${authStore.token}`
      }
    });

    // 调用我们后端的 POST /api/posts 接口
    const response = await api.post('/api/posts', form);

    ElMessage.success(response.data);
    // 发布成功后，跳转到首页（我们未来会在首页展示帖子列表）
    router.push('/');

  } catch (error) {
    // 如果token过期或无效，后端会返回401或403
    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      ElMessage.error('Your session has expired. Please log in again.');
      authStore.logout();
      router.push('/login');
    } else {
      ElMessage.error('Failed to create post. Please try again.');
    }
    console.error(error);
  }
};
</script>

<style scoped>
.create-post-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 2rem;
  background-color: #fff;
  border-radius: 8px;
}
</style>