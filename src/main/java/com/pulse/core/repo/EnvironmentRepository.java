package com.pulse.core.repo;

import com.pulse.core.model.entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {

    Optional<Environment> findByName(String name);
}
