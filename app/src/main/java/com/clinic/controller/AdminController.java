package com.clinic.controller;

import com.clinic.dto.JwtResponseDTO;
import com.clinic.dto.RegisterRequest;
import com.clinic.model.BodyPart;
import com.clinic.service.AuthService;
import com.clinic.service.BodyPartService;
import com.clinic.service.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final CloudinaryService cloudinaryService;
    private final BodyPartService bodyPartService;

    public AdminController(AuthService authService, CloudinaryService cloudinaryService, BodyPartService bodyPartService) {
        this.authService = authService;
        this.cloudinaryService = cloudinaryService;
        this.bodyPartService = bodyPartService;
    }

    @PostMapping("/create-doctor")
    public ResponseEntity<?> createDoctor(@RequestBody RegisterRequest request) {
        authService.register(request, "doctor");
        return ResponseEntity.ok("Doctor Created Successfully");
    }

    @PostMapping("/create-admin")
    public ResponseEntity<JwtResponseDTO> createAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request, "admin"));
    }
    @PostMapping("/add-body-part")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) {

        try {

            String imageUrl = cloudinaryService.uploadImage(file);

            BodyPart bodyPart = new BodyPart();
            bodyPart.setName(name);
            bodyPart.setImageUrl(imageUrl);
            bodyPartService.saveBodyPart(bodyPart);

            return ResponseEntity.ok("Image uploaded and saved successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Image upload failed: " + e.getMessage());
        }
    }

}
