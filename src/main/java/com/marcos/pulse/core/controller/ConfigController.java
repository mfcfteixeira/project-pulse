package com.marcos.pulse.core.controller;

import com.marcos.pulse.common.mapper.ConfigMapper;
import com.marcos.pulse.core.model.entity.ConfigItem;
import com.marcos.pulse.core.model.dto.ConfigItemDTO;
import com.marcos.pulse.core.service.ConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environments")
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/{envName}/configs")
    public ConfigItemDTO addConfig(@PathVariable String envName, @RequestBody ConfigItemDTO dto) {
        ConfigItem item = configService.addConfigItem(envName, dto);
        return ConfigMapper.toDTO(item);
    }


    @GetMapping("/{envName}/configs")
    public List<ConfigItemDTO> listConfigs(@PathVariable String envName) {
        return configService.getConfigs(envName)
                .stream()
                .map(ConfigMapper::toDTO)
                .toList();
    }
}
