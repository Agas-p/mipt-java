package org.example;

import java.time.Instant;
import java.time.Duration;
import java.util.Objects;

public record Incident(String id, String service, Instant startedAt, Instant resolvedAt) {
    public Incident {
        if (Objects.requireNonNull(id, "id").isBlank() || Objects.requireNonNull(service, "service").isBlank()) {
            throw new IllegalArgumentException("Id and service must not be blank");
        }
        Objects.requireNonNull(startedAt, "startedAt");
        Objects.requireNonNull(resolvedAt, "resolvedAt");
        if (resolvedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("Incident ends before it starts");
        }
    }

    public Duration duration() {
        return Duration.between(startedAt, resolvedAt);
    }
}
