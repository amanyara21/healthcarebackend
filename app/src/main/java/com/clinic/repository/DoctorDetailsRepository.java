package com.clinic.repository;

import com.clinic.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, UUID> {
    Optional<DoctorDetails> findByDoctor_Id(UUID userId);
}

