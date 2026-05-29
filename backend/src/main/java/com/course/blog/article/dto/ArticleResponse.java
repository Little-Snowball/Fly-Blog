package com.course.blog.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private String tags;
    private String authorName;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
