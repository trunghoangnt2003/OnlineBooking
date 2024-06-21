package org.frog.model;


import java.sql.Date;

public class Transaction {

    private int id;

    private Date date;

    private float amount;

    private float fee;

    private TypeTransaction typeTransaction;

    private Wallet wallet;

    private Wallet walletOpposite;

    private Account account;

    private String description;

    private Status status;

    public Transaction(int id, Date date, float amount, float fee, TypeTransaction typeTransaction, Wallet wallet, Wallet walletOpposite, Account account, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.fee = fee;
        this.typeTransaction = typeTransaction;
        this.wallet = wallet;
        this.walletOpposite = walletOpposite;
        this.account = account;
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

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
