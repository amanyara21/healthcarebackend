package com.clinic.controller;

import com.clinic.dto.AddUnavailableDatesRequest;
import com.clinic.model.BodyPart;
import com.clinic.service.DoctorAvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService doctorAvailabilityService;

    public DoctorAvailabilityController(DoctorAvailabilityService doctorAvailabilityService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }

    @PostMapping("/doctor/unavailable-dates")
    public ResponseEntity<String> addUnavailableDates(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddUnavailableDatesRequest request) {
        doctorAvailabilityService.addUnavailableDates(userDetails.getUsername(), request.getDates());
        return ResponseEntity.ok("Unavailable Dates Added Successfully");
    }

    @GetMapping("/api/{doctorId}/unavailable-dates")
    public ResponseEntity<List<LocalDate>> getUnavailableDates(@PathVariable UUID doctorId) {
        List<LocalDate> dates = doctorAvailabilityService.getUnavailableDatesByDoctorId(doctorId);
        return ResponseEntity.ok(dates);
    }
}
