package com.confiance.common.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lightweight notification publisher.
 *
 * Any service can autowire {@link Notifier} and call
 * {@code notifier.notifyUser(userId, title, message, type)} to publish an
 * activity notification that shows up in the bell icon / notifications list.
 *
 * Implementation: POST {@code http://notification-service/api/v1/notifications}
 * resolved via Eureka + Spring Cloud LoadBalancer. Failures are logged and
 * swallowed — notifications are non-critical; callers must not break on send
 * failure.
 */
@Component
@Slf4j
public class Notifier {

    private final RestTemplate loadBalancedRestTemplate;

    public Notifier(@Autowired(required = false)
                    @Qualifier("notifierLoadBalancedRestTemplate")
                    RestTemplate loadBalancedRestTemplate) {
        this.loadBalancedRestTemplate = loadBalancedRestTemplate;
    }

    public void notifyUser(Long userId, String title, String message, String type) {
        notifyUser(userId, title, message, type, null, null);
    }

    public void notifyUser(Long userId, String title, String message,
                           String type, String actionUrl, String icon) {
        if (loadBalancedRestTemplate == null || userId == null) return;
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("userId", userId);
            body.put("title", title);
            body.put("message", message);
            body.put("type", type);
            if (actionUrl != null) body.put("actionUrl", actionUrl);
            if (icon != null)      body.put("icon", icon);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> req = new HttpEntity<>(body, headers);

            loadBalancedRestTemplate.postForEntity(
                "http://notification-service/api/v1/notifications", req, String.class);
        } catch (Exception e) {
            log.warn("Failed to publish notification to user {}: {}", userId, e.getMessage());
        }
    }

    public void notifyUsers(List<Long> userIds, String title, String message, String type) {
        notifyUsers(userIds, title, message, type, null, null);
    }

    public void notifyUsers(List<Long> userIds, String title, String message,
                            String type, String actionUrl, String icon) {
        if (userIds == null) return;
        for (Long id : userIds) {
            notifyUser(id, title, message, type, actionUrl, icon);
        }
    }

    @Configuration
    public static class LoadBalancedRestTemplateConfig {
        @Bean(name = "notifierLoadBalancedRestTemplate")
        @LoadBalanced
        public RestTemplate loadBalancedRestTemplate() {
            return new RestTemplate();
        }
    }
}
