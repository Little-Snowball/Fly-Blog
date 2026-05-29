package com.course.blog.favorite;

import com.course.blog.article.Article;
import com.course.blog.article.ArticleService;
import com.course.blog.article.dto.ArticleResponse;
import com.course.blog.auth.BlogUserDetails;
import com.course.blog.user.User;
import com.course.blog.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ArticleService articleService;
    private final UserRepository userRepository;

    public List<ArticleResponse> list(BlogUserDetails userDetails) {
        return favoriteRepository.findByUserIdOrderByCreatedAtDesc(userDetails.getUser().getId()).stream()
                .map(favorite -> toArticleResponse(favorite.getArticle()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void add(Long articleId, BlogUserDetails userDetails) {
        if (favoriteRepository.existsByUserIdAndArticleId(userDetails.getUser().getId(), articleId)) {
            return;
        }
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setArticle(articleService.getArticle(articleId));
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void remove(Long articleId, BlogUserDetails userDetails) {
        favoriteRepository.findByUserIdAndArticleId(userDetails.getUser().getId(), articleId).ifPresent(favoriteRepository::delete);
    }

    public boolean check(Long articleId, BlogUserDetails userDetails) {
        return favoriteRepository.existsByUserIdAndArticleId(userDetails.getUser().getId(), articleId);
    }

    private ArticleResponse toArticleResponse(Article article) {
        return new ArticleResponse(article.getId(), article.getTitle(), article.getSummary(), article.getContent(), article.getTags(),
                article.getAuthor().getUsername(), article.getViewCount(), article.getCreatedAt(), article.getUpdatedAt());
    }
}
