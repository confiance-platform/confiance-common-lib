package com.confiance.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final JwtUser jwtUser;
    private final String token;

    public JwtAuthenticationToken(String token, JwtUser jwtUser) {
        super(jwtUser != null ? mapRolesToAuthorities(jwtUser) : null);
        this.token = token;
        this.jwtUser = jwtUser;
        setAuthenticated(jwtUser != null);
    }

    private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(JwtUser jwtUser) {
        return jwtUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return jwtUser;
    }

    public JwtUser getJwtUser() {
        return jwtUser;
    }

    public Long getUserId() {
        return jwtUser != null ? jwtUser.getUserId() : null;
    }

    public String getEmail() {
        return jwtUser != null ? jwtUser.getEmail() : null;
    }

    public String getSessionId() {
        return jwtUser != null ? jwtUser.getSessionId() : null;
    }
}