<template>
  <section class="auth-page">
    <el-card class="auth-card" shadow="never">
      <h1>登录</h1>
      <el-form label-position="top" @submit.prevent="submit">
        <el-form-item label="账号">
          <el-input v-model="username" placeholder="admin / User1 / User" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="password" type="password" show-password placeholder="请输入密码" @keyup.enter="submit" />
        </el-form-item>
        <el-button type="primary" class="full-button" :loading="loading" @click="submit">登录</el-button>
        <el-button class="full-button ghost-button" @click="router.push('/register')">注册账号</el-button>
      </el-form>
      <div class="account-hints">
        <span>admin / admin123</span>
        <span>User1 / user111</span>
        <span>User / user222</span>
      </div>
    </el-card>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/authStore'

const router = useRouter()
const auth = useAuthStore()
const username = ref('admin')
const password = ref('')
const loading = ref(false)

async function submit() {
  loading.value = true
  try {
    await auth.login(username.value, password.value)
    ElMessage.success('登录成功')
    router.push('/articles')
  } finally {
    loading.value = false
  }
}
</script>
