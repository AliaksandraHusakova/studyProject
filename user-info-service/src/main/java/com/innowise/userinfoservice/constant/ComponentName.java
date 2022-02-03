package com.innowise.userinfoservice.constant;

public enum ComponentName {
    EMPLOYEE_DOWNLOAD_CSV("download-csv"),
    EMPLOYEE_DOWNLOAD_JSON("download-json"),
    EMPLOYEE_UPLOAD_CSV("upload-csv"),
    EMPLOYEE_UPLOAD_JSON("upload-json");

    public final String name;

    ComponentName(String name) {
        this.name = name;
    }
}
