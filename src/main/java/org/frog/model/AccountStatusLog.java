package org.frog.model;

import java.sql.Date;

public class AccountStatusLog {
    private int id;
    private Account account;
    private Administrator administrator;
    private Date date;
    private Status status;

    public AccountStatusLog(int id, Account account, Administrator administrator, Date date, Status status) {
        this.id = id;
        this.account = account;
        this.administrator = administrator;
        this.date = date;
        this.status = status;
    }

    public AccountStatusLog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
