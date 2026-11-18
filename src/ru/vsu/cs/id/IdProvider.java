package ru.vsu.cs.id;

public class IdProvider {
    private long currentId = 0;

    public long nextId() {
        currentId++;
        return currentId;
    }
}