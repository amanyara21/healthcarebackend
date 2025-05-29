package com.clinic.dto;

import com.clinic.model.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private UUID id;
    private String appoinmentId;
    private PatientDTO patient;
    private DoctorDTO doctor;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private PaymentStatus paymentStatus;
    private AppointmentType appointmentType;

    public AppointmentResponse(Appointment appointment) {
        this.id = appointment.getId();
        this.appoinmentId = appointment.getAppointmentId();
        this.appointmentDate = appointment.getAppointmentDate();
        this.appointmentTime = appointment.getAppointmentTime();
        this.status = appointment.getStatus();
        this.paymentStatus = appointment.getPaymentStatus();
        this.appointmentType = appointment.getAppointmentType();

        User patient = appointment.getPatient();
        this.patient = new PatientDTO(patient.getId(), patient.getEmail(), patient.getUsername());

        DoctorDetails doctor = appointment.getDoctor();
        this.doctor = new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getAppointmentTypes(),
                doctor.getExperience(),
                doctor.getClinicName()
        );
    }
}

