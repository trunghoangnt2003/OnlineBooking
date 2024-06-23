package org.frog.model;

import java.util.ArrayList;

public class Mentor_CV_Log {
    private Account account;

    private String profileDetail;

    private int price;

    private String experience;

    private String education;

    private ArrayList<Level_Skills> level_skills;

    private Status status;

    public Mentor_CV_Log(Account account, String profileDetail, int price, String experience, String education, ArrayList<Level_Skills> level_skills, Status status) {
        this.account = account;
        this.profileDetail = profileDetail;
        this.price = price;
        this.experience = experience;
        this.education = education;
        this.level_skills = level_skills;
        this.status = status;
    }

    public Mentor_CV_Log() {

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public ArrayList<Level_Skills> getLevel_skills() {
        return level_skills;
    }

    public void setLevel_skills(ArrayList<Level_Skills> level_skills) {
        this.level_skills = level_skills;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
