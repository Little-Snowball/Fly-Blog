package com.course.blog.auth;

import com.course.blog.auth.dto.CurrentUserResponse;
import com.course.blog.auth.dto.LoginRequest;
import com.course.blog.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        BlogUserDetails userDetails = (BlogUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return new LoginResponse(token, userDetails.getUser().getId(), userDetails.getUsername(), userDetails.getUser().getRole());
    }

    public CurrentUserResponse currentUser(BlogUserDetails userDetails) {
        return new CurrentUserResponse(userDetails.getUser().getId(), userDetails.getUsername(), userDetails.getUser().getRole());
    }
}
