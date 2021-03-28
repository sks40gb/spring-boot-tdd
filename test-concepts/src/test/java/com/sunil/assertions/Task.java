package com.sunil.assertions;

import lombok.Data;

@Data
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
}
