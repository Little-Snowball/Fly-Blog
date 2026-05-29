import { apiDelete, apiGet, apiPost } from './request'
import type { Article } from '@/types'

export function getFavorites() {
  return apiGet<Article[]>('/favorites')
}

export function addFavorite(articleId: number) {
  return apiPost<void>(`/favorites/${articleId}`)
}

export function removeFavorite(articleId: number) {
  return apiDelete<void>(`/favorites/${articleId}`)
}

export function checkFavorite(articleId: number) {
  return apiGet<boolean>(`/favorites/check/${articleId}`)
}
