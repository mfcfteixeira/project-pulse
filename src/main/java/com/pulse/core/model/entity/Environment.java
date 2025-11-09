package com.pulse.core.model.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // e.g. "dev", "staging", "prod"

    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConfigItem> configs;

    // Getters & setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<ConfigItem> getConfigs() { return configs; }
    public void setConfigs(List<ConfigItem> configs) { this.configs = configs; }
}