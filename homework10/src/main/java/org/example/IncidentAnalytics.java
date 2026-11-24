package org.example;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class IncidentAnalytics {
    private static final Comparator<Incident> BY_START = Comparator.comparing(Incident::startedAt)
            .thenComparing(Incident::id);

    public static Duration totalDowntime(List<Incident> incidents) {
        Duration total = Duration.ZERO;
        for (Incident incident : Objects.requireNonNull(incidents, "incidents")) {
            total = total.plus(Objects.requireNonNull(incident, "incident").duration());
        }
        return total;
    }

    public static Duration averageDowntime(List<Incident> incidents) {
        Duration total = totalDowntime(incidents);
        return incidents.isEmpty() ? Duration.ZERO : total.dividedBy(incidents.size());
    }

    public static Optional<Incident> longestIncident(List<Incident> incidents) {
        Objects.requireNonNull(incidents, "incidents");
        return incidents.stream().min(Comparator.comparing(Incident::duration).reversed().thenComparing(BY_START));
    }

    public static Map<LocalDate, List<Incident>> groupByStartDate(List<Incident> incidents, ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        Map<LocalDate, List<Incident>> groups = new TreeMap<>();
        for (Incident incident : Objects.requireNonNull(incidents, "incidents")) {
            LocalDate date = incident.startedAt().atZone(zone).toLocalDate();
            groups.computeIfAbsent(date, key -> new ArrayList<>()).add(incident);
        }
        for (List<Incident> group : groups.values()) {
            group.sort(BY_START);
        }
        return groups;
    }
}
