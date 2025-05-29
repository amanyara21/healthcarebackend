package com.clinic.controller;

import com.clinic.dto.AppointmentRequest;
import com.clinic.dto.AppointmentResponse;
import com.clinic.model.Appointment;
import com.clinic.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/user/appointments/book")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @RequestBody AppointmentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(appointmentService.bookAppointment(request, userDetails.getUsername()));
    }
    @GetMapping("/user/appointments/upcoming")
    public ResponseEntity<List<AppointmentResponse>> getUpcomingAppointments(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsByPatient(userDetails.getUsername()));
    }

    @GetMapping("/user/appointments/past")
    public ResponseEntity<List<AppointmentResponse>> getPastAppointments(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(appointmentService.getPastAppointmentsByPatient(userDetails.getUsername()));
    }

    @GetMapping("/api/appointment/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(
            @PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/doctor/appointments/{date}")
    public List<AppointmentResponse> getAppointmentsByDoctor(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        return appointmentService.getAppointmentsByDoctorAndDate(userDetails.getUsername(), appointmentDate);
    }

    @GetMapping("/api/available-slots/{doctorId}/{date}")
    public List<LocalTime> getAvailableSlots(
            @PathVariable UUID doctorId,
            @PathVariable String date
    ) {
        LocalDate appointmentDate = LocalDate.parse(date);
        return appointmentService.getAvailableSlots(doctorId, appointmentDate);
    }

    @PutMapping("/user/appointments/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("Is calling");
        appointmentService.cancelAppointment(id, userDetails.getUsername());
        return ResponseEntity.ok("Appointment cancelled successfully");
    }

}



