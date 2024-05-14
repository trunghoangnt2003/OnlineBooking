package org.frog.model;

public class TypeTransaction {
    private int id;
    private String name;

    public TypeTransaction(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeTransaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
