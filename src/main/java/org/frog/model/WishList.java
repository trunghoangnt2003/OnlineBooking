package org.frog.model;

import java.util.List;

public class WishList {
    private Mentee mentee;
    private List<Mentor> mentors;

    public WishList(Mentee mentee, List<Mentor> mentors) {
        this.mentee = mentee;
        this.mentors = mentors;
    }

    public WishList() {
    }

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }

    public List<Mentor> getMentors() {
        return mentors;
    }

    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
    }

}
