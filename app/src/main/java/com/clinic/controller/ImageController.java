package com.clinic.controller;

import com.clinic.model.BodyPart;
import com.clinic.service.BodyPartService;
import com.clinic.service.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/upload")
public class ImageController {

    private final CloudinaryService cloudinaryService;
    private final BodyPartService bodyPartService;

    public ImageController(CloudinaryService cloudinaryService, BodyPartService bodyPartService) {
        this.cloudinaryService = cloudinaryService;
        this.bodyPartService = bodyPartService;
    }

    @PostMapping("/pdf")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadPdf(file);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
}
