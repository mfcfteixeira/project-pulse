package com.pulse.common.mapper;

import com.pulse.core.model.dto.ConfigItemDTO;
import com.pulse.core.model.dto.EnvironmentDTO;
import com.pulse.core.model.entity.ConfigItem;
import com.pulse.core.model.entity.Environment;

import java.util.stream.Collectors;

public class ConfigMapper {

    public static ConfigItemDTO toDTO(ConfigItem item) {
        ConfigItemDTO dto = new ConfigItemDTO();
        dto.setId(item.getId());
        dto.setKeyName(item.getKeyName());
        dto.setValue(item.getValue());
        dto.setDescription(item.getDescription());
        return dto;
    }

    public static EnvironmentDTO toDTO(Environment env) {
        EnvironmentDTO dto = new EnvironmentDTO();
        dto.setId(env.getId());
        dto.setName(env.getName());
        dto.setConfigs(
                env.getConfigs() != null
                        ? env.getConfigs().stream().map(ConfigMapper::toDTO).collect(Collectors.toList())
                        : null
        );
        return dto;
    }
}
