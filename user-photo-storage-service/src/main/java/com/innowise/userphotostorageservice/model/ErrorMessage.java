package com.innowise.userphotostorageservice.model;

public enum ErrorMessage {
    PHOTO_NOT_FOUND("Photo with Id %s not found"),
    EMPTY_FILE("Upload file %s is empty");

    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
