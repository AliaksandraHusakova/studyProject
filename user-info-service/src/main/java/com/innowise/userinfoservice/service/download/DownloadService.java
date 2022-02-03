package com.innowise.userinfoservice.service.download;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface DownloadService {

    default ResponseEntity<byte[]> downloadFile(String fileName) {
        HttpHeaders header = new HttpHeaders();

        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return new ResponseEntity<>(downloadEmployees(), header, HttpStatus.OK);
    }

    byte[] downloadEmployees();
}
