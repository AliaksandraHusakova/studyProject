package com.innowise.userinfoservice.exception;

import com.innowise.userinfoservice.model.ErrorDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({
            EmployeeNotFoundException.class,
            RoleNotFoundException.class,
            EntityNotFoundException.class
    })
    public ResponseEntity<ErrorDetail> handleEmployeeNotFoundException(RuntimeException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .errorMessage(ex.getMessage())
                .time(LocalDateTime.now())
                .build();

        logger.warn(error);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            EmptyFileException.class,
            ParseException.class
    })
    public ResponseEntity<ErrorDetail> handlerEmptyFileException(RuntimeException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .errorMessage(ex.getMessage())
                .time(LocalDateTime.now())
                .build();

        logger.warn(error);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        logger.warn(errors);

        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetail> constraintViolationValidationException(ConstraintViolationException ex) {

        ErrorDetail error = ErrorDetail.builder()
                .errorMessage(ex.getMessage())
                .time(LocalDateTime.now())
                .build();

        logger.warn(error);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}