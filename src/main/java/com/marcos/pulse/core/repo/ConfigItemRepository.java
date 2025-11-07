package com.marcos.pulse.core.repo;

import com.marcos.pulse.core.model.entity.ConfigItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigItemRepository extends JpaRepository<ConfigItem, Long> {

    List<ConfigItem> findByEnvironmentId(Long environmentId);

}

