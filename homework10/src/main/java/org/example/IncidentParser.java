package org.example;

import java.time.Instant;
import java.util.Objects;

public class IncidentParser {
    public static Incident parse(String line) {
        String[] fields = Objects.requireNonNull(line, "line").split(";", -1);
        if (fields.length != 4) {
            throw new IllegalArgumentException("Expected id;service;startedAt;resolvedAt");
        }
        return new Incident(fields[0], fields[1], Instant.parse(fields[2]), Instant.parse(fields[3]));
    }
}
