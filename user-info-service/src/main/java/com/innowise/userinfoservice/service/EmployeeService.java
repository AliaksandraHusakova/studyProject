package com.innowise.userinfoservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.innowise.userinfoservice.exception.EmployeeNotFoundException;
import com.innowise.userinfoservice.mapper.MapStructMapper;
import com.innowise.userinfoservice.model.FilterEmployee;
import com.innowise.userinfoservice.model.LogMessage;
import com.innowise.userinfoservice.model.Sorting;
import com.innowise.userinfoservice.model.dto.EmployeeToCsvDTO;
import com.innowise.userinfoservice.model.entity.Employee;
import com.innowise.userinfoservice.repository.EmployeeRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.StringBuilderWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
    private final MapStructMapper mapper;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            MapStructMapper mapper
    ) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
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

    public List<Employee> uploadEmployeesFromJson(MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        List<Employee> employees = mapper.readValue(file.getBytes(), new TypeReference<>() {});

        List<Employee> employeesResult = employeeRepository.saveAll(employees);

        logger.info(String.format(LogMessage.ADD_EMPLOYEES_JSON.message, employees.size()));

        return employeesResult;
    }

    public List<Employee> uploadEmployeesFromCsv(MultipartFile file) throws IOException {

        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        CsvToBean<EmployeeToCsvDTO> csvToBean = new CsvToBeanBuilder<EmployeeToCsvDTO>(reader)
                .withSeparator(',')
                .withType(EmployeeToCsvDTO.class)
                .build();

        List<EmployeeToCsvDTO> employeeToCsvDTOS = csvToBean.parse();

        List<Employee> employees = mapper.employeeToCsvDTOsToEmployees(employeeToCsvDTOS);

        List<Employee> employeesResult = employeeRepository.saveAll(employees);

        logger.info(String.format(LogMessage.ADD_EMPLOYEES_CSV.message, employees.size()));

        return employeesResult;
    }

    public byte[] getJsonEmployeesListBytes()
            throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        logger.info(LogMessage.DOWNLOAD_EMPLOYEES.message);

        return writer.writeValueAsBytes(employeeRepository.findAll());
    }

    public byte[] getCsvEmployeesListBytes()
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        Writer writer = new StringBuilderWriter();

        StatefulBeanToCsv<EmployeeToCsvDTO> beanToCsv = new StatefulBeanToCsvBuilder<EmployeeToCsvDTO>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(true)
                .build();

        beanToCsv.write(mapper.employeesToEmployeeToCsvDTOs(employeeRepository.findAll()));

        byte[] bytes = writer.toString().getBytes(StandardCharsets.UTF_8);

        writer.close();

        logger.info(LogMessage.DOWNLOAD_EMPLOYEES.message);

        return bytes;
    }
}