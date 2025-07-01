import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router' 

const app = createApp(App)
const pinia = createPinia() // 创建Pinia实例

app.use(pinia) // 【关键】确保这行存在，激活Pinia
app.use(router) 
app.use(ElementPlus)

app.mount('#app')