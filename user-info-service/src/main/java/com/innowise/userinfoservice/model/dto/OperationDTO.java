package com.innowise.userinfoservice.model.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class OperationDTO {

    Integer id;

    @NotBlank
    String name;
}
