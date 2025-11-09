package com.pulse.core.service;

import com.pulse.core.model.entity.DriftSeverity;
import com.pulse.core.util.DiffUtil;
import org.springframework.stereotype.Service;

@Service
public class ConfigComparisonService {

    /**
     * Return a small text diff
     */
    public String computeDiffSummary(String before, String after) {
        return DiffUtil.simpleSummary(before, after);
    }

    public DriftSeverity estimateSeverity(String before, String after) {
        if (before == null) {
            before = "";
        }
        if (after == null) {
            after = "";
        }
        if (before.equals(after)) {
            return DriftSeverity.INFO;
        }
        int beforeLines = before.split("\\R").length;
        int afterLines = after.split("\\R").length;
        int delta = Math.abs(afterLines - beforeLines);

        if (delta == 0) {
            return DriftSeverity.MINOR;
        }
        if (delta < 10) {
            return DriftSeverity.MINOR;
        }
        return DriftSeverity.MAJOR;
    }
}
