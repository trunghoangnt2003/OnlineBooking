package org.frog.model;

import javax.persistence.*;
import java.sql.Date;
@Entity(name = "Account")
public class Account {
    @Id
    private String id;

    @Column(name = "name")
    private String name;
    @Column(name="username")
    private String userName;
    @Column()
    private String password;
    @Column()
    private Date dob;
    @Column()
    private String phone;
    @Column(name = "mail")
    private String email;
    @Column
    private String address;

    public Account(String id, String name, String userName, String password, Date dob, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Account() {

    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
