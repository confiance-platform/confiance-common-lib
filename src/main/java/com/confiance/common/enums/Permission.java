package com.confiance.common.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Permission {
    // User Permissions
    USER_READ("Read user information"),
    USER_WRITE("Create and update users"),
    USER_DELETE("Delete users"),

    // Investment Permissions
    INVESTMENT_READ("Read investment information"),
    INVESTMENT_WRITE("Create and update investments"),
    INVESTMENT_DELETE("Delete investments"),

    // Transaction Permissions
    TRANSACTION_READ("Read transaction information"),
    TRANSACTION_WRITE("Create and update transactions"),

    // Portfolio Permissions
    PORTFOLIO_READ("Read portfolio information"),
    PORTFOLIO_WRITE("Create and update portfolio"),

    // Admin Permissions
    ADMIN_PANEL_ACCESS("Access admin panel"),
    PERMISSION_GRANT("Grant permissions to users"),
    PERMISSION_REVOKE("Revoke permissions from users"),
    PERMISSION_VIEW("View user permissions");

    private final String description;

    Permission(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get all available permissions
     */
    public static Set<Permission> getAllPermissions() {
        return new HashSet<>(Arrays.asList(Permission.values()));
    }

    /**
     * Get default permissions for SUPER_ADMIN role
     * SUPER_ADMIN has all permissions
     */
    public static Set<Permission> getDefaultSuperAdminPermissions() {
        return getAllPermissions();
    }

    /**
     * Get default permissions for ADMIN role
     * ADMIN has all permissions except PERMISSION_GRANT and PERMISSION_REVOKE
     */
    public static Set<Permission> getDefaultAdminPermissions() {
        Set<Permission> permissions = new HashSet<>(Arrays.asList(
            // User permissions
            USER_READ,
            USER_WRITE,
            USER_DELETE,

            // Investment permissions
            INVESTMENT_READ,
            INVESTMENT_WRITE,
            INVESTMENT_DELETE,

            // Transaction permissions
            TRANSACTION_READ,
            TRANSACTION_WRITE,

            // Portfolio permissions
            PORTFOLIO_READ,
            PORTFOLIO_WRITE,

            // Admin permissions (limited)
            ADMIN_PANEL_ACCESS,
            PERMISSION_VIEW
        ));
        return permissions;
    }

    /**
     * Get default permissions for USER role
     * USER has only read permissions for their own resources
     */
    public static Set<Permission> getDefaultUserPermissions() {
        return new HashSet<>(Arrays.asList(
            USER_READ,
            INVESTMENT_READ,
            TRANSACTION_READ,
            PORTFOLIO_READ
        ));
    }

    /**
     * Get permissions based on role
     */
    public static Set<Permission> getPermissionsByRole(UserRole role) {
        return switch (role) {
            case ROLE_SUPER_ADMIN -> getDefaultSuperAdminPermissions();
            case ROLE_ADMIN -> getDefaultAdminPermissions();
            case ROLE_USER -> getDefaultUserPermissions();
        };
    }
}