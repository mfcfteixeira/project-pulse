// java
package com.pulse.pulseignore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PulseIgnoreService {

    private static final Logger log = LoggerFactory.getLogger(PulseIgnoreService.class);
    public static final String PULSEIGNORE_FILENAME = ".pulseignore";

    private volatile List<String> rawPatterns = Collections.emptyList();
    private volatile List<Pattern> compiled = Collections.emptyList();
    private volatile boolean loggingEnabled = false;

    @PostConstruct
    public void init() {
        Path defaultPath = Path.of(".").resolve(PULSEIGNORE_FILENAME).normalize();
        if (Files.exists(defaultPath)) {
            try {
                loadFromFile(defaultPath);
                log.info(".pulseignore loaded from {}", defaultPath.toAbsolutePath());
            } catch (IOException e) {
                log.warn("Failed to load .pulseignore from {}: {}", defaultPath, e.getMessage());
            }
        }
    }

    public boolean isIgnored(String key) {
        if (key == null) return false;
        for (Pattern p : compiled) {
            if (p.matcher(key).matches()) {
                if (loggingEnabled) {
                    log.debug("Ignored key matched pattern: key='{}' pattern='{}'", key, p.pattern());
                }
                return true;
            }
        }
        return false;
    }

    public void loadFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        loadFromLines(lines);
    }

    public void loadFromLines(List<String> lines) {
        List<String> cleaned = lines.stream()
                .map(String::trim)
                .filter(l -> !l.isEmpty())
                .filter(l -> !l.startsWith("#"))
                .toList();

        List<Pattern> newCompiled = new ArrayList<>(cleaned.size());
        for (String p : cleaned) {
            newCompiled.add(Pattern.compile(patternToRegex(p)));
        }

        rawPatterns = List.copyOf(cleaned);
        compiled = Collections.unmodifiableList(newCompiled);
    }

    public void setPatterns(List<String> patterns) {
        loadFromLines(new ArrayList<>(patterns));
    }

    public List<String> getPatterns() {
        return rawPatterns;
    }

    public void setLoggingEnabled(boolean enabled) {
        this.loggingEnabled = enabled;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void clear() {
        rawPatterns = Collections.emptyList();
        compiled = Collections.emptyList();
    }

    private static String patternToRegex(String pattern) {
        String escaped = Pattern.quote(pattern).replace("\\*", ".*");
        return "^" + escaped + "$";
    }
}