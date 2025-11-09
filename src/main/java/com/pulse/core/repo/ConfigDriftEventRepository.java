package com.pulse.core.repo;

import com.pulse.core.model.entity.ConfigDriftEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigDriftEventRepository extends JpaRepository<ConfigDriftEvent, Long> {
    List<ConfigDriftEvent> findBySourceIdOrderByDetectedAtDesc(Long sourceId);
}
