package com.innowise.userinfoservice.service.download;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.innowise.userinfoservice.constant.ErrorMessage;
import com.innowise.userinfoservice.constant.LogMessage;
import com.innowise.userinfoservice.repository.EmployeeRepository;
import com.innowise.userinfoservice.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component("download-json")
public class DownloadJsonService implements DownloadService {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public DownloadJsonService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public byte[] downloadEmployees() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

            logger.info(LogMessage.DOWNLOAD_EMPLOYEES.message);

            return writer.writeValueAsBytes(employeeRepository.findAll());

        } catch (Exception e) {

            logger.error(String.format(ErrorMessage.RESULT_FILE_GENERATION.message, e.getMessage()));

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format(ErrorMessage.RESULT_FILE_GENERATION.message, e.getMessage())
            );
        }
    }
}
