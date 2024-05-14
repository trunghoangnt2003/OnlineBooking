package org.frog.model;

import java.sql.Date;

public class Report {
    private int id;
    private String reason;
    private Date date;
    private Status status;
    private Mentee mentee;
    private Mentor mentor;
    private Administrator administrator;

    public Report(int id, String reason, Date date, Status status, Mentee mentee, Mentor mentor, Administrator administrator) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.status = status;
        this.mentee = mentee;
        this.mentor = mentor;
        this.administrator = administrator;
    }

    public Report() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
