package com.innowise.userphotostorageservice.exception;

import com.innowise.userphotostorageservice.model.ErrorDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(PhotoNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleEmployeeNotFoundException(RuntimeException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .errorMessage(ex.getMessage())
                .time(LocalDateTime.now())
                .build();

        logger.warn(error);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<ErrorDetail> handlerEmptyFileException(RuntimeException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .errorMessage(ex.getMessage())
                .time(LocalDateTime.now())
                .build();

        logger.warn(error);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}