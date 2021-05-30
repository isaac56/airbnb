package team14.airbnb.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final DateTimeFormatter basicDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter basicDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtils() {
    }

    public static String toBasicString(LocalDate localDate) {
        return localDate.format(basicDateFormat);
    }

    public static String toBasicString(LocalDateTime localDateTime) {
        return localDateTime.format(basicDateTimeFormat);
    }
}
