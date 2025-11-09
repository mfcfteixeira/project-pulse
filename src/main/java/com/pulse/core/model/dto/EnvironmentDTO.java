package com.pulse.core.model.dto;

import java.util.List;

public class EnvironmentDTO {
    private Long id;
    private String name;
    private List<ConfigItemDTO> configs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConfigItemDTO> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ConfigItemDTO> configs) {
        this.configs = configs;
    }
}
