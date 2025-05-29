package com.clinic.controller;

import com.clinic.model.BodyPart;
import com.clinic.service.BodyPartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class BodyPartController {
    private final BodyPartService bodyPartService;

    public BodyPartController(BodyPartService bodyPartService) {
        this.bodyPartService = bodyPartService;
    }

    @GetMapping("/body-parts")
    public ResponseEntity<List<BodyPart>> getBodyParts() {
        return ResponseEntity.ok(bodyPartService.getAllParts());
    }
}

