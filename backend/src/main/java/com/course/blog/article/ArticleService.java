package com.course.blog.article;

import com.course.blog.article.dto.ArticleRequest;
import com.course.blog.article.dto.ArticleResponse;
import com.course.blog.auth.BlogUserDetails;
import com.course.blog.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public Page<ArticleResponse> list(String keyword, Pageable pageable) {
        return articleRepository.search(keyword, pageable).map(this::toResponse);
    }

    @Transactional
    public ArticleResponse detail(Long id) {
        Article article = getArticle(id);
        article.setViewCount(article.getViewCount() + 1);
        return toResponse(article);
    }

    @Transactional
    public ArticleResponse create(ArticleRequest request, BlogUserDetails userDetails) {
        Article article = new Article();
        fillArticle(article, request);
        article.setAuthor(userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("用户不存在")));
        return toResponse(articleRepository.save(article));
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleRequest request) {
        Article article = getArticle(id);
        fillArticle(article, request);
        return toResponse(article);
    }

    public void delete(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new IllegalArgumentException("文章不存在");
        }
        articleRepository.deleteById(id);
    }

    public Article getArticle(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("文章不存在"));
    }

    private void fillArticle(Article article, ArticleRequest request) {
        article.setTitle(request.getTitle());
        article.setSummary(request.getSummary());
        article.setContent(request.getContent());
        article.setTags(request.getTags());
    }

    private ArticleResponse toResponse(Article article) {
        return new ArticleResponse(
                article.getId(), article.getTitle(), article.getSummary(), article.getContent(), article.getTags(),
                article.getAuthor().getUsername(), article.getViewCount(), article.getCreatedAt(), article.getUpdatedAt());
    }
}
