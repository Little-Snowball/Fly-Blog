package com.course.blog.article.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class ArticleRequest {
    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "摘要不能为空")
    private String summary;

    @NotBlank(message = "正文不能为空")
    private String content;

    private String tags;
}
