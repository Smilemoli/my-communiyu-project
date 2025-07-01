import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173, // 你的前端服务运行端口
    proxy: {
      // 这个配置会将所有以 /api 开头的请求，都转发到你的后端服务
      '/api': {
        // 目标后端地址
        target: 'http://localhost:8080',
        // 这是必须的，否则代理会失败
        changeOrigin: true,
      }
    }
  }
})