package com.pulse.core.repo;

import com.pulse.core.model.entity.ConfigSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigSourceRepository extends JpaRepository<ConfigSource, Long> {

    Optional<ConfigSource> findByName(String name);

}
