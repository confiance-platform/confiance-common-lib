package com.confiance.common.versioning;

import com.confiance.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/version")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "confiance.versioning.enabled", havingValue = "true", matchIfMissing = true)
public class VersionController {

    private final VersioningService versioningService;

    @Value("${spring.application.name:unknown-service}")
    private String serviceName;

    @GetMapping
    public ApiResponse<Map<String, Object>> getCurrentVersion() {
        ServiceVersion current = versioningService.getCurrentVersion();
        Map<String, Object> versionInfo = new HashMap<>();

        if (current != null) {
            versionInfo.put("serviceName", current.getServiceName());
            versionInfo.put("version", current.getFullVersion());
            versionInfo.put("majorVersion", current.getMajorVersion());
            versionInfo.put("minorVersion", current.getMinorVersion());
            versionInfo.put("buildNumber", current.getBuildNumber());
            versionInfo.put("gitCommit", current.getGitCommit());
            versionInfo.put("gitBranch", current.getGitBranch());
            versionInfo.put("environment", current.getEnvironment());
            versionInfo.put("hostName", current.getHostName());
            versionInfo.put("deployedAt", current.getDeployedAt());
            versionInfo.put("buildTimestamp", current.getBuildTimestamp());
        } else {
            versionInfo.put("serviceName", serviceName);
            versionInfo.put("version", "unknown");
            versionInfo.put("message", "Version not initialized");
        }

        return ApiResponse.success(versionInfo);
    }

    @GetMapping("/history")
    public ApiResponse<List<ServiceVersion>> getVersionHistory() {
        List<ServiceVersion> history = versioningService.getVersionHistory(serviceName);
        return ApiResponse.success(history);
    }
}
