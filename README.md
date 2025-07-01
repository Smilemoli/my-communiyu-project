# Full-Stack Community Application

这是一个使用 Spring Boot 和 Vue.js 构建的全栈社区项目。用户可以注册、登录、发布帖子、浏览帖子、发表评论等。

## ✨ 项目亮点/功能列表

- **用户系统**: 基于 Spring Security + JWT 的完整用户认证与授权。
- **内容发布**: 用户可以发布帖子和发表评论。
- **数据交互**: RESTful API 设计，前后端完全分离。
- **架构优化**: 实现了中央 `axios` 实例来自动处理认证头，解决了N+1查询问题。
- ... (其他你觉得值得一提的功能)

## 🛠️ 技术栈

- **后端**: Spring Boot, Spring Security, Spring Data JPA (Hibernate), MySQL, Lombok, JJWT
- **前端**: Vue 3, Vite, Vue Router, Pinia, Element Plus, Axios
- **数据库**: MySQL 8.x

## 🚀 如何运行

1.  克隆项目到本地: `git clone https://github.com/your-username/your-repository-name.git`
2.  **后端配置**:
    -   进入 `community-backend` 目录。
    -   在 `src/main/resources/` 下，复制 `application.yml.example` 并重命名为 `application.yml`。
    -   修改 `application.yml` 文件，填入你自己的MySQL数据库用户名和密码。
    -   使用 IntelliJ IDEA 打开后端项目并运行。
3.  **前端配置**:
    -   进入 `community-frontend` 目录。
    -   运行 `npm install` 安装依赖。
    -   运行 `npm run dev` 启动前端开发服务器。

## 📸 项目截图

<img width="1539" alt="截屏2025-07-01 12 56 17" src="https://github.com/user-attachments/assets/101e7b5c-a525-4fba-bef0-1da9ebb84063" />
