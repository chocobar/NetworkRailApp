package utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created by priya on 14/08/2014.
 */
public class DateTimeUtils {
    public static final DateTimeZone utc = DateTimeZone.forID("UTC");

    public static final PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
            .appendDays()
            .appendSuffix(" day", " days")
            .appendSeparator(" and ")
            .appendHours()
            .appendSuffix(" hour", " hours")
            .appendSeparator(" and ")
            .appendMinutes()
            .appendSuffix(" minute", " minutes")
            .appendSeparator(" and ")
            .appendSeconds()
            .appendSuffix(" second", " seconds")
            .toFormatter();

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss Z");

    public static String printLocalDateTime(DateTime dateTime) {
        return dateTimeFormatter.print(dateTime.toLocalDateTime());
    }

    public static DateTime getTimeFromTimeStamp(String timestamp) {
        Long _sLong = new Long(timestamp);
        return new DateTime(_sLong).withZone(utc);
    }
}
