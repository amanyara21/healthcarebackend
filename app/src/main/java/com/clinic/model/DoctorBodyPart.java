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
@Table(name = "doctor_bodypart")
public class DoctorBodyPart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorDetails doctor;

    @ManyToOne
    @JoinColumn(name = "body_part_id", referencedColumnName = "id")
    private BodyPart bodyPart;
}

