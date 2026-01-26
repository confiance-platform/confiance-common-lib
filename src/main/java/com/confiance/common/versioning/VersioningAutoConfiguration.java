package com.confiance.common.versioning;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@ConditionalOnClass(JpaRepository.class)
@ConditionalOnProperty(name = "confiance.versioning.enabled", havingValue = "true", matchIfMissing = true)
@EnableJpaRepositories(basePackages = "com.confiance.common.versioning")
@EntityScan(basePackages = "com.confiance.common.versioning")
@ComponentScan(basePackages = "com.confiance.common.versioning")
public class VersioningAutoConfiguration {
}
