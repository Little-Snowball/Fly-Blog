package com.course.blog.history;

import com.course.blog.article.Article;
import com.course.blog.article.ArticleService;
import com.course.blog.article.dto.ArticleResponse;
import com.course.blog.auth.BlogUserDetails;
import com.course.blog.user.User;
import com.course.blog.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final ViewHistoryRepository historyRepository;
    private final ArticleService articleService;
    private final UserRepository userRepository;

    public List<ArticleResponse> list(BlogUserDetails userDetails) {
        return historyRepository.findByUserIdOrderByViewedAtDesc(userDetails.getUser().getId()).stream()
                .map(history -> toArticleResponse(history.getArticle()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void record(Long articleId, BlogUserDetails userDetails) {
        ViewHistory history = historyRepository.findByUserIdAndArticleId(userDetails.getUser().getId(), articleId).orElseGet(ViewHistory::new);
        if (history.getId() == null) {
            User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
            history.setUser(user);
            history.setArticle(articleService.getArticle(articleId));
        }
        history.setViewedAt(LocalDateTime.now());
        historyRepository.save(history);
    }

    private ArticleResponse toArticleResponse(Article article) {
        return new ArticleResponse(article.getId(), article.getTitle(), article.getSummary(), article.getContent(), article.getTags(),
                article.getAuthor().getUsername(), article.getViewCount(), article.getCreatedAt(), article.getUpdatedAt());
    }
}
