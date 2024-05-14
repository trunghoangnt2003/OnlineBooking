package org.frog.model;

public class Wallet {

    private int id;

    private float balance;

    public Wallet(int id, float balance) {
        this.id = id;
        this.balance = balance;
    }

    public Wallet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
