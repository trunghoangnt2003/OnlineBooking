package org.frog.model;


import java.util.ArrayList;
import java.util.List;

public class Skill {

    private int id;
    private String name;
    private Category category;
    private String src_icon;

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

    public String getSrc_icon() {
        return src_icon;
    }

    public void setSrc_icon(String src_icon) {
        this.src_icon = src_icon;
    }

    @Override
    public String toString() {
        return "Skill [id=" + id + ", name=" + name + ", category=" + category.getId() + "]";
    }
}
