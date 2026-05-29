import { apiDelete, apiGet, apiPost, apiPut } from './request'
import type { Article, ArticleRequest, Page } from '@/types'

export function getArticles(params: { page: number; size: number; keyword?: string }) {
  return apiGet<Page<Article>>('/articles', params)
}

export function getArticle(id: number) {
  return apiGet<Article>(`/articles/${id}`)
}

export function createArticle(data: ArticleRequest) {
  return apiPost<Article>('/articles', data)
}

export function updateArticle(id: number, data: ArticleRequest) {
  return apiPut<Article>(`/articles/${id}`, data)
}

export function deleteArticle(id: number) {
  return apiDelete<void>(`/articles/${id}`)
}
