import { apiPost } from './request'
import type { ChatMessage } from '@/types'

export function chat(message: string, history: ChatMessage[]) {
  return apiPost<{ content: string }>('/ai/chat', { message, history })
}
