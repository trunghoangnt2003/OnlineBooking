package org.frog.model;


import java.util.ArrayList;

public class Category {

    private int id;

    private String name;

    public ArrayList<Skill> skills;

    public Category(int id, String name, ArrayList<Skill> skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
    }

    public Category() {
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

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }
}
