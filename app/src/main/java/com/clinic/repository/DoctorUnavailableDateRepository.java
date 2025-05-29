package com.clinic.repository;

import com.clinic.model.DoctorUnavailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorUnavailableDateRepository extends JpaRepository<DoctorUnavailableDate, Long> {
    List<DoctorUnavailableDate> findAllByDoctor_Id(UUID doctorId);
    boolean existsByDoctor_IdAndUnavailableDate(UUID doctorId, LocalDate date);
}
