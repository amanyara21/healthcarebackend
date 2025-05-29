package com.clinic.repository;

import com.clinic.model.BodyPart;
import com.clinic.model.DoctorBodyPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoctorBodyPartRepository extends JpaRepository<DoctorBodyPart, UUID> {
    List<DoctorBodyPart> findByBodyPart(BodyPart bodyPart);
}
