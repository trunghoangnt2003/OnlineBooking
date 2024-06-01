package org.frog.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Booking {
    private int id;
    private Status status;
    private int amount;
    private Date date;
    private Mentor mentor;
    private Mentee mentee;
    private Date startDate;
    private Date endDate;
    private String description;
    private Level_Skills level_skills;


    public Booking(int id, Status status, int amount, Date date, Mentor mentor, Mentee mentee, Date startDate, Date endDate) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Level_Skills getLevel_skills() {
        return level_skills;
    }

    public void setLevel_skills(Level_Skills level_skills) {
        this.level_skills = level_skills;
    }



    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", status=" + status.getId() +
                ", amount=" + amount +
                ", date=" + date +
                ", mentor=" + mentor.getAccount().getId() +
                ", mentee=" + mentee+
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", level_skills=" + level_skills.getSkill().getName() + " - " + level_skills.getLevel().getName() +
                '}';
    }
}
