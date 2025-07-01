<template>
  <div class="form-container">
    <h2>Login</h2>
    <el-form :model="form" label-width="80px">
      <el-form-item label="Username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="form.password" type="password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Login</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/authStore'; // 引入 store

const authStore = useAuthStore(); // 获取 store 实例
const router = useRouter();
const form = reactive({
  username: '',
  password: '',
});

const onSubmit = async () => {
  try {
    const success = await authStore.login(form);
    if (success) {
      ElMessage.success('Login successful!');
      router.push('/'); // 登录成功后，跳转到首页
    }
  } catch (error) {
    ElMessage.error(error.response?.data || 'Login failed.');
  }
};
</script>

<style scoped>
.form-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 2rem;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
</style>