package com.example.grabbs.service;

import com.cloudinary.Transformation;
import com.example.grabbs.model.Attachment;
import com.example.grabbs.repository.AttachmentRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final Cloudinary cloudinary;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository) {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "jhhjhjjhjh",
                "api_key", "1349jv,vjh,hjbjh68433492292",
                "api_secret", "bjhbjhbjhj",
                "secure", true));
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment uploadFile(MultipartFile file, String kind, Long kindId) throws IOException {
        // Upload the file to Cloudinary
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // Create a new Attachment entity and save it to the database
        Attachment attachment = new Attachment();
        attachment.setFilename(file.getOriginalFilename());
        attachment.setCloudinaryPublicId(uploadResult.get("public_id").toString());
        attachment.setKind(kind); // Set the "kind" field
        attachment.setKindId(kindId); // Set the "kind" field
        return attachmentRepository.save(attachment);
    }

    public void deleteFile(String publicId) throws IOException {
        // Delete the file from Cloudinary
        // Initialize Cloudinary
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    public URL downloadFile(String cloudinaryPublicId) throws IOException {
        // Generate a URL for the Cloudinary resource
        Attachment attachment = attachmentRepository.findByCloudinaryPublicId(cloudinaryPublicId);// Define transformation options (if needed)
        // Define transformation options (if needed)
        if (attachment!= null){
            Transformation transformation = new Transformation();
            // Generate a URL for the Cloudinary resource with the specified transformation options
            String urlString = cloudinary.url().resourceType("auto").transformation(transformation).generate(attachment.getCloudinaryPublicId());

            URL url = new URL(urlString);
            return url;

        }
        return null;

    }


    public List<Attachment> findAttachmentsById(Long kindId) {
        return attachmentRepository.findAllByKindId(kindId);
    }
}
