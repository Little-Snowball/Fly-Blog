<template>
  <section class="editor-page">
    <div class="page-title-row">
      <div>
        <h1>{{ isEdit ? '编辑文章' : '发布文章' }}</h1>
        <p>正文支持 Markdown，可用于课程设计截图展示。</p>
      </div>
    </div>

    <el-form label-position="top" class="editor-form">
      <el-form-item label="标题">
        <el-input v-model="form.title" maxlength="120" show-word-limit />
      </el-form-item>
      <el-form-item label="摘要">
        <el-input v-model="form.summary" type="textarea" :rows="3" maxlength="500" show-word-limit />
      </el-form-item>
      <el-form-item label="标签">
        <el-input v-model="form.tags" placeholder="Java,Spring Boot,课程设计" />
      </el-form-item>
      <el-form-item label="正文 Markdown">
        <el-input v-model="form.content" type="textarea" :rows="16" resize="vertical" />
      </el-form-item>
      <div class="editor-actions">
        <el-button @click="router.back()">取消</el-button>
        <el-button type="primary" :loading="saving" @click="save">保存</el-button>
      </div>
    </el-form>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createArticle, getArticle, updateArticle } from '@/api/article'

const route = useRoute()
const router = useRouter()
const saving = ref(false)
const isEdit = computed(() => Boolean(route.params.id))
const form = reactive({
  title: '',
  summary: '',
  tags: '',
  content: '# 新文章\n\n请在这里编写 Markdown 正文。',
})

async function load() {
  if (!isEdit.value) return
  const article = await getArticle(Number(route.params.id))
  form.title = article.title
  form.summary = article.summary
  form.tags = article.tags
  form.content = article.content
}

async function save() {
  saving.value = true
  try {
    const article = isEdit.value
      ? await updateArticle(Number(route.params.id), form)
      : await createArticle(form)
    ElMessage.success('保存成功')
    router.push(`/articles/${article.id}`)
  } finally {
    saving.value = false
  }
}

onMounted(load)
</script>
