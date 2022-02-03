package com.innowise.userinfoservice.controller;

import com.innowise.userinfoservice.constant.ComponentName;
import com.innowise.userinfoservice.constant.ErrorMessage;
import com.innowise.userinfoservice.exception.EmptyFileException;
import com.innowise.userinfoservice.model.entity.Employee;
import com.innowise.userinfoservice.service.upload.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/upload")
public class UploadController {

    @Autowired
    private Map<String, UploadService> uploadServices;

    @Operation(
            summary = "Upload employees list",
            description = "Upload employees list"
    )
    @PostMapping(value = "/upload-employees", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Employee>> uploadEmployees(
            @RequestPart("file") MultipartFile file,
            @RequestParam String fileFormat
    ) {

        if (file.isEmpty()) {
            throw new EmptyFileException(file.getOriginalFilename());
        } else {

            switch (fileFormat) {

                case "csv":
                    return new ResponseEntity<>(
                            uploadServices.get(ComponentName.EMPLOYEE_UPLOAD_CSV.name).uploadFromFile(file),
                            HttpStatus.CREATED
                    );

                case "json":
                    return new ResponseEntity<>(
                            uploadServices.get(ComponentName.EMPLOYEE_UPLOAD_JSON.name).uploadFromFile(file),
                            HttpStatus.CREATED
                    );

                default:
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            String.format(ErrorMessage.FILE_FORMAT_NOT_FOUND.message, fileFormat)
                    );
            }
        }
    }
}
