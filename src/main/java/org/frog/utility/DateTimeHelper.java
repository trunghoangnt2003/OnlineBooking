package org.frog.utility;

import org.frog.model.Schedule;
import org.frog.model.Week;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public static Date convertStringToDateByDay(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate;
        try {
            utilDate = sdf.parse(dateString);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean compareDayIDtoNow(String day, String start , String end){
        start = start.trim() + ":00";
        end = end.trim() + ":00";

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime startTime = LocalTime.parse(start, formatterTime);
        LocalTime endTime = LocalTime.parse(end, formatterTime);

        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate selectDay = LocalDate.parse(day, formatterDay);
        LocalDateTime startDayTime = selectDay.atTime(startTime);
        LocalDateTime endDayTime = selectDay.atTime(endTime);
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(startDayTime) && now.isAfter(endDayTime)){
            return false;
        }
        else if (now.isAfter(startDayTime) && now.isBefore(endDayTime)){
            return false;
        }
        return true;
    }
    public static boolean checkExpiredBooking(Timestamp createDate){
        LocalDateTime createDateTime = createDate.toLocalDateTime();
        LocalDateTime expireDateTime = createDateTime.plusDays(1);
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(expireDateTime)) {
            return true;
        }
        return false;
    }

    public static String getFutureDate(int weeksAhead) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusWeeks(weeksAhead);

        return futureDate.toString();
    }

}
