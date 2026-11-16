package ru.vsu.cs.domain;

public class Equipment {
    private final long id;
    private String name;
    private String inventoryNumber;

    public Equipment(long id, String name, String inventoryNumber) {
        this.id = id;
        this.name = name;
        this.inventoryNumber = inventoryNumber;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }
}