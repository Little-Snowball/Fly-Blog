<template>
  <section class="placeholder-page">
    <el-empty description="注册功能暂未开放">
      <el-button type="primary" @click="router.push('/login')">返回登录</el-button>
    </el-empty>
  </section>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
</script>
