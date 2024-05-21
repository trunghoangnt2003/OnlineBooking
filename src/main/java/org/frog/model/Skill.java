package org.frog.model;


import java.util.ArrayList;
import java.util.List;

public class Skill {

    private int id;
    private String name;
    private Category category;
    private ArrayList<Mentor> mentors;

    public Skill(int id, String name, Category category, ArrayList<Mentor> mentors) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.mentors = mentors;
    }

    public Skill() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public ArrayList<Mentor> getMentors() {
        return mentors;
    }

    public void setMentors(ArrayList<Mentor> mentors) {
        this.mentors = mentors;
    }

    @Override
    public String toString() {
       return "Skill [id=" + id + ", name=" + name + ", category=" + category.getId() + "]";
    }
}
