package com.clinic.service;

import com.clinic.dto.DoctorDetailsDTO;
import com.clinic.model.DoctorDetails;
import com.clinic.model.User;
import com.clinic.repository.DoctorDetailsRepository;
import com.clinic.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorDetailsService {

    private final DoctorDetailsRepository doctorDetailsRepository;
    private final UserRepository userRepository;

    public DoctorDetailsService(DoctorDetailsRepository doctorDetailsRepository, UserRepository userRepository) {
        this.doctorDetailsRepository = doctorDetailsRepository;
        this.userRepository = userRepository;
    }

    public DoctorDetails getDoctorByUserId(UUID userId) {
        return doctorDetailsRepository.findByDoctor_Id(userId)
                .orElseThrow(() -> new RuntimeException("Doctor not found for user ID: " + userId));
    }

    public List<DoctorDetails> getAllDoctors() {
        return doctorDetailsRepository.findAll();
    }

    public Optional<DoctorDetails> getDoctorById(UUID id) {
        return doctorDetailsRepository.findById(id);
    }

    public DoctorDetails saveDoctor(String email, DoctorDetailsDTO doctorDetailsDTO) {
        User user = userRepository.findByEmail(email).orElseThrow();
        DoctorDetails doctorDetails = new DoctorDetails();
        doctorDetails.setName(doctorDetailsDTO.getName());
        doctorDetails.setSpecialization(doctorDetailsDTO.getSpecialization());
        doctorDetails.setDoctor(user);
        doctorDetails.setExperience(doctorDetailsDTO.getExperience());
        doctorDetails.setClinicName(doctorDetailsDTO.getClinicName());
        doctorDetails.setAppointmentTypes(doctorDetailsDTO.getAppointmentTypes());
        return doctorDetailsRepository.save(doctorDetails);
    }

    public DoctorDetails getDetails(String email) {
        User doctor = userRepository.findByEmail(email).orElseThrow();
        DoctorDetails doctorDetails = doctorDetailsRepository.findByDoctor_Id(doctor.getId())
                .orElseThrow(() -> new RuntimeException("DoctorDetail Not Found"));

        return doctorDetailsRepository.save(doctorDetails);
    }
}


