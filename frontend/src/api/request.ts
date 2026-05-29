import axios from 'axios'
import { ElMessage } from 'element-plus'
import type { ApiResponse } from '@/types'

export const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 60000,
})

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use(
  (response) => response,
  (error) => {
    const message = error.response?.data?.message || '请求失败，请检查后端服务'
    ElMessage.error(message)
    return Promise.reject(error)
  },
)

export async function apiGet<T>(url: string, params?: unknown): Promise<T> {
  const response = await request.get<ApiResponse<T>>(url, { params })
  return response.data.data
}

export async function apiPost<T>(url: string, data?: unknown): Promise<T> {
  const response = await request.post<ApiResponse<T>>(url, data)
  return response.data.data
}

export async function apiPut<T>(url: string, data?: unknown): Promise<T> {
  const response = await request.put<ApiResponse<T>>(url, data)
  return response.data.data
}

export async function apiDelete<T>(url: string): Promise<T> {
  const response = await request.delete<ApiResponse<T>>(url)
  return response.data.data
}
