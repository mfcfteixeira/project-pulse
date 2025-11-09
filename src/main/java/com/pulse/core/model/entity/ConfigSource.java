package com.pulse.core.model.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "config_source")
public class ConfigSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ConfigSourceType type;

    private String location;

    @Lob
    private String metadata;

    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConfigSnapshot> snapshots = new ArrayList<>();

    public ConfigSource() {}

    public ConfigSource(String name, ConfigSourceType type, String location) {
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ConfigSourceType getType() { return type; }
    public void setType(ConfigSourceType type) { this.type = type; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
    public Instant getCreatedAt() { return createdAt; }

    public List<ConfigSnapshot> getSnapshots() {
        return Collections.unmodifiableList(snapshots);
    }

    public void addSnapshot(ConfigSnapshot snapshot) {
        snapshot.setSource(this);
        snapshots.add(snapshot);
    }

    public void removeSnapshot(ConfigSnapshot snapshot) {
        snapshots.remove(snapshot);
        snapshot.setSource(null);
    }
}
