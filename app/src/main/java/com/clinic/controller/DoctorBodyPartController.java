package com.clinic.controller;

import com.clinic.dto.DoctorDTO;
import com.clinic.model.DoctorBodyPart;
import com.clinic.model.DoctorDetails;
import com.clinic.service.DoctorBodyPartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorBodyPartController {

    private final DoctorBodyPartService doctorBodyPartService;

    public DoctorBodyPartController(DoctorBodyPartService doctorBodyPartService) {
        this.doctorBodyPartService = doctorBodyPartService;
    }

    @GetMapping("/users/doctor/by-body-part")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByBodyPart(@RequestParam String bodyPartName) {
        List<DoctorDTO> doctors = doctorBodyPartService.getDoctorsByBodyPart(bodyPartName);
        return ResponseEntity.ok(doctors);
    }

    @PostMapping("/doctor/add-body-part")
    public ResponseEntity<DoctorBodyPart> addDoctorBodyPart(@RequestBody DoctorBodyPart doctorBodyPart) {
        return ResponseEntity.ok(doctorBodyPartService.addDoctorBodyPart(doctorBodyPart));
    }

}
