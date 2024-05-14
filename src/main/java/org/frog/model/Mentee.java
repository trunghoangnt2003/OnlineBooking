package org.frog.model;


public class Mentee {

    private Account account;

    public Mentee(Account account) {
        this.account = account;
    }

    public Mentee() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
