package com.innowise.userinfoservice.model.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
public class EmployeeDTO {

    Integer id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String middleName;

    @NotNull
    LocalDate dateOfBirth;

    @NotNull
    UserDTO user;
}
