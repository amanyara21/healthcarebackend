package com.clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "billing")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorDetails doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}


