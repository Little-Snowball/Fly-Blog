<template>
  <div class="app-shell">
    <header class="topbar">
      <RouterLink class="brand" to="/articles">课程设计博客</RouterLink>
      <nav class="nav-links">
        <RouterLink to="/articles">文章</RouterLink>
        <RouterLink to="/ai-chat">AIChat</RouterLink>
        <RouterLink to="/history">浏览历史</RouterLink>
        <RouterLink to="/favorites">收藏夹</RouterLink>
      </nav>
      <div class="user-area">
        <el-button v-if="auth.isAdmin" type="primary" size="small" @click="router.push('/admin/articles/new')">发布文章</el-button>
        <template v-if="auth.isLoggedIn">
          <span class="username">{{ auth.user?.username }}</span>
          <el-button size="small" @click="logout">退出</el-button>
        </template>
        <el-button v-else size="small" type="primary" @click="router.push('/login')">登录</el-button>
      </div>
    </header>
    <main class="page-container">
      <RouterView />
    </main>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const router = useRouter()
const auth = useAuthStore()

function logout() {
  auth.logout()
  router.push('/articles')
}
</script>
