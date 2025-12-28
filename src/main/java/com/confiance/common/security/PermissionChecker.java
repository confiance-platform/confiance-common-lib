package com.confiance.common.security;

import com.confiance.common.enums.Permission;
import com.confiance.common.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Utility class for checking user permissions
 */
@Component
@Slf4j
public class PermissionChecker {

    /**
     * Check if user has a specific permission
     */
    public boolean hasPermission(JwtUser jwtUser, Permission permission) {
        if (jwtUser == null || jwtUser.getPermissions() == null) {
            log.warn("User or permissions is null");
            return false;
        }
        return jwtUser.getPermissions().contains(permission);
    }

    /**
     * Check if user has any of the specified permissions
     */
    public boolean hasAnyPermission(JwtUser jwtUser, Permission... permissions) {
        if (jwtUser == null || jwtUser.getPermissions() == null) {
            log.warn("User or permissions is null");
            return false;
        }

        Set<Permission> userPermissions = jwtUser.getPermissions();
        for (Permission permission : permissions) {
            if (userPermissions.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has all of the specified permissions
     */
    public boolean hasAllPermissions(JwtUser jwtUser, Permission... permissions) {
        if (jwtUser == null || jwtUser.getPermissions() == null) {
            log.warn("User or permissions is null");
            return false;
        }

        Set<Permission> userPermissions = jwtUser.getPermissions();
        for (Permission permission : permissions) {
            if (!userPermissions.contains(permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if user has a specific role
     */
    public boolean hasRole(JwtUser jwtUser, UserRole role) {
        if (jwtUser == null || jwtUser.getRoles() == null) {
            log.warn("User or roles is null");
            return false;
        }
        return jwtUser.getRoles().contains(role);
    }

    /**
     * Check if user has any of the specified roles
     */
    public boolean hasAnyRole(JwtUser jwtUser, UserRole... roles) {
        if (jwtUser == null || jwtUser.getRoles() == null) {
            log.warn("User or roles is null");
            return false;
        }

        Set<UserRole> userRoles = jwtUser.getRoles();
        for (UserRole role : roles) {
            if (userRoles.contains(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user is admin (ADMIN or SUPER_ADMIN)
     */
    public boolean isAdmin(JwtUser jwtUser) {
        return hasAnyRole(jwtUser, UserRole.ROLE_ADMIN, UserRole.ROLE_SUPER_ADMIN);
    }

    /**
     * Check if user is super admin
     */
    public boolean isSuperAdmin(JwtUser jwtUser) {
        return hasRole(jwtUser, UserRole.ROLE_SUPER_ADMIN);
    }

    /**
     * Check if user can manage permissions (grant/revoke)
     */
    public boolean canManagePermissions(JwtUser jwtUser) {
        return hasAllPermissions(jwtUser, Permission.PERMISSION_GRANT, Permission.PERMISSION_REVOKE);
    }

    /**
     * Check if user can view permissions
     */
    public boolean canViewPermissions(JwtUser jwtUser) {
        return hasPermission(jwtUser, Permission.PERMISSION_VIEW);
    }

    /**
     * Check if user can access admin panel
     */
    public boolean canAccessAdminPanel(JwtUser jwtUser) {
        return hasPermission(jwtUser, Permission.ADMIN_PANEL_ACCESS);
    }
}