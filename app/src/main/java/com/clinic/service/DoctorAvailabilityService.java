package com.clinic.service;

import com.clinic.model.DoctorDetails;
import com.clinic.model.DoctorUnavailableDate;
import com.clinic.model.User;
import com.clinic.repository.DoctorDetailsRepository;
import com.clinic.repository.DoctorUnavailableDateRepository;
import com.clinic.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorAvailabilityService {

    private final DoctorDetailsRepository doctorDetailsRepository;
    private final UserRepository userRepository;
    private final DoctorUnavailableDateRepository doctorUnavailableDateRepository;

        public  DoctorAvailabilityService(DoctorDetailsRepository doctorDetailsRepository, UserRepository userRepository, DoctorUnavailableDateRepository doctorUnavailableDateRepository){
        this.doctorDetailsRepository = doctorDetailsRepository;
            this.userRepository = userRepository;

            this.doctorUnavailableDateRepository = doctorUnavailableDateRepository;
    }

    public void addUnavailableDates(String name, Set<LocalDate> dates) {
            User user= userRepository. findByEmail(name).orElseThrow();

            DoctorDetails doctor = doctorDetailsRepository.findByDoctor_Id(user.getId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            Set<DoctorUnavailableDate> newDates = dates.stream()
                    .map(date -> new DoctorUnavailableDate(null, date, doctor))
                    .collect(Collectors.toSet());

            doctorUnavailableDateRepository.saveAll(newDates);
    }

    public List<LocalDate> getUnavailableDatesByDoctorId(UUID doctorId) {
        return doctorUnavailableDateRepository.findAllByDoctor_Id(doctorId)
                .stream()
                .map(DoctorUnavailableDate::getUnavailableDate)
                .collect(Collectors.toList());
    }

}
