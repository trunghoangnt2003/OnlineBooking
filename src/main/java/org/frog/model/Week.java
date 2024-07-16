package org.frog.model;

import org.frog.DAO.ScheduleDAO;
import org.frog.utility.DateTimeHelper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Week {
    private String dayOfWeek;
    private String dayOfMonth;

    public Week() {
    }

    public Week(String dayOfWeek, String dayOfMonth) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
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
    public boolean isSlotPassed(String date, int slot) {
        try {
            String time = "";
            switch (slot) {
                case 1:
                    time = "08:00";
                    break;
                case 2:
                    time = "10:00";
                    break;
                case 3:
                    time = "13:00";
                    break;
                case 4:
                    time = "15:00";
                    break;
                case 5:
                    time = "17:00";
                    break;
                case 6:
                    time = "19:00";
                    break;
                case 7:
                    time = "21:00";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid time slot");
            }

           // String dateTimeStr = date + " " + time;
            String dateTimeStr = date + " 00:00" ;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

            return dateTime.isBefore(LocalDateTime.now());



        } catch (DateTimeParseException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
