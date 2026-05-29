package com.course.blog.ai.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class AiChatRequest {
    @NotBlank(message = "消息不能为空")
    private String message;

    private List<ChatMessage> history = new ArrayList<ChatMessage>();
}
