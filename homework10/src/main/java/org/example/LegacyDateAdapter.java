package org.example;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class LegacyDateAdapter {
    public static Instant toInstant(Date date) {
        return Objects.requireNonNull(date, "date").toInstant();
    }

    public static Date toDate(Instant instant) {
        return Date.from(Objects.requireNonNull(instant, "instant"));
    }

    public static ZonedDateTime toZonedDateTime(GregorianCalendar calendar) {
        return Objects.requireNonNull(calendar, "calendar").toZonedDateTime();
    }

    public static GregorianCalendar toGregorianCalendar(ZonedDateTime time) {
        return GregorianCalendar.from(Objects.requireNonNull(time, "time"));
    }
}
