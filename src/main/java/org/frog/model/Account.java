package org.frog.model;

import javax.persistence.*;
import java.sql.Date;

public class Account {

    private String id;

    private String name;

    private String userName;

    private String password;

    private String avatar;

    private Date dob;

    private String phone;

    private String email;

    private String address;

    private int gender;

    private Wallet wallet;

    private Status status;

    private Role role;

    public Account(String id, String avatar, String name, String userName, String password, Date dob, String phone, String email, String address, int gender, Wallet wallet, Status status, Role role) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.wallet = wallet;
        this.status = status;
        this.role = role;
    }

    public Account() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
