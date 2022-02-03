package com.innowise.userinfoservice.service.upload;

import com.innowise.userinfoservice.model.entity.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {

    List<Employee> uploadFromFile(MultipartFile file);
}
