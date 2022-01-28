package com.innowise.userinfoservice.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DownloadService {

    public ResponseEntity<Object> downloadFile(String fileName, byte[] bytes) {
        HttpHeaders header = new HttpHeaders();

        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return new ResponseEntity<>(new ByteArrayResource(bytes), header, HttpStatus.OK);
    }
}
