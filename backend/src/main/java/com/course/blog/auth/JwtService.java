package com.course.blog.auth;

import com.course.blog.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final AppProperties appProperties;

    public String generateToken(BlogUserDetails userDetails) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + appProperties.getJwtExpirationMs());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getUser().getRole().name())
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, BlogUserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && parseClaims(token).getExpiration().after(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(appProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));
    }
}
