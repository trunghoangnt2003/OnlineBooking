package org.frog.model;


public class Mentee {

    private Account account;

    private Booking booking;

    public Mentee(Account account) {
        this.account = account;
    }

    public Mentee() {
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
