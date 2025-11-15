package com.pulse.core.controller;

import com.pulse.core.service.FileScanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/scan")
public class ScanController {
    private final FileScanService fileScanService;

    public ScanController(FileScanService fileScanService) {
        this.fileScanService = fileScanService;
    }

    @PostMapping
    public ResponseEntity<String> triggerScan(@RequestParam String sourceName,
                                              @RequestParam(defaultValue = ".") String root) {
        try {
            fileScanService.scanPath(sourceName, Path.of(root));
            return ResponseEntity.ok("Scan completed");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Scan failed: " + e.getMessage());
        }
    }
}
