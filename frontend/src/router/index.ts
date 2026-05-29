import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '@/layouts/MainLayout.vue'
import ArticleListPage from '@/pages/ArticleListPage.vue'
import ArticleDetailPage from '@/pages/ArticleDetailPage.vue'
import AiChatPage from '@/pages/AiChatPage.vue'
import HistoryPage from '@/pages/HistoryPage.vue'
import FavoritesPage from '@/pages/FavoritesPage.vue'
import LoginPage from '@/pages/LoginPage.vue'
import RegisterPlaceholderPage from '@/pages/RegisterPlaceholderPage.vue'
import ArticleEditorPage from '@/pages/ArticleEditorPage.vue'
import { useAuthStore } from '@/stores/authStore'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: MainLayout,
      children: [
        { path: '', redirect: '/articles' },
        { path: 'articles', component: ArticleListPage },
        { path: 'articles/:id', component: ArticleDetailPage },
        { path: 'ai-chat', component: AiChatPage, meta: { requiresAuth: true } },
        { path: 'history', component: HistoryPage, meta: { requiresAuth: true } },
        { path: 'favorites', component: FavoritesPage, meta: { requiresAuth: true } },
        { path: 'login', component: LoginPage },
        { path: 'register', component: RegisterPlaceholderPage },
        { path: 'admin/articles/new', component: ArticleEditorPage, meta: { requiresAuth: true, requiresAdmin: true } },
        { path: 'admin/articles/:id/edit', component: ArticleEditorPage, meta: { requiresAuth: true, requiresAdmin: true } },
      ],
    },
  ],
})

router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth && !auth.isLoggedIn) return '/login'
  if (to.meta.requiresAdmin && !auth.isAdmin) return '/articles'
})

export default router
