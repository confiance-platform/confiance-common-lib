package com.confiance.common.security;

import com.confiance.common.enums.Permission;
import com.confiance.common.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        try {
            return getAllClaimsFromToken(token).getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("Error checking token expiration: {}", e.getMessage());
            return true;
        }
    }

    public boolean validateToken(String token) {
        try {
            getAllClaimsFromToken(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public JwtUser extractJwtUser(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);

            Long userId = claims.get("userId", Long.class);
            String email = claims.get("email", String.class);
            String sessionId = claims.get("sessionId", String.class);

            @SuppressWarnings("unchecked")
            List<String> rolesList = claims.get("roles", List.class);
            Set<UserRole> roles = rolesList != null
                ? rolesList.stream()
                    .map(UserRole::valueOf)
                    .collect(Collectors.toSet())
                : Set.of();

            @SuppressWarnings("unchecked")
            List<String> permissionsList = claims.get("permissions", List.class);
            Set<Permission> permissions = permissionsList != null
                ? permissionsList.stream()
                    .map(Permission::valueOf)
                    .collect(Collectors.toSet())
                : Set.of();

            return JwtUser.builder()
                    .userId(userId)
                    .email(email)
                    .roles(roles)
                    .permissions(permissions)
                    .sessionId(sessionId)
                    .build();
        } catch (Exception e) {
            log.error("Error extracting user from JWT: {}", e.getMessage());
            return null;
        }
    }

    public Long extractUserId(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims.get("userId", Long.class);
        } catch (Exception e) {
            log.error("Error extracting userId from JWT: {}", e.getMessage());
            return null;
        }
    }

    public String extractEmail(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims.get("email", String.class);
        } catch (Exception e) {
            log.error("Error extracting email from JWT: {}", e.getMessage());
            return null;
        }
    }
}