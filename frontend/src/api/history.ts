import { apiGet, apiPost } from './request'
import type { Article } from '@/types'

export function getHistory() {
  return apiGet<Article[]>('/history')
}

export function recordHistory(articleId: number) {
  return apiPost<void>(`/history/${articleId}`)
}
