package org.frog.model;


public class Mentor {

    private Account account;

    private String profileDetail;

    private float price;

    private String experience;

    private String education;

    public Mentor(Account account, String profileDetail, float price, String experience, String education) {
        this.account = account;
        this.profileDetail = profileDetail;
        this.price = price;
        this.experience = experience;
        this.education = education;
    }

    public Mentor() {

    }

    public Mentor(Account account) {
        this.account = account;
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
}
