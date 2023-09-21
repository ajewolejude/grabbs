package com.example.grabbs.controller;

import com.example.grabbs.model.Attachment;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

//    @PostMapping("/upload")
//    public String uploadFiles(@RequestParam("files") List<MultipartFile> files, @RequestParam("kind") String kind, Model model) {
//        if (files.isEmpty()) {
//            return "No files uploaded!";
//        }
//        List<Attachment> uploadedFiles = new ArrayList<>();
//        for (MultipartFile file : files) {
//            try {
//                Attachment uploadedFile = attachmentService.uploadFile(file, kind);
//                uploadedFiles.add(uploadedFile);
//            } catch (IOException e) {
//                // Handle file upload error.
//                model.addAttribute("error", "Failed to upload one or more files.");
//                return "upload"; // Return to the upload form with an error message.
//            }
//        }
//
//        // Optionally, you can redirect to a success page or display a success message.
//        return "redirect:/success"; // Redirect to a success page.
//    }

//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadFiles( @RequestParam("files") List<MultipartFile> files) {
//        List<String> uploadedFileNames = new ArrayList<>();
//
//        for (MultipartFile file : files) {
//            // Process each uploaded file
//            if (!file.isEmpty()) {
//                // You can save, process, or return information about the uploaded file here.
//                // For this example, we're returning the file name.
//                String fileName = file.getOriginalFilename();
//                uploadedFileNames.add(fileName);
//            }
//        }
//
//        return ResponseEntity.ok(uploadedFileNames);
//    }

//
//    @PostMapping("/files/upload")
//    public String uploadFile(Model model, @RequestParam("files") List<MultipartFile> files, @RequestParam("kind") String kind) throws IOException {
//        String message = "";
//
//        List<String> uploadedFileNames = new ArrayList<>();
//
//        List<Attachment> uploadedFiles = new ArrayList<>();
//        for (MultipartFile file : files) {
//            try {
//                Attachment uploadedFile = attachmentService.uploadFile(file, kind);
//                uploadedFiles.add(uploadedFile);
//            } catch (IOException e) {
//                // Handle file upload error.
//                model.addAttribute("error", "Failed to upload one or more files.");
//                return "tyre/add?error_attachment"; // Return to the upload form with an error message.
//            }
//        }
//
//        model.addAttribute("tyre", new Tyre());
//        model.addAttribute("template", "layout");
//        model.addAttribute("title", "Add new Tyre");
//        model.addAttribute("item", "Tyre");
//        return "tyre/add?success_attachment";
//
//    }

    @GetMapping("/download/{keyName}")
    public ResponseEntity<Object> downloadFile(@PathVariable String keyName) {
        InputStream downloadInputStream = attachmentService.downloadFile(keyName);

        if (downloadInputStream != null) {

            // Determine the content type based on the file extension (as you did)
            Attachment attachment = attachmentService.findAttachmentsByKeyName(keyName);
            MediaType mediaType = contentType(attachment.getFilename());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            headers.setContentDispositionFormData("attachment", keyName);

            InputStreamResource resource = new InputStreamResource(downloadInputStream);


            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            // Handle the case where the file wasn't found
            // You can return an error page or a response as needed
            return ResponseEntity.notFound().build();
        }
    }

    private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1].toLowerCase(); // Convert to lowercase for case-insensitive comparison

        switch (fileExtension) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) throws IOException {
        Optional<Attachment> attachment = attachmentService.findById(id);
        if (attachment.isPresent()) {
            attachmentService.deleteFile(attachment.get());
            return ResponseEntity.ok("File deleted!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
