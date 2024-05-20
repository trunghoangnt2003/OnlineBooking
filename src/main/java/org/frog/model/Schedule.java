package org.frog.model;

import java.sql.Date;

public class Schedule {
    private int id;
    private Date dateStart;
    private Date dateEnd;
    private Status status;
    private Account account;
    private Booking booking;

    public Schedule(int id, Date dateStart, Date dateEnd, Status status, Account account, Booking booking) {
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
}
