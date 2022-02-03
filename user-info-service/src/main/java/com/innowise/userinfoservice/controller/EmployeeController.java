package com.innowise.userinfoservice.controller;

import com.innowise.userinfoservice.mapper.MapStructMapper;
import com.innowise.userinfoservice.model.FilterEmployee;
import com.innowise.userinfoservice.model.Sorting;
import com.innowise.userinfoservice.model.dto.EmployeeDTO;
import com.innowise.userinfoservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final MapStructMapper mapper;

    private static final int MIN_ID = 1;

    public EmployeeController(EmployeeService employeeService, MapStructMapper mapper) {
        this.employeeService = employeeService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Find all employee",
            description = "Find all employee"
    )
    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        return new ResponseEntity<>(employeeService.findAllEmployees(), HttpStatus.OK);
    }

    @Operation(
            summary = "Find employee by id",
            description = "Find employee by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> findEmployeeById(
            @PathVariable @Min(MIN_ID) int id
    ) {

        return new ResponseEntity<>(employeeService.findEmployeeById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Filter employee",
            description = "Filter employee"
    )
    @GetMapping("/get-filtered-employees")
    public ResponseEntity<Object> getFilteredEmployees(
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo,
            @RequestParam(required = false) List<Integer> roleIds,
            @RequestParam int pageNumber,
            @RequestParam String sortField,
            @RequestParam String sortDir
    ) {

        FilterEmployee filterEmployee = FilterEmployee.builder()
                .lastName(lastName)
                .firstName(firstName)
                .middleName(middleName)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .roleIds(roleIds)
                .build();

        Sorting sort = Sorting.builder()
                .pageNumber(pageNumber)
                .sortField(sortField)
                .sortDir(sortDir)
                .build();

        return new ResponseEntity<>(
                employeeService.getFilteredEmployees(filterEmployee, sort), HttpStatus.OK
        );
    }

    @Operation(
            summary = "Add employee",
            description = "Add employee"
    )
    @PostMapping("/add")
    public ResponseEntity<Object> addEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO
    ) {
        return new ResponseEntity<>(
                employeeService.addEmployee(mapper.employeeDTOtoEmployee(employeeDTO)), HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Edit employee",
            description = "Edit employee"
    )
    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO,
            @PathVariable @Min(MIN_ID) Integer id
    ) {

        if (employeeService.findEmployeeById(id) != null) {
            return new ResponseEntity<>(
                    employeeService.editEmployee(mapper.employeeDTOtoEmployee(employeeDTO), id), HttpStatus.OK
            );
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(
            summary = "Delete employee",
            description = "Delete employee"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteEmployee(
            @PathVariable @Min(MIN_ID) Integer id
    ) {

        employeeService.deleteEmployee(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
