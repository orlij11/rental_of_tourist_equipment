package ru.vsu.cs.domain;

public class Client {
    private final long id;
    private String fullName;
    private String phone;

    public Client(long id, String fullName, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }
}