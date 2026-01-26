package com.confiance.common.versioning;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VersioningInitializer {

    private final VersioningService versioningService;

    @Value("${spring.application.name:unknown-service}")
    private String serviceName;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            log.info("Initializing versioning for service: {}", serviceName);
            versioningService.incrementAndSaveVersion(serviceName, activeProfile);
        } catch (Exception e) {
            log.warn("Failed to initialize versioning: {}. Service will continue without version tracking.", e.getMessage());
        }
    }
}
