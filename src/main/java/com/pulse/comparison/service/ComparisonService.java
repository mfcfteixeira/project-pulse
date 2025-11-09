package com.pulse.comparison.service;

import com.pulse.comparison.model.ComparisonStatus;
import com.pulse.comparison.model.dto.ComparisonResultDTO;
import com.pulse.core.model.entity.ConfigItem;
import com.pulse.core.model.entity.Environment;
import com.pulse.core.repo.ConfigItemRepository;
import com.pulse.core.repo.EnvironmentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComparisonService {

    private final EnvironmentRepository environmentRepository;
    private final ConfigItemRepository configItemRepository;

    public ComparisonService(EnvironmentRepository environmentRepository,
                             ConfigItemRepository configItemRepository) {
        this.environmentRepository = environmentRepository;
        this.configItemRepository = configItemRepository;
    }

    public List<ComparisonResultDTO> compareEnvironments(String env1Name, String env2Name) {
        Environment env1 = environmentRepository.findByName(env1Name)
                .orElseThrow(() -> new IllegalArgumentException("Environment not found: " + env1Name));
        Environment env2 = environmentRepository.findByName(env2Name)
                .orElseThrow(() -> new IllegalArgumentException("Environment not found: " + env2Name));

        Map<String, String> env1Map = mapConfigs(env1);
        Map<String, String> env2Map = mapConfigs(env2);

        List<ComparisonResultDTO> results = new ArrayList<>();

        // Compare env1 keys
        for (String key : env1Map.keySet()) {
            if (env2Map.containsKey(key)) {
                if (!Objects.equals(env1Map.get(key), env2Map.get(key))) {
                    results.add(new ComparisonResultDTO(key, env1Map.get(key), env2Map.get(key), ComparisonStatus.CHANGED));
                }
            } else {
                results.add(new ComparisonResultDTO(key, env1Map.get(key), null, ComparisonStatus.MISSING_IN_ENV2));
            }
        }

        // Compare env2-only keys
        for (String key : env2Map.keySet()) {
            if (!env1Map.containsKey(key)) {
                results.add(new ComparisonResultDTO(key, null, env2Map.get(key), ComparisonStatus.ADDED_IN_ENV2));
            }
        }

        // Sort results alphabetically by key (optional)
        return results.stream()
                .sorted(Comparator.comparing(ComparisonResultDTO::getKeyName))
                .collect(Collectors.toList());
    }

    private Map<String, String> mapConfigs(Environment env) {
        return env.getConfigs().stream()
                .collect(Collectors.toMap(ConfigItem::getKeyName, ConfigItem::getValue));
    }
}
