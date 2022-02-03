package com.innowise.userinfoservice.constant;

public enum FileName {
    EMPLOYEES_LIST_CSV("employees.csv"),
    EMPLOYEES_LIST_JSON("employees.json");

    public final String name;

    FileName(String name) {
        this.name = name;
    }
}
