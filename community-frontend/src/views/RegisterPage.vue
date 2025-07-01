<template>
  <div class="form-container">
    <h2>Register</h2>
    <el-form :model="form" label-width="80px">
      <el-form-item label="Username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Register</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

const router = useRouter();
const form = reactive({
  username: '',
  email: '',
  password: '',
});

const onSubmit = async () => {
  try {
    const response = await axios.post('/api/auth/register', form);
    ElMessage.success(response.data);
    router.push('/login'); // 注册成功后跳转到登录页
  } catch (error) {
    ElMessage.error(error.response.data);
  }
};
</script>

<style scoped>
.form-container {
  max-width: 400px;
  margin: 50px auto;
}
</style>