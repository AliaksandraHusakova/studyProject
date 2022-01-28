package com.innowise.userinfoservice.model.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class UserDTO {

    Integer id;

    @NotBlank
    String username;

    @NotBlank
    String password;

    @NotNull
    RoleDTO role;
}
