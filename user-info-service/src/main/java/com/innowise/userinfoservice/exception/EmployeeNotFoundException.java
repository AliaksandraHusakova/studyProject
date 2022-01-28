package com.innowise.userinfoservice.exception;

import com.innowise.userinfoservice.model.ErrorMessage;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Integer id) {
        super(String.format(ErrorMessage.EMPLOYEE_NOT_FOUND.message, id));
    }
}
