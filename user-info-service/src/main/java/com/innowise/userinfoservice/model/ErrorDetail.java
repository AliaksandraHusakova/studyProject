package com.innowise.userinfoservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDetail {
    private String errorMessage;
    private LocalDateTime time;
}
