package org.frog.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private int id;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private boolean status;
    private Account account;
    private Booking booking;

    public Schedule(int id, Timestamp dateStart, Timestamp dateEnd, boolean status, Account account, Booking booking) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
        this.account = account;
        this.booking = booking;
    }

    public Schedule() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    public int getDateStartHour() {
        LocalDateTime localDateTime = dateStart.toLocalDateTime();
        return localDateTime.getHour();
    }

    public int getDateEndtHour() {
        LocalDateTime localDateTime = dateEnd.toLocalDateTime();
        if(localDateTime.getMinute()>0) {
            return localDateTime.getHour()+1;
        }
        else{
            return localDateTime.getHour();
        }
    }
    public  String convertTimestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return ""; // hoặc giá trị mặc định bạn muốn trả về khi timestamp là null
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }
}
