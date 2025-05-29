package com.clinic.service;

import com.clinic.dto.DoctorDTO;
import com.clinic.model.BodyPart;
import com.clinic.model.DoctorBodyPart;
import com.clinic.repository.BodyPartRepository;
import com.clinic.repository.DoctorBodyPartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorBodyPartService {

    private final DoctorBodyPartRepository doctorBodyPartRepository;
    private final BodyPartRepository bodyPartRepository;

    public DoctorBodyPartService(DoctorBodyPartRepository doctorBodyPartRepository, BodyPartRepository bodyPartRepository) {
        this.doctorBodyPartRepository = doctorBodyPartRepository;
        this.bodyPartRepository = bodyPartRepository;
    }

    public List<DoctorDTO> getDoctorsByBodyPart(String bodyPartName) {
        BodyPart bodyPart = bodyPartRepository.findByName(bodyPartName);
        if (bodyPart != null) {
            List<DoctorBodyPart> mappings = doctorBodyPartRepository.findByBodyPart(bodyPart);
            return mappings.stream()
                    .map(DoctorBodyPart::getDoctor)
                    .distinct()
                    .map(doctor -> new DoctorDTO(
                            doctor.getId(),
                            doctor.getName(),
                            doctor.getSpecialization(),
                            doctor.getAppointmentTypes(),
                            doctor.getExperience(),
                            doctor.getClinicName()
                    ))
                    .collect(Collectors.toList());
        }
        return List.of();
    }


    public DoctorBodyPart addDoctorBodyPart(DoctorBodyPart doctorBodyPart) {
        return doctorBodyPartRepository.save(doctorBodyPart);
    }

}
