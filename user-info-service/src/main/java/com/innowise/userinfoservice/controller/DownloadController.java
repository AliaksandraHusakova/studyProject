package com.innowise.userinfoservice.controller;

import com.innowise.userinfoservice.constant.ComponentName;
import com.innowise.userinfoservice.constant.ErrorMessage;
import com.innowise.userinfoservice.constant.FileName;
import com.innowise.userinfoservice.service.download.DownloadService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("api/v1/download")
public class DownloadController {

    @Autowired
    private Map<String, DownloadService> downloadServices;

    @Operation(
            summary = "Download employees list",
            description = "Download employees list"
    )
    @GetMapping("/download-employees/{fileFormat}")
    public ResponseEntity<byte[]> downloadEmployees(@PathVariable String fileFormat) {

        switch (fileFormat) {

            case "csv":

                return downloadServices.get(ComponentName.EMPLOYEE_DOWNLOAD_CSV.name).downloadFile(FileName.EMPLOYEES_LIST_CSV.name);

            case "json":

                return downloadServices.get(ComponentName.EMPLOYEE_DOWNLOAD_JSON.name).downloadFile(FileName.EMPLOYEES_LIST_JSON.name);

            default:

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        String.format(ErrorMessage.FILE_FORMAT_NOT_FOUND.message, fileFormat)
                );
        }
    }
}
