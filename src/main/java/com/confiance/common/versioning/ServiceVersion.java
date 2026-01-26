package com.confiance.common.versioning;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_version")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "version", nullable = false, length = 20)
    private String version;

    @Column(name = "major_version", nullable = false)
    private Integer majorVersion;

    @Column(name = "minor_version", nullable = false)
    private Integer minorVersion;

    @Column(name = "build_number", nullable = false)
    private Integer buildNumber;

    @Column(name = "git_commit", length = 50)
    private String gitCommit;

    @Column(name = "git_branch", length = 100)
    private String gitBranch;

    @Column(name = "build_timestamp", nullable = false)
    private LocalDateTime buildTimestamp;

    @Column(name = "deployed_at", nullable = false)
    private LocalDateTime deployedAt;

    @Column(name = "environment", length = 50)
    private String environment;

    @Column(name = "host_name", length = 255)
    private String hostName;

    @Column(name = "notes", length = 500)
    private String notes;

    @PrePersist
    protected void onCreate() {
        if (deployedAt == null) {
            deployedAt = LocalDateTime.now();
        }
        if (buildTimestamp == null) {
            buildTimestamp = LocalDateTime.now();
        }
    }

    public String getFullVersion() {
        return String.format("%d.%d.%d", majorVersion, minorVersion, buildNumber);
    }
}
