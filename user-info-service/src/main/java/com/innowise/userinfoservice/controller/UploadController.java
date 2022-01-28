package com.innowise.userinfoservice.controller;

import com.innowise.userinfoservice.exception.EmptyFileException;
import com.innowise.userinfoservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/upload")
public class UploadController {

    private final EmployeeService employeeService;

    public UploadController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Загрузить список employee (*.csv)",
            description = "Загрузить список employee (*.csv)"
    )
    @PostMapping(value = "upload_employees_from_json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadEmployeesFromJson(@RequestPart("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new EmptyFileException(file.getOriginalFilename());
        } else {
            return new ResponseEntity<>(employeeService.uploadEmployeesFromJson(file), HttpStatus.CREATED);
        }
    }

    @Operation(
            summary = "Загрузить список employee (*.csv)",
            description = "Загрузить список employee (*.csv)"
    )
    @PostMapping(value = "upload_employees_from_csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadEmployeesFromCsv(@RequestPart("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new EmptyFileException(file.getOriginalFilename());
        } else {
            return new ResponseEntity<>(employeeService.uploadEmployeesFromCsv(file), HttpStatus.CREATED);
        }
    }
}
