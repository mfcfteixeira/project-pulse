package com.pulse.core.repo;

import com.pulse.core.model.entity.ConfigSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigSourceRepository extends JpaRepository<ConfigSource, Long> {
    // add query methods later (findByName, findByLocation, etc.)
}
