package com.innowise.userinfoservice.model.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Value
public class RoleDTO {

    Integer id;

    @NotBlank
    String name;

    @NotEmpty
    Set<OperationDTO> operations;
}
