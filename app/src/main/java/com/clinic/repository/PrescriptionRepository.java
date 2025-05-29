package com.clinic.repository;

import com.clinic.model.Appointment;
import com.clinic.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
    List<Prescription> findByAppointmentId(UUID appointmentId);
    List<Prescription> findByPatientId(UUID patientId);
}
