package com.innowise.userinfoservice.model;

public enum ErrorMessage {
    EMPLOYEE_NOT_FOUND("Employee with Id %d not found"),
    ROLE_NOT_FOUND("Role with Id %d not bound"),
    EMPTY_FILE("Upload file %s is empty"),
    PARSE("An error occurred while processing the %s file");

    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
