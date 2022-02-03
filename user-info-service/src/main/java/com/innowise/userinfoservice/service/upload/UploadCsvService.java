package com.innowise.userinfoservice.service.upload;

import com.innowise.userinfoservice.constant.ErrorMessage;
import com.innowise.userinfoservice.constant.LogMessage;
import com.innowise.userinfoservice.mapper.MapStructMapper;
import com.innowise.userinfoservice.model.dto.EmployeeToCsvDTO;
import com.innowise.userinfoservice.model.entity.Employee;
import com.innowise.userinfoservice.repository.EmployeeRepository;
import com.innowise.userinfoservice.service.EmployeeService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component("upload-csv")
public class UploadCsvService implements UploadService {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final MapStructMapper mapper;

    public UploadCsvService(
            EmployeeRepository employeeRepository,
            MapStructMapper mapper
    ) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Employee> uploadFromFile(MultipartFile file) {

        try {
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

        } catch (Exception e) {

            logger.error(String.format(ErrorMessage.BAD_FILE_CONTENT.message, file.getOriginalFilename(), e));

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format(ErrorMessage.BAD_FILE_CONTENT.message, file.getOriginalFilename(), e)
            );
        }
    }
}
