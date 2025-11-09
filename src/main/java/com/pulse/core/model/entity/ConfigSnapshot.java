package com.pulse.core.model.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "config_snapshot", indexes = {
        @Index(name = "idx_snapshot_source_ts", columnList = "source_id, capturedAt")
})
public class ConfigSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private ConfigSource source;

    private Instant capturedAt = Instant.now();

    private String checksum;

    private Long contentSize;

    @Lob
    private String content;

    private String summary;

    public ConfigSnapshot() {}

    public ConfigSnapshot(String checksum, Long contentSize, String content, String summary) {
        this.checksum = checksum;
        this.contentSize = contentSize;
        this.content = content;
        this.summary = summary;
    }

    public Long getId() { return id; }
    public ConfigSource getSource() { return source; }
    public void setSource(ConfigSource source) { this.source = source; }
    public Instant getCapturedAt() { return capturedAt; }
    public void setCapturedAt(Instant capturedAt) { this.capturedAt = capturedAt; }
    public String getChecksum() { return checksum; }
    public void setChecksum(String checksum) { this.checksum = checksum; }
    public Long getContentSize() { return contentSize; }
    public void setContentSize(Long contentSize) { this.contentSize = contentSize; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}
