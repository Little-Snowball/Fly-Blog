package com.course.blog.user;

import com.course.blog.article.Article;
import com.course.blog.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        ensureArticleContentColumn();
        User admin = ensureUser("admin", "admin123", UserRole.ADMIN);
        ensureUser("User1", "user111", UserRole.USER);
        ensureUser("User", "user222", UserRole.USER);

        if (articleRepository.count() == 0) {
            createArticle(admin,
                    "Java 面向对象基础",
                    "从类、对象、封装、继承和多态几个角度理解 Java 面向对象编程。",
                    "Java,OOP,课程设计",
                    "# Java 面向对象基础\n\nJava 的面向对象编程主要围绕 **类** 和 **对象** 展开。\n\n## 封装\n\n封装通过 `private` 字段和公开方法隐藏内部实现。\n\n```java\npublic class User {\n    private String name;\n\n    public String getName() {\n        return name;\n    }\n}\n```\n\n## 继承\n\n继承可以复用父类代码。\n\n## 多态\n\n多态允许父类引用指向子类对象，是接口设计的重要基础。");
            createArticle(admin,
                    "Spring Boot 博客后端设计思路",
                    "用 Controller、Service、Repository 分层实现清晰的后端结构。",
                    "Spring Boot,JPA,后端",
                    "# Spring Boot 博客后端设计思路\n\n本项目后端采用三层结构：\n\n- Controller：处理 HTTP 请求\n- Service：处理业务逻辑\n- Repository：访问数据库\n\n这种结构适合课程设计展示，也方便后续继续扩展。");
            createArticle(admin,
                    "前后端分离项目如何联调",
                    "介绍 Vue 前端如何通过 REST API 调用 Spring Boot 后端。",
                    "Vue,REST,前后端分离",
                    "# 前后端分离项目如何联调\n\n前端运行在 `http://localhost:5173`，后端运行在 `http://localhost:8080`。\n\n前端通过封装后的请求工具访问 `/api` 接口。后端需要配置 CORS，允许前端本地地址访问。");
        }
    }

    private User ensureUser(String username, String password, UserRole role) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(passwordEncoder.encode(password));
            user.setRole(role);
            return userRepository.save(user);
        });
    }

    private void createArticle(User author, String title, String summary, String tags, String content) {
        Article article = new Article();
        article.setAuthor(author);
        article.setTitle(title);
        article.setSummary(summary);
        article.setTags(tags);
        article.setContent(content);
        articleRepository.save(article);
    }

    private void ensureArticleContentColumn() {
        try {
            jdbcTemplate.execute("alter table articles modify column content longtext not null");
        } catch (Exception ignored) {
            // The table does not exist yet on a fresh database; Hibernate will create it.
        }
    }
}
