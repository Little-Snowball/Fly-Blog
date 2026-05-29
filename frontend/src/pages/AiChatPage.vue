<template>
  <section class="chat-page">
    <div class="page-title-row">
      <div>
        <h1>AIChat</h1>
        <p>发送问题后等待模型完整返回，结果会一次性呈现。</p>
      </div>
    </div>

    <div class="chat-panel">
      <div class="chat-messages">
        <div v-if="messages.length === 0" class="chat-empty">可以询问 Java、Spring Boot、课程设计相关问题。</div>
        <div v-for="(message, index) in messages" :key="index" :class="['chat-message', message.role]">
          <div class="message-role">{{ message.role === 'user' ? '我' : 'AI' }}</div>
          <div class="message-content">{{ message.content }}</div>
        </div>
      </div>
      <div class="chat-input-row">
        <el-input v-model="input" type="textarea" :rows="3" resize="none" placeholder="请输入你的问题" :disabled="loading" @keydown.ctrl.enter="send" />
        <el-button type="primary" :loading="loading" @click="send">发送</el-button>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { chat } from '@/api/ai'
import type { ChatMessage } from '@/types'

const messages = ref<ChatMessage[]>([])
const input = ref('')
const loading = ref(false)

async function send() {
  const text = input.value.trim()
  if (!text || loading.value) return
  input.value = ''
  messages.value.push({ role: 'user', content: text })
  messages.value.push({ role: 'assistant', content: '正在思考...' })
  loading.value = true
  try {
    const history = messages.value.slice(0, -2)
    const result = await chat(text, history)
    messages.value[messages.value.length - 1] = { role: 'assistant', content: result.content }
  } catch {
    messages.value[messages.value.length - 1] = { role: 'assistant', content: '请求失败，请检查 API Key、模型名称或后端服务。' }
  } finally {
    loading.value = false
  }
}
</script>
