package org.frog.model;

public class Wallet {

    private String id;

    private float hold;

    private float balance;


    public Wallet(String id, float balance, float hold) {
        this.id = id;
        this.balance = balance;
        this.hold = hold;
    }


    public Wallet() {
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

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getHold() {
        return hold;
    }

    public void setHold(float hold) {
        this.hold = hold;
    }
}
