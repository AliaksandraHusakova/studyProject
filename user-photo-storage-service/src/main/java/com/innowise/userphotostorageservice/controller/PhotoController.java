package com.innowise.userphotostorageservice.controller;

import com.innowise.userphotostorageservice.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("v1/photo")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Object> downloadPhoto(@PathVariable String id) throws IOException {
        return new ResponseEntity<>(photoService.downloadPhoto(id), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadPhoto(@RequestBody MultipartFile photo) throws IOException {
        return new ResponseEntity<>(photoService.uploadPhoto(photo), HttpStatus.CREATED);
    }
}
