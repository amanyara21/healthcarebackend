package com.clinic.controller;

import com.clinic.dto.DoctorDetailsDTO;
import com.clinic.model.DoctorDetails;
import com.clinic.service.DoctorDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class DoctorDetailsController {

    private final DoctorDetailsService doctorDetailsService;

    public DoctorDetailsController(DoctorDetailsService doctorDetailsService) {
        this.doctorDetailsService = doctorDetailsService;
    }

    @GetMapping("/api/doctors")
    public List<DoctorDetails> getAllDoctors() {
        return doctorDetailsService.getAllDoctors();
    }

    @GetMapping("/api/doctor/{id}")
    public ResponseEntity<DoctorDetails> getDoctorById(@PathVariable UUID id) {
        Optional<DoctorDetails> doctor = doctorDetailsService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/doctor/add-doctor-detail")
    public DoctorDetails createDoctor(@AuthenticationPrincipal UserDetails userDetails, @RequestBody DoctorDetailsDTO doctorDetailsDto) {
        return doctorDetailsService.saveDoctor(userDetails.getUsername(), doctorDetailsDto);
    }
    @GetMapping("/doctor/get-doctor-detail")
    public DoctorDetails getDoctorDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return doctorDetailsService.getDetails(userDetails.getUsername());
    }
}



