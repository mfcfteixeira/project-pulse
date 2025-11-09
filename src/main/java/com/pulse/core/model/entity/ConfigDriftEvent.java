package com.pulse.core.model.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "config_drift_event", indexes = {
        @Index(name = "idx_drift_source_ts", columnList = "source_id, detectedAt")
})
public class ConfigDriftEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private ConfigSource source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_snapshot_id")
    private ConfigSnapshot fromSnapshot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_snapshot_id")
    private ConfigSnapshot toSnapshot;

    private Instant detectedAt = Instant.now();

    @Enumerated(EnumType.STRING)
    private DriftSeverity severity = DriftSeverity.INFO;

    @Lob
    private String diffSummary;

    private boolean resolved = false;
    private Instant resolvedAt;
    private String resolverNote;

    public ConfigDriftEvent() {}

    public ConfigDriftEvent(ConfigSource source, ConfigSnapshot fromSnapshot, ConfigSnapshot toSnapshot, DriftSeverity severity, String diffSummary) {
        this.source = source;
        this.fromSnapshot = fromSnapshot;
        this.toSnapshot = toSnapshot;
        this.severity = severity;
        this.diffSummary = diffSummary;
    }

    public Long getId() { return id; }

    public ConfigSource getSource() { return source; }
    public void setSource(ConfigSource source) { this.source = source; }

    public ConfigSnapshot getFromSnapshot() { return fromSnapshot; }
    public void setFromSnapshot(ConfigSnapshot fromSnapshot) { this.fromSnapshot = fromSnapshot; }

    public ConfigSnapshot getToSnapshot() { return toSnapshot; }
    public void setToSnapshot(ConfigSnapshot toSnapshot) { this.toSnapshot = toSnapshot; }

    public Instant getDetectedAt() { return detectedAt; }
    public void setDetectedAt(Instant detectedAt) { this.detectedAt = detectedAt; }

    public DriftSeverity getSeverity() { return severity; }
    public void setSeverity(DriftSeverity severity) { this.severity = severity; }

    public String getDiffSummary() { return diffSummary; }
    public void setDiffSummary(String diffSummary) { this.diffSummary = diffSummary; }

    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }

    public Instant getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Instant resolvedAt) { this.resolvedAt = resolvedAt; }

    public String getResolverNote() { return resolverNote; }
    public void setResolverNote(String resolverNote) { this.resolverNote = resolverNote; }
}
