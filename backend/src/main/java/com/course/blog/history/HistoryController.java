package com.course.blog.history;

import com.course.blog.article.dto.ArticleResponse;
import com.course.blog.auth.BlogUserDetails;
import com.course.blog.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping
    public ApiResponse<List<ArticleResponse>> list(@AuthenticationPrincipal BlogUserDetails userDetails) {
        return ApiResponse.ok(historyService.list(userDetails));
    }

    @PostMapping("/{articleId}")
    public ApiResponse<Void> record(@PathVariable Long articleId, @AuthenticationPrincipal BlogUserDetails userDetails) {
        historyService.record(articleId, userDetails);
        return ApiResponse.ok(null);
    }
}
