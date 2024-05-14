package org.frog.model;

import java.util.ArrayList;
import java.util.List;

public class BookingSchedule {
    private Schedule schedule;
    private ArrayList<Booking> bookings;

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
