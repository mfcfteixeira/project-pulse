package com.marcos.pulse.core.service;

import com.marcos.pulse.core.model.entity.ConfigItem;
import com.marcos.pulse.core.model.entity.Environment;
import com.marcos.pulse.core.model.dto.ConfigItemDTO;
import com.marcos.pulse.core.repo.ConfigItemRepository;
import com.marcos.pulse.core.repo.EnvironmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {

    private final ConfigItemRepository configRepo;
    private final EnvironmentRepository envRepo;

    public ConfigService(ConfigItemRepository configRepo, EnvironmentRepository envRepo) {
        this.configRepo = configRepo;
        this.envRepo = envRepo;
    }

    public Environment createEnvironment(String name) {
        return envRepo.findByName(name).orElseGet(() -> {
            Environment env = new Environment();
            env.setName(name);
            return envRepo.save(env);
        });
    }

    public ConfigItem addConfigItem(String envName, ConfigItemDTO dto) {
        Environment env = createEnvironment(envName);
        ConfigItem item = new ConfigItem();
        item.setKeyName(dto.getKeyName());
        item.setValue(dto.getValue());
        item.setDescription(dto.getDescription());
        item.setEnvironment(env);
        return configRepo.save(item);
    }

    public List<ConfigItem> getConfigs(String envName) {
        Environment env = envRepo.findByName(envName)
                .orElseThrow(() -> new RuntimeException("Environment not found: " + envName));
        return configRepo.findByEnvironmentId(env.getId());
    }
}
