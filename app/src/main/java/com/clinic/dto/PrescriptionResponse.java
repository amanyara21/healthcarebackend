package com.clinic.dto;

import com.clinic.model.Prescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrescriptionResponse {
    private UUID id;
    private LocalDate date;
    private DoctorDTO doctor;
    private PatientDTO patient;
    private UUID appointmentId;
    private List<MedicineDTO> medicines;
    private List<LabTestDTO> labTests;

    public PrescriptionResponse(Prescription prescription) {
        this.id = prescription.getId();
        this.date = prescription.getDate();

        this.doctor = new DoctorDTO(
                prescription.getDoctor().getId(),
                prescription.getDoctor().getName(),
               prescription.getDoctor().getSpecialization(),
                prescription.getDoctor().getAppointmentTypes(),
                prescription.getDoctor().getExperience(),
                prescription.getDoctor().getClinicName()
        );

        this.patient = new PatientDTO(
                prescription.getPatient().getId(),
                prescription.getPatient().getEmail(),
                prescription.getPatient().getUsername()
        );

        this.appointmentId = prescription.getAppointment() != null ? prescription.getAppointment().getId() : null;

        this.medicines = prescription.getMedicines().stream()
                .map(MedicineDTO::new)
                .toList();

        this.labTests = prescription.getLabTests().stream()
                .map(LabTestDTO::new)
                .toList();
    }
}

