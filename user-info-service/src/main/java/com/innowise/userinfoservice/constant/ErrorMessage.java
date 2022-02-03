package com.innowise.userinfoservice.constant;

public enum ErrorMessage {
    EMPLOYEE_NOT_FOUND("Employee with Id %d not found"),
    ROLE_NOT_FOUND("Role with Id %d not found"),
    EMPTY_FILE("Upload file %s is empty"),
    PARSE("An error occurred while processing the %s file"),
    FILE_FORMAT_NOT_FOUND("File format %s not found"),
    BAD_FILE_CONTENT("Upload file %s content is incorrect: %s"),
    RESULT_FILE_GENERATION("Failed to create result file: %s");

    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
