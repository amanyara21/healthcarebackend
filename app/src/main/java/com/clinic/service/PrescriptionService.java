package com.clinic.service;

import com.clinic.dto.CreatePrescriptionRequest;
import com.clinic.dto.LabTestDTO;
import com.clinic.dto.MedicineDTO;
import com.clinic.dto.PrescriptionResponse;
import com.clinic.model.*;
import com.clinic.repository.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorDetailsRepository doctorDetailsRepository;
    private final UserRepository userRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, AppointmentRepository appointmentRepository, DoctorDetailsRepository doctorDetailsRepository, UserRepository userRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorDetailsRepository = doctorDetailsRepository;
        this.userRepository = userRepository;
    }

    public PrescriptionResponse createPrescription(CreatePrescriptionRequest request, String email) {
        User doc = userRepository.findByEmail(email).orElseThrow();
        DoctorDetails doctor = doctorDetailsRepository.findByDoctor_Id(doc.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Prescription prescription = new Prescription();
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setAppointment(appointment);

        List<Medicine> medicines = request.getMedicines().stream()
                .map(dto -> {
                    Medicine m = new Medicine();
                    m.setName(dto.getName());
                    m.setDosage(dto.getDosage());
                    m.setFrequency(dto.getFrequency());
                    m.setPrescription(prescription);
                    return m;
                }).toList();

        List<LabTest> labTests = request.getLabTests().stream()
                .map(dto -> {
                    LabTest lt = new LabTest();
                    lt.setTestName(dto.getTestName());
                    lt.setFileUrl(dto.getFileUrl());
                    lt.setPrescription(prescription);
                    return lt;
                }).toList();

        prescription.setMedicines(medicines);
        prescription.setLabTests(labTests);

        Prescription saved = prescriptionRepository.save(prescription);
        return new PrescriptionResponse(saved);
    }

    public PrescriptionResponse addMedicines(UUID prescriptionId, List<MedicineDTO> medicineDTOs) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        List<Medicine> newMeds = medicineDTOs.stream()
                .map(dto -> {
                    Medicine med = new Medicine();
                    med.setName(dto.getName());
                    med.setDosage(dto.getDosage());
                    med.setFrequency(dto.getFrequency());
                    med.setPrescription(prescription);
                    return med;
                }).toList();

        prescription.getMedicines().addAll(newMeds);
        Prescription updated = prescriptionRepository.save(prescription);
        return new PrescriptionResponse(updated);
    }

    public PrescriptionResponse addLabTests(UUID prescriptionId, List<LabTestDTO> labTestDTOs) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        List<LabTest> newTests = labTestDTOs.stream()
                .map(dto -> {
                    LabTest test = new LabTest();
                    test.setTestName(dto.getTestName());
                    test.setFileUrl(dto.getFileUrl());
                    test.setPrescription(prescription);
                    return test;
                }).toList();

        prescription.getLabTests().addAll(newTests);
        Prescription updated = prescriptionRepository.save(prescription);
        return new PrescriptionResponse(updated);
    }

    public List<PrescriptionResponse> getPrescriptionsByAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        System.out.println(appointment.getId());
        return prescriptionRepository.findByAppointmentId(appointment.getId())
                .stream()
                .map(PrescriptionResponse::new)
                .toList();
    }

    public List<PrescriptionResponse> getPrescriptionsByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        System.out.print("user "+user.getId());
        return prescriptionRepository.findByPatientId(user.getId())
                .stream()
                .map(PrescriptionResponse::new)
                .toList();
    }

}
