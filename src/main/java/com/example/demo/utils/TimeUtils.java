package com.example.demo.utils;

import java.time.*;

public class TimeUtils {
    private TimeUtils() {
    }

    public static Instant instant(int year, Month month, int day) {
        return toInstant(LocalDate.of(year, month, day).atStartOfDay());
    }

    public static Instant toInstant(LocalDateTime localDateTime) {
        return localDateTime.toInstant(OffsetDateTime.now().getOffset());
    }


    public static ZonedDateTime toZonedDateTime(Instant sessionStartInstant, String zoneId) {
        ZoneId zone = ZoneId.of(zoneId, ZoneId.SHORT_IDS);
        return sessionStartInstant.atZone(zone);
    }

    public static ZonedDateTime toZonedDateTime(Instant sessionStartInstant) {
        return toZonedDateTime(sessionStartInstant, ZoneId.systemDefault().getId());
    }


}
