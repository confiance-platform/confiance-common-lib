package com.confiance.common.security;

import com.confiance.common.enums.Permission;
import com.confiance.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUser {
    private Long userId;
    private String email;
    private Set<UserRole> roles;
    private Set<Permission> permissions;
    private String sessionId;
}