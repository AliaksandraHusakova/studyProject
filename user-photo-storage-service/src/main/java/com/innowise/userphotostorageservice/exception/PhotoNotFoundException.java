package com.innowise.userphotostorageservice.exception;

import com.innowise.userphotostorageservice.model.ErrorMessage;

public class PhotoNotFoundException extends RuntimeException {

    public PhotoNotFoundException(String id) {
        super(String.format(ErrorMessage.PHOTO_NOT_FOUND.message, id));
    }
}
