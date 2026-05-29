<template>
  <article class="article-card" @click="router.push(`/articles/${article.id}`)">
    <div class="card-main">
      <h2>{{ article.title }}</h2>
      <p>{{ article.summary }}</p>
      <div class="meta">
        <span>{{ article.authorName }}</span>
        <span>{{ formatDate(article.createdAt) }}</span>
        <span>浏览 {{ article.viewCount }}</span>
      </div>
    </div>
    <div class="tags">
      <el-tag v-for="tag in tagList" :key="tag" size="small" effect="plain">{{ tag }}</el-tag>
    </div>
  </article>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import type { Article } from '@/types'

const props = defineProps<{ article: Article }>()
const router = useRouter()

const tagList = computed(() => props.article.tags?.split(',').map((tag) => tag.trim()).filter(Boolean) || [])

function formatDate(value: string) {
  return value?.replace('T', ' ').slice(0, 16)
}
</script>
