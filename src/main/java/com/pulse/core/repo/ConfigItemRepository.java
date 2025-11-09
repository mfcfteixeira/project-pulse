package com.pulse.core.repo;

import com.pulse.core.model.entity.ConfigItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigItemRepository extends JpaRepository<ConfigItem, Long> {

    List<ConfigItem> findByEnvironmentId(Long environmentId);

}

