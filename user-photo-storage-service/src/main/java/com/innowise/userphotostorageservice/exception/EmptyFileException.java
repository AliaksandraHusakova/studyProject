package com.innowise.userphotostorageservice.exception;

import com.innowise.userphotostorageservice.model.ErrorMessage;

public class EmptyFileException extends RuntimeException {

    public EmptyFileException(String name) {
        super(String.format(ErrorMessage.EMPTY_FILE.message, name));
    }

}
