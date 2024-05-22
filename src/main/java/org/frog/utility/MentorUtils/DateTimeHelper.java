package org.frog.utility.MentorUtils;

import org.frog.model.Week;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateTimeHelper {
    public static List<Week> getWeekDates(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate givenDate = LocalDate.parse(dateStr, formatter);
        LocalDate startOfWeek = givenDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        List<Week> weekDates = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate currentDay = startOfWeek.plusDays(i);
            String dayOfMonth = currentDay.format(formatter);
            String dayOfWeek = currentDay.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            weekDates.add(new Week(dayOfWeek, dayOfMonth));
        }

        return weekDates;
    }
    public static String convertDateToString(LocalDate date) {
        // Định dạng ngày
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public static java.sql.Timestamp convertToTimestamp(String date, String time) {
        String dateTime = date + " " + time;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Timestamp timestamp = null;

        try {
            java.util.Date parsedDate = dateFormat.parse(dateTime);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestamp;
    }
}
