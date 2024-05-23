package org.frog.model;


import java.util.ArrayList;

public class Mentor {

    private Account account;

    private String profileDetail;

    private float price;

    private String experience;

    private String education;

    private float rating;

    private int totalBookings;

    private ArrayList<Level_Skills> level_skills;

    public Mentor() {

    }

    public Mentor(Account account, String profileDetail, float price, String experience, String education, float rating) {
        this.account = account;
        this.profileDetail = profileDetail;
        this.price = price;
        this.experience = experience;
        this.education = education;
        this.rating = rating;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getProfileDetail() {
        return profileDetail;
    }

    public void setProfileDetail(String profileDetail) {
        this.profileDetail = profileDetail;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public ArrayList<Level_Skills> getLevel_skills() {
        return level_skills;
    }

    public void setLevel_skills(ArrayList<Level_Skills> level_skills) {
        this.level_skills = level_skills;
    }
}
