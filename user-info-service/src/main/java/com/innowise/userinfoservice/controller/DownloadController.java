package com.innowise.userinfoservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.innowise.userinfoservice.service.DownloadService;
import com.innowise.userinfoservice.service.EmployeeService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/download")
public class DownloadController {

    private final EmployeeService employeeService;
    private final DownloadService downloadService;

    public DownloadController(EmployeeService employeeService, DownloadService downloadService) {
        this.employeeService = employeeService;
        this.downloadService = downloadService;
    }

    @Operation(
            summary = "Скачать список employee (*.json)",
            description = "Скачать список employee (*.json)"
    )
    @GetMapping("download_employees_json")
    public ResponseEntity<Object> downloadEmployeesJson() throws JsonProcessingException {

        return downloadService.downloadFile("employees.json", employeeService.getJsonEmployeesListBytes());
    }

    @Operation(
            summary = "Скачать список employee (*.csv)",
            description = "Скачать список employee (*.csv)"
    )
    @GetMapping("download_employees_csv")
    public ResponseEntity<Object> downloadEmployeesCsv()
            throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        
        return downloadService.downloadFile("roles.csv", employeeService.getCsvEmployeesListBytes());
    }
}
