<template>
  <section>
    <div class="page-title-row">
      <div>
        <h1>收藏夹</h1>
        <p>这里保存你收藏过的文章。</p>
      </div>
    </div>
    <el-skeleton v-if="loading" :rows="4" animated />
    <el-empty v-else-if="articles.length === 0" description="暂无收藏" />
    <div v-else class="article-list">
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
    </div>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import ArticleCard from '@/components/ArticleCard.vue'
import { getFavorites } from '@/api/favorite'
import type { Article } from '@/types'

const articles = ref<Article[]>([])
const loading = ref(false)

async function load() {
  loading.value = true
  try {
    articles.value = await getFavorites()
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>
