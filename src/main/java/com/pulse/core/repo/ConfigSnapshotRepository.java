package com.pulse.core.repo;

import com.pulse.core.model.entity.ConfigSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigSnapshotRepository extends JpaRepository<ConfigSnapshot, Long> {
    Optional<ConfigSnapshot> findTopBySourceIdOrderByCapturedAtDesc(Long sourceId);
}
