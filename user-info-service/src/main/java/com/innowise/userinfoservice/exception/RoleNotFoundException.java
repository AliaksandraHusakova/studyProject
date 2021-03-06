package com.innowise.userinfoservice.exception;

import com.innowise.userinfoservice.constant.ErrorMessage;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Integer id) {
        super(String.format(ErrorMessage.ROLE_NOT_FOUND.message, id));
    }
}
