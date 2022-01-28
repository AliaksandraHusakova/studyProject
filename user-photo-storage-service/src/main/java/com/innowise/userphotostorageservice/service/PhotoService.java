package com.innowise.userphotostorageservice.service;

import com.innowise.userphotostorageservice.exception.PhotoNotFoundException;
import com.innowise.userphotostorageservice.model.LogMessage;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.util.Optional.ofNullable;

@Service
public class PhotoService {

    private static final Logger logger = LogManager.getLogger(PhotoService.class);

    private final GridFsTemplate template;
    private final GridFsOperations operations;

    public PhotoService(GridFsTemplate template, GridFsOperations operations) {
        this.template = template;
        this.operations = operations;
    }

    public String uploadPhoto(MultipartFile photo) throws IOException {

        DBObject metadata = new BasicDBObject();

        metadata.put("fileSize", photo.getSize());

        Object fieldId = template.store(
                photo.getInputStream(),
                photo.getOriginalFilename(),
                photo.getContentType(),
                metadata);

        logger.info(String.format(LogMessage.UPLOAD_PHOTO.message, fieldId));

        return fieldId.toString();
    }

    public byte[] downloadPhoto(String id) throws IOException {

        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );

        if (gridFSFile != null) {
            logger.info(String.format(LogMessage.DOWNLOAD_PHOTO.message, id));

            return IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream());
        } else {
            throw new PhotoNotFoundException(id);
        }
    }
}
