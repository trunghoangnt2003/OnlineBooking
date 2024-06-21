package org.frog.utility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelp {
    public static Timestamp converDayIDtoStartDate(String dayID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate givenDate = LocalDate.parse(dayID, formatter);
        LocalDateTime startOfDay = givenDate.atStartOfDay();
        return Timestamp.valueOf(startOfDay);
    }

    public static Timestamp converDayIDtoEndDate(String dayID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate givenDate = LocalDate.parse(dayID, formatter);
        LocalDateTime endOfDay = givenDate.atTime(23, 59, 59);
        return Timestamp.valueOf(endOfDay);
    }

    public static java.sql.Timestamp convertToTimestamp(String date, String time) {
        Timestamp timestamp = null;
        try {
            String dateTime = date + " " + time;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date parsedDate = dateFormat.parse(dateTime);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public static String convertDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
