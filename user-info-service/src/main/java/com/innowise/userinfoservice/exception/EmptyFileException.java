package com.innowise.userinfoservice.exception;

import com.innowise.userinfoservice.constant.ErrorMessage;

public class EmptyFileException extends RuntimeException {

    public EmptyFileException(String name) {
        super(String.format(ErrorMessage.EMPTY_FILE.message, name));
    }

}
