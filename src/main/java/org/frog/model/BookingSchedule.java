package org.frog.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookingSchedule {
    private Schedule schedule;
    private ArrayList<Booking> bookings;
    private  Booking booking;
    private Account account;
    private Status status;
    private Level_Skills skill;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Level_Skills getSkill() {
        return skill;
    }

    public void setSkill(Level_Skills skill) {
        this.skill = skill;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public BookingSchedule(ArrayList<Booking> bookings, Schedule schedule) {
        this.bookings = bookings;
        this.schedule = schedule;
    }

    public BookingSchedule() {
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }
}
