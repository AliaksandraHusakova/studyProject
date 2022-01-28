package com.innowise.userinfoservice.controller;

import com.innowise.userinfoservice.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("api/v1/role")
public class RoleController {

    private final RoleService roleService;

    private static final int MIN_ID = 1;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(
            summary = "Найти role по id",
            description = "Найти role по id"
    )
    @GetMapping("{id}")
    public ResponseEntity<Object> findRoleById(
            @PathVariable @Min(MIN_ID) int id
    ) {

        return new ResponseEntity<>(roleService.findRoleById(id), HttpStatus.OK);
    }
}
