package com.clinic.repository;

import com.clinic.model.BodyPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BodyPartRepository extends JpaRepository<BodyPart, Long> {
    BodyPart findByName(String name);
}

