package com.confiance.common.versioning;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VersioningService {

    private final ServiceVersionRepository versionRepository;

    private ServiceVersion currentVersion;

    @Transactional
    public ServiceVersion incrementAndSaveVersion(String serviceName, String environment) {
        Optional<ServiceVersion> latestVersion = versionRepository.findTopByServiceNameOrderByIdDesc(serviceName);

        int majorVersion = 1;
        int minorVersion = 0;
        int buildNumber = 0;

        if (latestVersion.isPresent()) {
            ServiceVersion prev = latestVersion.get();
            majorVersion = prev.getMajorVersion();
            minorVersion = prev.getMinorVersion();
            buildNumber = prev.getBuildNumber() + 1;
        }

        String hostName = getHostName();
        String gitCommit = getGitCommit();
        String gitBranch = getGitBranch();

        ServiceVersion newVersion = ServiceVersion.builder()
                .serviceName(serviceName)
                .version(String.format("%d.%d.%d", majorVersion, minorVersion, buildNumber))
                .majorVersion(majorVersion)
                .minorVersion(minorVersion)
                .buildNumber(buildNumber)
                .buildTimestamp(LocalDateTime.now())
                .deployedAt(LocalDateTime.now())
                .environment(environment)
                .hostName(hostName)
                .gitCommit(gitCommit)
                .gitBranch(gitBranch)
                .notes("Auto-deployed version")
                .build();

        currentVersion = versionRepository.save(newVersion);

        log.info("╔══════════════════════════════════════════════════════════════╗");
        log.info("║                    SERVICE VERSION INFO                       ║");
        log.info("╠══════════════════════════════════════════════════════════════╣");
        log.info("║  Service Name  : {}", padRight(serviceName, 45) + "║");
        log.info("║  Version       : {}", padRight(currentVersion.getFullVersion(), 45) + "║");
        log.info("║  Build Number  : {}", padRight(String.valueOf(buildNumber), 45) + "║");
        log.info("║  Environment   : {}", padRight(environment != null ? environment : "default", 45) + "║");
        log.info("║  Host          : {}", padRight(hostName, 45) + "║");
        log.info("║  Git Commit    : {}", padRight(gitCommit != null ? gitCommit : "N/A", 45) + "║");
        log.info("║  Git Branch    : {}", padRight(gitBranch != null ? gitBranch : "N/A", 45) + "║");
        log.info("║  Deployed At   : {}", padRight(currentVersion.getDeployedAt().toString(), 45) + "║");
        log.info("╚══════════════════════════════════════════════════════════════╝");

        return currentVersion;
    }

    public ServiceVersion getCurrentVersion() {
        return currentVersion;
    }

    public Optional<ServiceVersion> getLatestVersion(String serviceName) {
        return versionRepository.findTopByServiceNameOrderByIdDesc(serviceName);
    }

    public List<ServiceVersion> getVersionHistory(String serviceName) {
        return versionRepository.findTop10ByServiceNameOrderByDeployedAtDesc(serviceName);
    }

    private String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "unknown";
        }
    }

    private String getGitCommit() {
        try {
            ProcessBuilder pb = new ProcessBuilder("git", "rev-parse", "--short", "HEAD");
            pb.redirectErrorStream(true);
            Process process = pb.start();
            String result = new String(process.getInputStream().readAllBytes()).trim();
            int exitCode = process.waitFor();
            return exitCode == 0 ? result : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String getGitBranch() {
        try {
            ProcessBuilder pb = new ProcessBuilder("git", "rev-parse", "--abbrev-ref", "HEAD");
            pb.redirectErrorStream(true);
            Process process = pb.start();
            String result = new String(process.getInputStream().readAllBytes()).trim();
            int exitCode = process.waitFor();
            return exitCode == 0 ? result : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String padRight(String s, int n) {
        if (s == null) s = "";
        return String.format("%-" + n + "s", s.length() > n ? s.substring(0, n) : s);
    }
}
