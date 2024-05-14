package org.frog.model;

import java.util.Date;

public class Chat {
    private int id;
    private Date date;
    private String message;
    private Mentor mentor;
    private Mentee mentee;

    public Chat(int id, Date date, String message, Mentor mentor, Mentee mentee) {
        this.id = id;
        this.date = date;
        this.message = message;
        this.mentor = mentor;
        this.mentee = mentee;
    }

    public Chat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
