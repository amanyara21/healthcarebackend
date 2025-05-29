package com.clinic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "doctor_details")
public class DoctorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"email", "password", "role"})
    private User doctor;

    @Column(nullable = false)
    private String name;

    @Column( nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String clinicName;

    @Column(nullable = false)
    private int experience;

    @Column(nullable = false)
    @ElementCollection(targetClass = AppointmentType.class)
    @CollectionTable(name = "doctor_appointment_types", joinColumns = @JoinColumn(name = "doctor_id"))
    @Enumerated(EnumType.STRING)
    private Set<AppointmentType> appointmentTypes;


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DoctorUnavailableDate> unavailableDates = new HashSet<>();

}

