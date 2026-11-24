package org.example;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MaintenanceScheduler {
    private final Clock clock;

    public MaintenanceScheduler(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    private static void validate(LocalTime localStart, Duration duration, ZoneId zone, OverlapPolicy policy) {
        Objects.requireNonNull(localStart, "localStart");
        Objects.requireNonNull(zone, "zone");
        Objects.requireNonNull(policy, "policy");
        Objects.requireNonNull(duration, "duration");
        if (duration.isNegative() || duration.isZero()) {
            throw new IllegalArgumentException("Window duration must be positive");
        }
    }

    public MaintenanceWindow nextWindow(LocalDate date, LocalTime localStart, Duration duration,
                                        ZoneId zone, OverlapPolicy policy) {
        validate(localStart, duration, zone, policy);
        LocalDateTime local = Objects.requireNonNull(date, "date").atTime(localStart);
        if (zone.getRules().getValidOffsets(local).isEmpty()) {
            throw new DateTimeException("Local time does not exist: " + local + " in " + zone);
        }
        ZonedDateTime time = local.atZone(zone);
        time = policy == OverlapPolicy.EARLIER ? time.withEarlierOffsetAtOverlap() : time.withLaterOffsetAtOverlap();
        Instant start = time.toInstant();
        return new MaintenanceWindow(start, start.plus(duration));
    }

    public List<MaintenanceWindow> upcomingWindows(int days, LocalTime localStart, Duration duration,
                                                   ZoneId zone, OverlapPolicy policy) {
        validate(localStart, duration, zone, policy);
        if (days < 0) {
            throw new IllegalArgumentException("Days must be non-negative");
        }
        List<MaintenanceWindow> windows = new ArrayList<>();
        LocalDate today = LocalDate.now(clock.withZone(zone));
        for (int i = 0; i < days; i++) {
            windows.add(nextWindow(today.plusDays(i), localStart, duration, zone, policy));
        }
        return windows;
    }
}
