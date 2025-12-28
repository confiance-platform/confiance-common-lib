package com.confiance.common.enums;

public enum UserRole {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin"),
    ROLE_SUPER_ADMIN("Super Admin");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isAdmin() {
        return this == ROLE_ADMIN || this == ROLE_SUPER_ADMIN;
    }

    public boolean isSuperAdmin() {
        return this == ROLE_SUPER_ADMIN;
    }
}