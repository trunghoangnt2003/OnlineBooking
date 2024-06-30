package org.frog.model;

public class Wallet {

    private String id;

    private int  hold;

    private int balance;


    public Wallet(String id, int balance, int hold) {
        this.id = id;
        this.balance = balance;
        this.hold = hold;
    }


    public Wallet() {
        this.hold = 0;
        this.balance = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public float getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }
}
