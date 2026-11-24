package org.example;

import org.junit.jupiter.api.Test;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class TimeApiTest {
    private final ZoneId berlin = ZoneId.of("Europe/Berlin");
    private final Duration hour = Duration.ofHours(1);
    private final MaintenanceScheduler scheduler = new MaintenanceScheduler(
            Clock.fixed(Instant.parse("2024-03-29T23:30:00Z"), ZoneOffset.UTC));

    @Test
    void parsesIncidentAndChecksFields() {
        String times = ";2024-03-30T22:30:00Z;2024-03-31T01:00:00Z";
        Incident incident = IncidentParser.parse("INC-1;billing" + times);
        assertEquals("billing", incident.service());
        assertEquals(Duration.ofMinutes(150), incident.duration());
        assertThrows(IllegalArgumentException.class, () -> IncidentParser.parse(";billing" + times));
        assertThrows(IllegalArgumentException.class, () -> IncidentParser.parse("x; " + times));
        assertThrows(IllegalArgumentException.class, () -> IncidentParser.parse("x;billing" + times + ";"));
        assertThrows(DateTimeParseException.class, () -> IncidentParser.parse("x;billing;bad;2024-01-01T00:00:00Z"));
        assertThrows(IllegalArgumentException.class, () -> IncidentParser.parse(
                "x;billing;2024-03-31T01:00:00Z;2024-03-30T22:30:00Z"));
    }

    @Test
    void totalsAndAveragesLargeDurations() {
        Instant start = Instant.EPOCH;
        Duration large = Duration.ofDays(200_000);
        Incident first = new Incident("a", "api", start, start.plus(large));
        Incident second = new Incident("b", "api", start, start.plus(large).plusNanos(1));
        List<Incident> incidents = List.of(first, second);
        assertEquals(large.plus(large).plusNanos(1), IncidentAnalytics.totalDowntime(incidents));
        assertEquals(large, IncidentAnalytics.averageDowntime(incidents));
        assertEquals(Optional.of(second), IncidentAnalytics.longestIncident(incidents));
        assertEquals(Duration.ZERO, IncidentAnalytics.totalDowntime(List.of()));
        assertEquals(Duration.ZERO, IncidentAnalytics.averageDowntime(List.of()));
        assertEquals(Optional.empty(), IncidentAnalytics.longestIncident(List.of()));
    }

    @Test
    void groupsByLocalDateAndBreaksTies() {
        Instant start = Instant.parse("2024-03-30T23:30:00Z");
        Incident a = new Incident("a", "api", start, start.plus(hour));
        Incident b = new Incident("b", "api", start, start.plus(hour));
        Incident c = new Incident("c", "api", start.plusSeconds(1), start.plusSeconds(3601));
        Incident previous = new Incident("z", "api", start.minus(hour), start.minus(hour));
        List<Incident> incidents = List.of(c, b, previous, a);
        var groups = IncidentAnalytics.groupByStartDate(incidents, berlin);
        assertEquals(List.of(LocalDate.of(2024, 3, 30), LocalDate.of(2024, 3, 31)), List.copyOf(groups.keySet()));
        assertEquals(List.of(a, b, c), groups.get(LocalDate.of(2024, 3, 31)));
        assertEquals(Optional.of(a), IncidentAnalytics.longestIncident(incidents));
    }

    @Test
    void rejectsSpringGapAndResolvesAutumnOverlap() {
        LocalTime start = LocalTime.of(2, 30);
        assertThrows(DateTimeException.class, () -> scheduler.nextWindow(
                LocalDate.of(2024, 3, 31), start, hour, berlin, OverlapPolicy.EARLIER));
        LocalDate date = LocalDate.of(2024, 10, 27);
        var early = scheduler.nextWindow(date, start, hour, berlin, OverlapPolicy.EARLIER);
        var late = scheduler.nextWindow(date, start, hour, berlin, OverlapPolicy.LATER);
        assertEquals(ZoneOffset.ofHours(2), early.start().atZone(berlin).getOffset());
        assertEquals(ZoneOffset.ofHours(1), late.start().atZone(berlin).getOffset());
        assertEquals(hour, Duration.between(early.start(), late.start()));
        assertEquals(hour, early.duration());
    }

    @Test
    void schedulesByCalendarDay() {
        LocalTime start = LocalTime.of(3, 30);
        var windows = scheduler.upcomingWindows(2, start, hour, berlin, OverlapPolicy.EARLIER);
        assertEquals(2, windows.size());
        assertEquals(LocalDate.of(2024, 3, 30).atTime(start), windows.get(0).start().atZone(berlin).toLocalDateTime());
        assertEquals(LocalDate.of(2024, 3, 31).atTime(start), windows.get(1).start().atZone(berlin).toLocalDateTime());
        assertEquals(Duration.ofHours(23), Duration.between(windows.get(0).start(), windows.get(1).start()));
        assertEquals(windows, scheduler.upcomingWindows(2, start, hour, berlin, OverlapPolicy.EARLIER));
        assertTrue(scheduler.upcomingWindows(0, start, hour, berlin, OverlapPolicy.EARLIER).isEmpty());
        assertThrows(IllegalArgumentException.class, () -> scheduler.upcomingWindows(-1, start, hour, berlin, OverlapPolicy.EARLIER));
        assertThrows(IllegalArgumentException.class, () -> scheduler.upcomingWindows(1, start, Duration.ZERO, berlin, OverlapPolicy.EARLIER));
    }

    @Test
    void formatsReportAndConvertsLegacyTypes() {
        Instant start = Instant.parse("2024-03-31T01:00:00Z");
        var window = new MaintenanceWindow(start, start.plus(hour));
        assertEquals("31.03.2024 03:00 +02:00 (Europe/Berlin) – 31.03.2024 04:00 +02:00 (Europe/Berlin)",
                ReportFormatter.formatWindow(window, berlin, Locale.GERMANY));
        Instant withMillis = start.plusMillis(123);
        assertEquals(withMillis, LegacyDateAdapter.toInstant(LegacyDateAdapter.toDate(withMillis)));
        ZonedDateTime zoned = withMillis.atZone(berlin);
        assertEquals(zoned, LegacyDateAdapter.toZonedDateTime(LegacyDateAdapter.toGregorianCalendar(zoned)));
    }
}
