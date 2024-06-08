package org.frog.model;

import java.util.Date;
import java.util.List;

public class WishList {
    private int id;
    private Mentor mentor;
    private Mentee mentee;
    private Date timeRequest;
    private Status status;


    public WishList(int id, Mentor mentor, Mentee mentee, Date timeRequest, Status status) {
        this.id = id;
        this.mentor = mentor;
        this.mentee = mentee;
        this.timeRequest = timeRequest;
        this.status = status;
    }

    public WishList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getTimeRequest() {
        return timeRequest;
    }

    public void setTimeRequest(Date timeRequest) {
        this.timeRequest = timeRequest;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
