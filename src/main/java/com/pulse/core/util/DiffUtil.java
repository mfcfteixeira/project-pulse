package com.pulse.core.util;

public final class DiffUtil {

    private DiffUtil() {}

    public static String simpleSummary(String before, String after) {
        if (before == null) before = "";
        if (after == null) after = "";
        if (before.equals(after)) return "no-changes";
        int beforeLines = before.split("\\R").length;
        int afterLines = after.split("\\R").length;
        return String.format("lines: %d -> %d", beforeLines, afterLines);
    }
}
