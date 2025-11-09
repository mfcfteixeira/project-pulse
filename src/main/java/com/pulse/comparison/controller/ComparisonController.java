package com.pulse.comparison.controller;

import com.pulse.comparison.model.dto.ComparisonResultDTO;
import com.pulse.comparison.service.ComparisonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/compare")
public class ComparisonController {

    private final ComparisonService comparisonService;

    public ComparisonController(ComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    @GetMapping
    public List<ComparisonResultDTO> compareEnvironments(
            @RequestParam String env1,
            @RequestParam String env2) {
        return comparisonService.compareEnvironments(env1, env2);
    }
}
