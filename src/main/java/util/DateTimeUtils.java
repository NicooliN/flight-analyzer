package util;
import lombok.experimental.UtilityClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtils {
    private final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy H:mm");

    public LocalDateTime parseDateTime(String date, String time) {
        String dateTimeString = date + " " + time;
        return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
    }

    public String formatDuration(long minutes) {
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        return hours + " часов " + remainingMinutes + " минут";
    }
}