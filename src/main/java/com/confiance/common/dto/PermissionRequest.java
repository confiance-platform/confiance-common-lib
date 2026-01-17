package com.confiance.common.dto;

import com.confiance.common.enums.Permission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "Permissions cannot be empty")
    private Set<Permission> permissions;
}