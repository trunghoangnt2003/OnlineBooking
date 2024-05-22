package org.frog.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Booking {
    private int id;
    private Status status;
    private float amount;
    private Date date;
    private Mentor mentor;
    private Mentee mentee;
    private Timestamp startDate;
    private Timestamp endDate;

    public Booking(int id, Status status, float amount, Date date, Mentor mentor, Mentee mentee, Timestamp startDate, Timestamp endDate) {
        this.id = id;
        this.status = status;
        this.amount = amount;
        this.date = date;
        this.mentor = mentor;
        this.mentee = mentee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Booking() {
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }
}
