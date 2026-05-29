<template>
  <section>
    <div class="page-title-row">
      <div>
        <h1>文章</h1>
        <p>按发布时间倒序浏览博客主人发布的内容。</p>
      </div>
      <el-input v-model="keyword" class="search-input" placeholder="搜索文章" clearable @keyup.enter="loadArticles">
        <template #append>
          <el-button @click="loadArticles">搜索</el-button>
        </template>
      </el-input>
    </div>

    <el-skeleton v-if="loading" :rows="5" animated />
    <el-empty v-else-if="articles.length === 0" description="暂无文章" />
    <div v-else class="article-list">
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" />
    </div>

    <div class="pagination" v-if="total > pageSize">
      <el-pagination layout="prev, pager, next" :total="total" :page-size="pageSize" @current-change="changePage" />
    </div>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import ArticleCard from '@/components/ArticleCard.vue'
import { getArticles } from '@/api/article'
import type { Article } from '@/types'

const articles = ref<Article[]>([])
const keyword = ref('')
const loading = ref(false)
const page = ref(0)
const pageSize = 10
const total = ref(0)

async function loadArticles() {
  loading.value = true
  try {
    const data = await getArticles({ page: page.value, size: pageSize, keyword: keyword.value })
    articles.value = data.content
    total.value = data.totalElements
  } finally {
    loading.value = false
  }
}

function changePage(current: number) {
  page.value = current - 1
  loadArticles()
}

onMounted(loadArticles)
</script>
