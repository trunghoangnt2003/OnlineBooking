package org.frog.utility;

import org.frog.model.Schedule;
import org.frog.model.Week;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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
    public static boolean compareTimeMenteeBook(ArrayList<Schedule> list, java.sql.Timestamp start, java.sql.Timestamp end) {
        return false;
    }
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


    // cua hai
    public static Date getWeekStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysToSubtract = dayOfWeek - Calendar.MONDAY;
        if (daysToSubtract < 0) {
            daysToSubtract = 6;
        }
        calendar.add(Calendar.DAY_OF_MONTH, -daysToSubtract);

        return calendar.getTime();
    }

    public static java.sql.Date convertUtilDateToSqlDate(java.util.Date utilDate) {
        if (utilDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(utilDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return new java.sql.Date(calendar.getTimeInMillis());
        } else {
            return null;
        }
    }

    public static Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public static ArrayList<java.sql.Date> getDatesBetween(java.sql.Date fromDate, java.sql.Date toDate) {
        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("Both fromDate and toDate cannot be null.");
        }
        if (fromDate.after(toDate)) {
            throw new IllegalArgumentException("fromDate cannot be after toDate.");
        }

        Date from = new Date(fromDate.getTime());
        Date to = new Date(toDate.getTime());

        Date temp = from;

        ArrayList<java.sql.Date> dates = new ArrayList<>();
        while(!temp.after(to))
        {
            dates.add(convertUtilDateToSqlDate(temp));
            temp=addDaysToDate(temp, 1);
        }
        return dates;
    }

}
