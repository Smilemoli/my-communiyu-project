import { defineStore } from 'pinia';
import axios from 'axios';

export const useAuthStore = defineStore('auth', {
  /**
   * 1. 定义 state (核心状态)
   * 一个函数，返回一个对象，包含你的状态。
   */
  state: () => ({
    // 应用启动时，立即尝试从浏览器的 localStorage 中读取已保存的 token。
    // 如果存在，用户就保持登录状态；如果不存在，则为 null。
    // 这是实现“持久化登录”（刷新页面不掉线）的关键。
    token: localStorage.getItem('token') || null,
    user: null, // 预留字段，以后可以用来存放更详细的用户信息，如用户名、头像等
  }),

  /**
   * 2. 定义 getters (类似Vue的计算属性)
   * 用于从 state 派生出一些新的状态。
   */
  getters: {
    // 一个布尔值，方便地告诉我们用户当前是否已认证。
    // '!!' 是一个JavaScript技巧，可以将任何值转换为布尔值（null, undefined, '' -> false; 其他 -> true）。
    isAuthenticated: (state) => !!state.token,
  },

  /**
   * 3. 定义 actions (方法)
   * 用于执行异步或同步操作来修改 state。
   */
  actions: {
    /**
     * 异步登录方法
     * @param {object} credentials - 包含用户名和密码的对象
     */
    async login(credentials) {
      try {
        // 调用后端的登录API
        const response = await axios.post('/api/auth/login', credentials);
        const token = response.data.accessToken;

        // 确保我们真的从后端获取到了token
        if (token) {
          // a. 更新 Pinia store 中的 token 状态
          this.token = token;
          // b. 将 token 存入浏览器的 localStorage，以便下次刷新或打开应用时读取
          localStorage.setItem('token', token);
          return true;
        }
        return false;
      } catch (error) {
        // 如果API调用失败（如密码错误），确保清空所有旧的状态
        this.logout();
        throw error; // 将错误继续向上抛出，以便Vue组件可以捕获并显示提示
      }
    },

    /**
     * 退出登录方法
     */
    logout() {
      // a. 清空 Pinia store 中的 token 和 user 信息
      this.token = null;
      this.user = null;
      // b. 从 localStorage 中移除 token，完成持久化状态的登出
      localStorage.removeItem('token');
    },
  },
});