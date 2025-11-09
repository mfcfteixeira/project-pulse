package com.pulse.core;

import com.pulse.core.model.entity.ConfigDriftEvent;
import com.pulse.core.model.entity.ConfigSnapshot;
import com.pulse.core.model.entity.ConfigSource;
import com.pulse.core.model.entity.ConfigSourceType;
import com.pulse.core.repo.ConfigDriftEventRepository;
import com.pulse.core.repo.ConfigSnapshotRepository;
import com.pulse.core.repo.ConfigSourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersistenceSmokeTest {

    @Autowired
    ConfigSourceRepository sourceRepo;
    @Autowired
    ConfigSnapshotRepository snapRepo;
    @Autowired
    ConfigDriftEventRepository driftRepo;

    @Test
    @Transactional
    void smokeTest_persistSourceSnapshotsAndDriftEvent() {
        // create and persist a config source
        ConfigSource source = new ConfigSource();
        source.setName("hosts-file");
        source.setType(ConfigSourceType.FILE);
        source.setLocation("/etc/hosts");
        ConfigSource savedSource = sourceRepo.save(source);
        assertNotNull(savedSource);
        assertNotNull(savedSource.getId(), "source id should be generated");

        // create and persist first snapshot
        ConfigSnapshot snap1 = new ConfigSnapshot();
        snap1.setSource(savedSource);
        snap1.setChecksum("hash-v1");
        snap1.setCapturedAt(Instant.now());
        snap1.setContent("127.0.0.1 localhost");
        ConfigSnapshot savedSnap1 = snapRepo.save(snap1);
        assertNotNull(savedSnap1);
        assertNotNull(savedSnap1.getId(), "snapshot1 id should be generated");

        // create and persist second snapshot (changed)
        ConfigSnapshot snap2 = new ConfigSnapshot();
        snap2.setSource(savedSource);
        snap2.setChecksum("hash-v2");
        snap2.setCapturedAt(Instant.now());
        snap2.setContent("127.0.0.1 localhost\n127.0.1.1 myhost");
        ConfigSnapshot savedSnap2 = snapRepo.save(snap2);
        assertNotNull(savedSnap2);
        assertNotEquals(savedSnap1.getChecksum(), savedSnap2.getChecksum(), "hash must differ to simulate drift");

        // record a drift event when snapshots differ
        ConfigDriftEvent drift = new ConfigDriftEvent();
        drift.setSource(savedSource);
        drift.setFromSnapshot(savedSnap1);
        drift.setToSnapshot(savedSnap2);
        drift.setDetectedAt(Instant.now());
        drift.setDiffSummary("Detected added host entry");
        ConfigDriftEvent savedDrift = driftRepo.save(drift);
        assertNotNull(savedDrift);
        assertNotNull(savedDrift.getId(), "drift event id should be generated");

        // basic repository sanity checks
        assertTrue(sourceRepo.findById(savedSource.getId()).isPresent());
        assertTrue(snapRepo.findById(savedSnap1.getId()).isPresent());
        assertTrue(snapRepo.findById(savedSnap2.getId()).isPresent());
        assertTrue(driftRepo.findById(savedDrift.getId()).isPresent());
    }
}