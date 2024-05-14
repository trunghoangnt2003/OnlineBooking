package org.frog.model;

import java.sql.Date;

public class Review {
    private int id;
    private String comment;
    private int rate;
    private Date date;
    private Mentee mentee;
    private Mentor mentor;

    public Review(int id, String comment, int rate, Date date, Mentee mentee, Mentor mentor) {
        this.id = id;
        this.comment = comment;
        this.rate = rate;
        this.date = date;
        this.mentee = mentee;
        this.mentor = mentor;
    }

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }
}
