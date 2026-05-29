package com.course.blog.favorite;

import com.course.blog.article.dto.ArticleResponse;
import com.course.blog.auth.BlogUserDetails;
import com.course.blog.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    public ApiResponse<List<ArticleResponse>> list(@AuthenticationPrincipal BlogUserDetails userDetails) {
        return ApiResponse.ok(favoriteService.list(userDetails));
    }

    @PostMapping("/{articleId}")
    public ApiResponse<Void> add(@PathVariable Long articleId, @AuthenticationPrincipal BlogUserDetails userDetails) {
        favoriteService.add(articleId, userDetails);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{articleId}")
    public ApiResponse<Void> remove(@PathVariable Long articleId, @AuthenticationPrincipal BlogUserDetails userDetails) {
        favoriteService.remove(articleId, userDetails);
        return ApiResponse.ok(null);
    }

    @GetMapping("/check/{articleId}")
    public ApiResponse<Boolean> check(@PathVariable Long articleId, @AuthenticationPrincipal BlogUserDetails userDetails) {
        return ApiResponse.ok(favoriteService.check(articleId, userDetails));
    }
}
