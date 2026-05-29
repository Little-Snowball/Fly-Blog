package com.course.blog.auth.dto;

import com.course.blog.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentUserResponse {
    private Long userId;
    private String username;
    private UserRole role;
}
