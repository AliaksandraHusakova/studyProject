package com.innowise.userinfoservice.service.download;

import com.innowise.userinfoservice.constant.ErrorMessage;
import com.innowise.userinfoservice.constant.LogMessage;
import com.innowise.userinfoservice.mapper.MapStructMapper;
import com.innowise.userinfoservice.model.dto.EmployeeToCsvDTO;
import com.innowise.userinfoservice.repository.EmployeeRepository;
import com.innowise.userinfoservice.service.EmployeeService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.StringBuilderWriter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Component("download-csv")
public class DownloadCsvService implements DownloadService {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final MapStructMapper mapper;

    public DownloadCsvService(EmployeeRepository employeeRepository, MapStructMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public byte[] downloadEmployees() {

        try {
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

        } catch (Exception e) {

            logger.error(String.format(ErrorMessage.RESULT_FILE_GENERATION.message, e.getMessage()));

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format(ErrorMessage.RESULT_FILE_GENERATION.message, e.getMessage())
            );
        }
    }
}
