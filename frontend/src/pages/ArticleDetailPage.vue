<template>
  <section v-if="article" class="article-detail">
    <div class="detail-header">
      <div>
        <h1>{{ article.title }}</h1>
        <p>{{ article.authorName }} · {{ formatDate(article.createdAt) }} · 浏览 {{ article.viewCount }}</p>
      </div>
      <div class="detail-actions">
        <el-button v-if="auth.isLoggedIn" @click="toggleFavorite">{{ favorited ? '取消收藏' : '收藏' }}</el-button>
        <el-button v-if="auth.isAdmin" type="primary" @click="router.push(`/admin/articles/${article.id}/edit`)">编辑</el-button>
        <el-button v-if="auth.isAdmin" type="danger" @click="remove">删除</el-button>
      </div>
    </div>
    <div class="tags detail-tags">
      <el-tag v-for="tag in tagList" :key="tag" effect="plain">{{ tag }}</el-tag>
    </div>
    <MarkdownViewer :content="article.content" />
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import MarkdownViewer from '@/components/MarkdownViewer.vue'
import { deleteArticle, getArticle } from '@/api/article'
import { addFavorite, checkFavorite, removeFavorite } from '@/api/favorite'
import { recordHistory } from '@/api/history'
import { useAuthStore } from '@/stores/authStore'
import type { Article } from '@/types'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()
const article = ref<Article | null>(null)
const favorited = ref(false)
const articleId = Number(route.params.id)

const tagList = computed(() => article.value?.tags?.split(',').map((tag) => tag.trim()).filter(Boolean) || [])

async function load() {
  article.value = await getArticle(articleId)
  if (auth.isLoggedIn) {
    favorited.value = await checkFavorite(articleId)
    await recordHistory(articleId)
  }
}

async function toggleFavorite() {
  if (favorited.value) {
    await removeFavorite(articleId)
    favorited.value = false
  } else {
    await addFavorite(articleId)
    favorited.value = true
  }
}

async function remove() {
  await ElMessageBox.confirm('确定删除这篇文章吗？', '删除文章')
  await deleteArticle(articleId)
  ElMessage.success('已删除')
  router.push('/articles')
}

function formatDate(value: string) {
  return value?.replace('T', ' ').slice(0, 16)
}

onMounted(load)
</script>
