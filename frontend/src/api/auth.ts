import { apiGet, apiPost } from './request'
import type { LoginResponse, UserInfo } from '@/types'

export function login(username: string, password: string) {
  return apiPost<LoginResponse>('/auth/login', { username, password })
}

export function getCurrentUser() {
  return apiGet<UserInfo>('/auth/me')
}
