package com.pulse.core.service;

import com.pulse.core.model.entity.ConfigSource;
import com.pulse.core.repo.ConfigSourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileScanService {

    private final ConfigSourceRepository sourceRepo;

    public FileScanService(ConfigSourceRepository sourceRepo) {
            this.sourceRepo = sourceRepo;
    }

    /**
     * Walk the provided rootPath and return a list of config files found.
     */
    @Transactional
    public List<Path>  scanPath(String sourceName, Path rootPath) throws IOException {
        ConfigSource source = sourceRepo.findByName(sourceName)
                .orElseThrow(() -> new IllegalArgumentException("source not found: " + sourceName));

        if (!Files.exists(rootPath) || !Files.isDirectory(rootPath)) {
            throw new IllegalArgumentException("rootPath must exist and be a directory: " + rootPath);
        }

        List<Path> files;
        try (Stream<Path> walk = Files.walk(rootPath)) {
            files = walk
                    .filter(Files::isRegularFile)
                    .filter(this::isConfigFile)
                    .toList();
        }
        return files;
    }

    private boolean isConfigFile(Path p) {
        String n = p.getFileName().toString().toLowerCase();
        return n.endsWith(".yml") || n.endsWith(".yaml") || n.endsWith(".properties")
                || n.endsWith(".json") || n.endsWith(".conf") || n.endsWith(".ini");
    }
}
