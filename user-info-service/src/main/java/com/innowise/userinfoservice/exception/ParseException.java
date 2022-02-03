package com.innowise.userinfoservice.exception;

import com.innowise.userinfoservice.constant.ErrorMessage;

public class ParseException extends RuntimeException{

    public ParseException(String name) {
        super(String.format(ErrorMessage.PARSE.message, name));
    }
}
