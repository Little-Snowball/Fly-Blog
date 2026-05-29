<template>
  <section>
    <div class="page-title-row">
      <div>
        <h1>浏览历史</h1>
        <p>按最近访问时间查看你读过的文章。</p>
      </div>
    </div>
    <el-skeleton v-if="loading" :rows="4" animated />
    <el-empty v-else-if="articles.length === 0" description="暂无浏览历史" />
    <div v-else class="article-list">
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
    </div>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import ArticleCard from '@/components/ArticleCard.vue'
import { getHistory } from '@/api/history'
import type { Article } from '@/types'

const articles = ref<Article[]>([])
const loading = ref(false)

async function load() {
  loading.value = true
  try {
    articles.value = await getHistory()
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>
