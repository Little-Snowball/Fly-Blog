package com.course.blog.ai;

import com.course.blog.ai.dto.AiChatRequest;
import com.course.blog.ai.dto.AiChatResponse;
import com.course.blog.ai.dto.ChatMessage;
import com.course.blog.config.OpenAiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AiChatService {
    private final OpenAiProperties openAiProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public AiChatResponse chat(AiChatRequest request) {
        if (!StringUtils.hasText(openAiProperties.getApiKey())) {
            throw new IllegalArgumentException("请先配置 OPENAI_API_KEY 环境变量");
        }

        List<Map<String, String>> messages = new ArrayList<Map<String, String>>();
        messages.add(message("system", "你是一个面向 Java 程序设计课程设计博客的助手，回答应清晰、实用、适合学生理解。"));
        if (request.getHistory() != null) {
            for (ChatMessage item : request.getHistory()) {
                if (StringUtils.hasText(item.getRole()) && StringUtils.hasText(item.getContent())) {
                    messages.add(message(item.getRole(), item.getContent()));
                }
            }
        }
        messages.add(message("user", request.getMessage()));

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("model", openAiProperties.getModel());
        body.put("messages", messages);
        body.put("stream", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiProperties.getApiKey());

        ResponseEntity<Map> response = restTemplate.exchange(
                openAiProperties.getBaseUrl() + "/chat/completions",
                HttpMethod.POST,
                new HttpEntity<Map<String, Object>>(body, headers),
                Map.class);

        return new AiChatResponse(extractContent(response.getBody()));
    }

    private Map<String, String> message(String role, String content) {
        Map<String, String> message = new HashMap<String, String>();
        message.put("role", role);
        message.put("content", content);
        return message;
    }

    private String extractContent(Map body) {
        if (body == null || !(body.get("choices") instanceof List)) {
            throw new IllegalArgumentException("AI 接口返回格式异常");
        }
        List choices = (List) body.get("choices");
        if (choices.isEmpty() || !(choices.get(0) instanceof Map)) {
            throw new IllegalArgumentException("AI 接口没有返回内容");
        }
        Map first = (Map) choices.get(0);
        Object message = first.get("message");
        if (!(message instanceof Map)) {
            throw new IllegalArgumentException("AI 接口返回消息格式异常");
        }
        Object content = ((Map) message).get("content");
        return content == null ? "" : String.valueOf(content);
    }
}
