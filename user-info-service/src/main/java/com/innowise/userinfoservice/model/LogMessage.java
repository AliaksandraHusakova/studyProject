package com.innowise.userinfoservice.model;

public enum LogMessage {
    ADD_EMPLOYEE("Add new Employee at database with Id %d"),
    EDIT_EMPLOYEE("Edit Employee with Id %d"),
    DELETE_EMPLOYEE("Delete Employee from database with Id %d"),
    ADD_EMPLOYEES_JSON("Add %d Employees at database from Json"),
    ADD_EMPLOYEES_CSV("Add %d Employees at database from Csv"),
    DOWNLOAD_EMPLOYEES("Download employees list");

    public final String message;

    LogMessage(String message) {
        this.message = message;
    }
}
