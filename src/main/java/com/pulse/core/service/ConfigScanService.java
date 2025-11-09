package com.pulse.core.service;

import com.pulse.core.model.entity.ConfigDriftEvent;
import com.pulse.core.model.entity.ConfigSnapshot;
import com.pulse.core.model.entity.ConfigSource;
import com.pulse.core.model.entity.DriftSeverity;
import com.pulse.core.repo.ConfigDriftEventRepository;
import com.pulse.core.repo.ConfigSnapshotRepository;
import com.pulse.core.repo.ConfigSourceRepository;
import com.pulse.core.util.ChecksumUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class ConfigScanService {

    private final ConfigSourceRepository sourceRepo;
    private final ConfigSnapshotRepository snapshotRepo;
    private final ConfigDriftEventRepository driftRepo;
    private final ConfigComparisonService comparisonService;

    public ConfigScanService(ConfigSourceRepository sourceRepo,
                             ConfigSnapshotRepository snapshotRepo,
                             ConfigDriftEventRepository driftRepo,
                             ConfigComparisonService comparisonService) {
        this.sourceRepo = sourceRepo;
        this.snapshotRepo = snapshotRepo;
        this.driftRepo = driftRepo;
        this.comparisonService = comparisonService;
    }

    /**
     * Create a snapshot for the provided source with given content.
     */
    @Transactional
    public ConfigSnapshot createSnapshotAndDetectDrift(ConfigSource source, String content, String summary) {
        String checksum = ChecksumUtil.sha256Hex(content);
        long size = ChecksumUtil.sizeBytes(content);

        ConfigSnapshot newSnap = new ConfigSnapshot(checksum, size, content, summary);
        newSnap.setCapturedAt(Instant.now());
        newSnap.setSource(source);
        source.addSnapshot(newSnap);
        sourceRepo.save(source); // cascades snapshot

        Optional<ConfigSnapshot> lastOpt = snapshotRepo.findTopBySourceIdOrderByCapturedAtDesc(source.getId());
        if (lastOpt.isPresent()) {
            ConfigSnapshot last = lastOpt.get();
            if (!checksum.equals(last.getChecksum())) {
                String diff = comparisonService.computeDiffSummary(last.getContent(), content);
                DriftSeverity severity = comparisonService.estimateSeverity(last.getContent(), content);
                ConfigDriftEvent event = new ConfigDriftEvent(source, last, newSnap, severity, diff);
                event.setDetectedAt(Instant.now());
                driftRepo.save(event);
                return newSnap;
            }
        }
        return newSnap;
    }
}
