package com.innowise.userinfoservice.service;

import com.innowise.userinfoservice.constant.LogMessage;
import com.innowise.userinfoservice.exception.EmployeeNotFoundException;
import com.innowise.userinfoservice.model.FilterEmployee;
import com.innowise.userinfoservice.model.Sorting;
import com.innowise.userinfoservice.model.entity.Employee;
import com.innowise.userinfoservice.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Value("${default.employee-pagination.pageSize}")
    private String pageSize;

    @Value("${default.employee-pagination.sortField}")
    private String sortField;

    @Value("${default.employee-pagination.sortDir}")
    private String sortDir;

    @Value("${default.employee-pagination.pageNumber}")
    private String pageNumber;

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public List<Employee> findAllEmployees() {

        Pageable pageable = PageRequest.of(
                Integer.parseInt(pageNumber),
                Integer.parseInt(pageSize),
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                        Sort.by(sortField).ascending() : Sort.by(sortField).descending()
        );

        return employeeRepository.findAll(pageable).getContent();
    }

    public List<Employee> getFilteredEmployees(FilterEmployee filterEmployee, Sorting sort) {

        Pageable pageable = PageRequest.of(
                sort.getPageNumber() * Integer.parseInt(pageSize),
                Integer.parseInt(pageSize),
                sort.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                        Sort.by(sort.getSortField()).ascending() : Sort.by(sort.getSortField()).descending()
        );

        return employeeRepository.findAll(
                Specification.where(
                        EmployeeSpecifications.firstNameLike(filterEmployee.getFirstName())
                                .and(EmployeeSpecifications.lastNameLike(filterEmployee.getLastName()))
                                .and(EmployeeSpecifications.middleNameLike(filterEmployee.getMiddleName()))
                                .and(EmployeeSpecifications.dateOfBirthGreaterThan(filterEmployee.getDateFrom()))
                                .and(EmployeeSpecifications.dateOfBirthLessThan(filterEmployee.getDateTo()))
                                .and(EmployeeSpecifications.roleIn(filterEmployee.getRoleIds()))),
                pageable).getContent();
    }

    public Employee addEmployee(Employee employee) {

        Employee employeeResult = employeeRepository.save(employee);

        logger.info(String.format(LogMessage.ADD_EMPLOYEE.message, employeeResult.getId()));

        return employeeResult;
    }

    public Employee editEmployee(Employee employee, Integer id) {

        employee.setId(id);

        Employee employeeResult = employeeRepository.save(employee);

        logger.info(String.format(LogMessage.EDIT_EMPLOYEE.message, id));

        return employeeResult;
    }

    public void deleteEmployee(Integer id) {

        employeeRepository.delete(findEmployeeById(id));

        logger.info(String.format(LogMessage.DELETE_EMPLOYEE.message, id));
    }
}