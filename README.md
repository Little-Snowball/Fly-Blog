# Fly-Blog

一个基于 Spring Boot + Vue 3 的前后端分离博客系统，支持文章管理、AI 对话、收藏与浏览历史等功能。

## 功能特性

- 文章管理 — 管理员可发布、编辑、删除文章，支持 Markdown 正文与标签分类
- 文章搜索 — 按标题、摘要、正文、标签模糊搜索
- AI 对话 — 内置 AI 助手，基于 OpenAI 兼容 API（默认 DeepSeek）
- 文章收藏 — 登录用户可收藏/取消收藏文章
- 浏览历史 — 自动记录已浏览的文章
- 角色控制 — 管理员与普通用户两种角色，基于 RBAC 权限管理
- 响应式布局 — 适配移动端（760px 断点）

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | JDK 21 + Spring Boot 3.3.6 + Spring Data JPA + Spring Security + JWT |
| 前端 | Vue 3 + TypeScript + Vite + Pinia + Vue Router + Element Plus |
| 数据库 | MySQL 8 |
| AI | OpenAI 兼容 API（默认 DeepSeek deepseek-v4-flash） |

## 目录结构

```
├── backend/                  Spring Boot 后端
│   └── src/main/java/com/course/blog/
│       ├── article/          文章模块（实体、控制器、服务、仓库）
│       ├── auth/             认证模块（JWT、登录、过滤器）
│       ├── ai/               AI 对话模块
│       ├── favorite/         收藏模块
│       ├── history/          浏览历史模块
│       ├── user/             用户模块与数据初始化
│       ├── config/           安全配置、属性配置
│       └── common/           统一响应封装、全局异常处理
├── frontend/                 Vue 3 前端
│   └── src/
│       ├── pages/            页面组件
│       ├── components/       通用组件
│       ├── api/              API 请求封装
│       ├── stores/           Pinia 状态管理
│       ├── router/           路由配置
│       └── layouts/          布局组件
├── application.yml.example   配置模板
└── README.md
```

## 页面路由

| 路径 | 页面 | 权限 | 说明 |
|------|------|------|------|
| `/articles` | 文章列表 | 公开 | 分页展示，支持搜索 |
| `/articles/:id` | 文章详情 | 公开 | Markdown 渲染、收藏、历史记录 |
| `/ai-chat` | AI 对话 | 登录 | 多轮对话界面 |
| `/favorites` | 我的收藏 | 登录 | 收藏的文章列表 |
| `/history` | 浏览历史 | 登录 | 浏览过的文章列表 |
| `/login` | 登录 | 公开 | 用户登录 |
| `/admin/articles/new` | 发布文章 | 管理员 | Markdown 编辑器 |
| `/admin/articles/:id/edit` | 编辑文章 | 管理员 | Markdown 编辑器 |

## 快速开始

### 环境要求

- JDK 21+
- Maven 3.6+
- Node.js 18+
- MySQL 8

### 1. 配置

复制配置模板并填入你的信息：

```bash
cd backend/src/main/resources
cp application.yml.example application.yml
```

编辑 `application.yml`，修改以下配置：

```yaml
spring:
  datasource:
    username: root        # 你的 MySQL 用户名
    password: your_password  # 你的 MySQL 密码

app:
  jwt-secret: your-jwt-secret  # JWT 签名密钥（任意长字符串）

openai:
  api-key: your-api-key  # DeepSeek 或其他 OpenAI 兼容 API 的 Key
```

也可以通过环境变量设置（优先级更高）：

```bash
# PowerShell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="your-jwt-secret"
$env:OPENAI_API_KEY="your-api-key"

# Linux / macOS
export DB_USERNAME="root"
export DB_PASSWORD="your_password"
export JWT_SECRET="your-jwt-secret"
export OPENAI_API_KEY="your-api-key"
```

### 2. 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端运行在 `http://localhost:8080`。

### 3. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端运行在 `http://localhost:5173`。

### 4. 访问

浏览器打开 `http://localhost:5173`，使用初始账号登录：

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | admin123 |
| 普通用户 | User1 | user111 |
| 普通用户 | User | user222 |

项目首次启动后会自动创建以上账号并插入三篇示例文章。

## API 接口

### 认证

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/auth/login` | 登录，返回 JWT |
| GET | `/api/auth/me` | 获取当前用户信息 |

### 文章

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/articles` | 文章列表（支持 `?page=&size=&keyword=`） |
| GET | `/api/articles/{id}` | 文章详情 |
| POST | `/api/articles` | 创建文章（管理员） |
| PUT | `/api/articles/{id}` | 更新文章（管理员） |
| DELETE | `/api/articles/{id}` | 删除文章（管理员） |

### 收藏

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/favorites` | 收藏列表 |
| POST | `/api/favorites/{articleId}` | 添加收藏 |
| DELETE | `/api/favorites/{articleId}` | 取消收藏 |
| GET | `/api/favorites/check/{articleId}` | 检查是否已收藏 |

### 浏览历史

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/history` | 历史列表 |
| POST | `/api/history/{articleId}` | 记录浏览 |

### AI 对话

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/ai/chat` | 发送消息（支持多轮对话） |

## 数据模型

### User（用户）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| username | String | 用户名，唯一 |
| passwordHash | String | BCrypt 加密密码 |
| role | Enum | ADMIN / USER |
| createdAt | LocalDateTime | 创建时间 |

### Article（文章）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| title | String | 标题 |
| summary | String | 摘要 |
| content | String | Markdown 正文 |
| tags | String | 标签（逗号分隔） |
| author | User | 作者（多对一） |
| viewCount | Long | 浏览量 |
| createdAt / updatedAt | LocalDateTime | 创建/更新时间 |

### Favorite（收藏）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| user | User | 用户 |
| article | Article | 文章 |
| createdAt | LocalDateTime | 收藏时间 |

### ViewHistory（浏览历史）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| user | User | 用户 |
| article | Article | 文章 |
| viewedAt | LocalDateTime | 浏览时间 |

## 项目截图

> 欢迎补充截图展示项目界面

## License

[MIT](LICENSE)
