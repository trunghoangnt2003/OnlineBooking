package org.frog.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private int id;
    private Date date;
    private Slot slot;
   /* private Mentor mentor;*/
    private Status status;
    private Mentor_Schedule mentorSchedule;

    public Schedule() {
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

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    /*public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }*/

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Mentor_Schedule getMentorSchedule() {
        return mentorSchedule;
    }

    public void setMentorSchedule(Mentor_Schedule mentorSchedule) {
        this.mentorSchedule = mentorSchedule;
    }
}
