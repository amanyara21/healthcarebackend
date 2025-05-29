package com.clinic.dto;

import com.clinic.model.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDetailsDTO {
    private String name;
    private String specialization;
    private Set<AppointmentType> appointmentTypes;
    private Integer experience;
    private String ClinicName;
}
