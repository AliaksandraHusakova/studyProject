package com.innowise.userphotostorageservice.model;

public enum LogMessage {
    DOWNLOAD_PHOTO("Download photo with Id %s"),
    UPLOAD_PHOTO("Upload photo with Id %d");

    public final String message;

    LogMessage(String message) {
        this.message = message;
    }
}
