package org.example;

import java.time.Instant;
import java.time.Duration;
import java.util.Objects;

public record MaintenanceWindow(Instant start, Instant end) {
    public MaintenanceWindow {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("Window duration must be positive");
        }
    }

    public Duration duration() {
        return Duration.between(start, end);
    }
}
