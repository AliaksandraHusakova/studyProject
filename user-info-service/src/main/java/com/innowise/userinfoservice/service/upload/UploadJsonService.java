package com.innowise.userinfoservice.service.upload;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.userinfoservice.constant.ErrorMessage;
import com.innowise.userinfoservice.constant.LogMessage;
import com.innowise.userinfoservice.model.entity.Employee;
import com.innowise.userinfoservice.repository.EmployeeRepository;
import com.innowise.userinfoservice.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component("upload-json")
public class UploadJsonService implements UploadService {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public UploadJsonService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> uploadFromFile(MultipartFile file) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            List<Employee> employees = mapper.readValue(file.getBytes(), new TypeReference<>() {});

            List<Employee> employeesResult = employeeRepository.saveAll(employees);

            logger.info(String.format(LogMessage.ADD_EMPLOYEES_JSON.message, employees.size()));

            return employeesResult;
        }  catch (Exception e) {

            logger.error(String.format(ErrorMessage.BAD_FILE_CONTENT.message, file.getOriginalFilename(), e));

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format(ErrorMessage.BAD_FILE_CONTENT.message, file.getOriginalFilename(), e)
            );
        }
    }
}
