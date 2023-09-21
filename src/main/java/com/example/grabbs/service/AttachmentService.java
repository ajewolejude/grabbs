package com.example.grabbs.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.cloudinary.Transformation;
import com.example.grabbs.model.Attachment;
import com.example.grabbs.repository.AttachmentRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    private final AttachmentRepository attachmentRepository;

    private Logger logger = LoggerFactory.getLogger(AttachmentService.class);

    @Autowired
    private S3BucketStorageService s3BucketStorageService;


    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository, S3BucketStorageService s3BucketStorageService) {
        this.attachmentRepository = attachmentRepository;
        this.s3BucketStorageService = s3BucketStorageService;
    }

    public Optional<Attachment> findById(Long id){
        return attachmentRepository.findById(id);
    }

    public Attachment uploadFile(MultipartFile file, String kind, Long kindId) throws IOException {
        // Upload the file to AWS
        String keyName = LocalDateTime.now() + file.getOriginalFilename();
        String fileUrl = s3BucketStorageService.uploadFile(keyName, file);
        // Create a new Attachment entity and save it to the database
        Attachment attachment = new Attachment();
        attachment.setKeyName(keyName);
        attachment.setFilename(file.getOriginalFilename());
        attachment.setPublicUrl(fileUrl);
        attachment.setKind(kind); // Set the "kind" field
        attachment.setKindId(kindId); // Set the "kind" field
        return attachmentRepository.save(attachment);
    }

    public void deleteFile( Attachment attachment) throws IOException {
        if(s3BucketStorageService.deleteFile(attachment.getKeyName())) {
            //attachmentRepository.delete(attachment);
        }
    }

    /**
     * Downloads file using amazon S3 client from S3 bucket
     *
     * @param keyName
     * @return ByteArrayOutputStream
     */
    public S3ObjectInputStream downloadFile(String keyName) {
        return s3BucketStorageService.downloadFile(keyName);
    }



    public List<Attachment> findAttachmentsByKindIdAndKind(Long kindId, String kind) {
        return attachmentRepository.findAllByKindIdAndKind(kindId, kind);
    }
    public Attachment findAttachmentsByKeyName(String keyName) {
        return attachmentRepository.findAttachmentsByKeyName(keyName);
    }
}
