export type UserRole = 'ADMIN' | 'USER'

export interface ApiResponse<T> {
  success: boolean
  message: string
  data: T
}

export interface Page<T> {
  content: T[]
  totalElements: number
  totalPages: number
  number: number
  size: number
}

export interface UserInfo {
  userId: number
  username: string
  role: UserRole
}

export interface LoginResponse extends UserInfo {
  token: string
}

export interface Article {
  id: number
  title: string
  summary: string
  content: string
  tags: string
  authorName: string
  viewCount: number
  createdAt: string
  updatedAt: string
}

export interface ArticleRequest {
  title: string
  summary: string
  content: string
  tags: string
}

export interface ChatMessage {
  role: 'user' | 'assistant'
  content: string
}
