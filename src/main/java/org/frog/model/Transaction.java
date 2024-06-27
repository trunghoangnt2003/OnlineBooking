package org.frog.model;


import java.sql.Date;

public class Transaction {

    private int id;

    private Date date;

    private int amount;

    private int fee;

    private TypeTransaction typeTransaction;

    private Wallet wallet;

    private Wallet walletOpposite;

    private Account sender;

    private Account receiver;

    private String description;

    private Status status;

    public Transaction(int id, Date date, int amount, int fee, TypeTransaction typeTransaction, Wallet wallet, Wallet walletOpposite, Account sender, Account receiver, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.fee = fee;
        this.typeTransaction = typeTransaction;
        this.wallet = wallet;
        this.walletOpposite = walletOpposite;
        this.sender = sender;
        this.receiver = receiver;
        this.description = description;
    }

    public Transaction() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Wallet getWalletOpposite() {
        return walletOpposite;
    }

    public void setWalletOpposite(Wallet walletOpposite) {
        this.walletOpposite = walletOpposite;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
