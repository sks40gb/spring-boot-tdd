package com.sunil.assertions;

public class Task {

    private final long id;
    private String summary;
    private String description;
    private int year;

    public Task(long id, String summary, String description) {
        this.id = id;
        this.summary = summary;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }
}
