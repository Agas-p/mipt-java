package org.example;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class ReportFormatter {
    public static String formatWindow(MaintenanceWindow window, ZoneId zone, Locale locale) {
        Objects.requireNonNull(window, "window");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm XXX (VV)", locale)
                .withZone(Objects.requireNonNull(zone, "zone"));
        return format.format(window.start()) + " – " + format.format(window.end());
    }
}
