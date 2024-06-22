package org.frog.model;

public class Wallet {

    private int id;

    private float balance;
    private float available;

    public Wallet(int id, float balance, float available) {
        this.id = id;
        this.balance = balance;
        this.available = available;
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

    public float getAvailable() {
        return available;
    }

    public void setAvailable(float available) {
        this.available = available;
    }
}
