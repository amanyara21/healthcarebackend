package com.clinic.dto;

import com.clinic.model.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private UUID id;
    private String name;
    private String specialization;
    private Set<AppointmentType> appointmentTypes;
    private Integer experience;
    private String ClinicName;
}
