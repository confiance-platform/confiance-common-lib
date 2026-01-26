package com.confiance.common.versioning;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceVersionRepository extends JpaRepository<ServiceVersion, Long> {

    Optional<ServiceVersion> findTopByServiceNameOrderByIdDesc(String serviceName);

    @Query("SELECT sv FROM ServiceVersion sv WHERE sv.serviceName = :serviceName ORDER BY sv.majorVersion DESC, sv.minorVersion DESC, sv.buildNumber DESC")
    List<ServiceVersion> findAllByServiceNameOrderByVersionDesc(@Param("serviceName") String serviceName);

    @Query("SELECT MAX(sv.buildNumber) FROM ServiceVersion sv WHERE sv.serviceName = :serviceName AND sv.majorVersion = :major AND sv.minorVersion = :minor")
    Optional<Integer> findMaxBuildNumber(@Param("serviceName") String serviceName,
                                          @Param("major") Integer majorVersion,
                                          @Param("minor") Integer minorVersion);

    List<ServiceVersion> findTop10ByServiceNameOrderByDeployedAtDesc(String serviceName);
}
