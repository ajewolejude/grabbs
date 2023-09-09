package com.example.grabbs.controller;

import com.example.grabbs.model.Attachment;
import com.example.grabbs.model.Tyre;
import com.example.grabbs.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/files/download/{cloudinaryPublicId}")
    public ResponseEntity<Object> downloadFile(@PathVariable String cloudinaryPublicId) {
        try {
            // Generate a secure URL for downloading the file
            // Generate a secure URL for downloading the file
            URL downloadUrl = attachmentService.downloadFile(cloudinaryPublicId);

            // You can redirect the user to the download URL or send it as a response
            return ResponseEntity.status(HttpStatus.FOUND).location(downloadUrl.toURI()).build();
        } catch (IOException | RuntimeException | URISyntaxException e) {
            // Handle exceptions appropriately (e.g., log errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while downloading file");
        }
    }

}
