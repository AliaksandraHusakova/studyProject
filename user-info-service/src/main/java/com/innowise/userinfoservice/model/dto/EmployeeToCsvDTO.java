package com.innowise.userinfoservice.model.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class EmployeeToCsvDTO {

    @CsvBindByName(column = "EMPLOYEE_ID")
    Integer employeeId;

    @NotBlank
    @CsvBindByName(column = "FIRST_NAME")
    String firstName;

    @NotBlank
    @CsvBindByName(column = "LAST_NAME")
    String lastName;

    @NotBlank
    @CsvBindByName(column = "MIDDLE_NAME")
    String middleName;

    @NotNull
    @CsvBindByName(column = "DATE_OF_BIRTH")
    @CsvDate(value = "yyyy-MM-dd HH:mm:ss.s")
    LocalDate dateOfBirth;

    @CsvBindByName(column = "USER_ID")
    Integer userId;

    @NotBlank
    @CsvBindByName(column = "USERNAME")
    String username;

    @NotBlank
    @CsvBindByName(column = "PASSWORD")
    String password;

    @CsvBindByName(column = "ROLE_ID")
    Integer roleId;

    @NotBlank
    @CsvBindByName(column = "ROLE_NAME")
    String roleName;
}
