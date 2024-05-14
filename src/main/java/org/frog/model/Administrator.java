package org.frog.model;

public class Administrator {
    private Account account;

    public Administrator(Account account) {
        this.account = account;
    }

    public Administrator() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
