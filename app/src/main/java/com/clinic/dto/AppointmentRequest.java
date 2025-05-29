package com.clinic.dto;

import com.clinic.model.AppointmentType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequest {
    private UUID doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentType appointmentType;
}


