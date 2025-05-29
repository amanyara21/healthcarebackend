package com.clinic.service;

import com.clinic.dto.AppointmentRequest;
import com.clinic.dto.AppointmentResponse;
import com.clinic.model.*;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DoctorDetailsRepository;
import com.clinic.repository.DoctorUnavailableDateRepository;
import com.clinic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorUnavailableDateRepository doctorUnavailableDateRepository;
    private final UserRepository userRepository;
    private final DoctorDetailsRepository doctorDetailsRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorUnavailableDateRepository doctorUnavailableDateRepository, UserRepository userRepository, DoctorDetailsRepository doctorDetailsRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorUnavailableDateRepository = doctorUnavailableDateRepository;
        this.userRepository = userRepository;
        this.doctorDetailsRepository = doctorDetailsRepository;
    }
    public AppointmentResponse bookAppointment(AppointmentRequest appointmentRequest, String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DoctorDetails doctorDetails = doctorDetailsRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        boolean isUnavailable = doctorUnavailableDateRepository.existsByDoctor_IdAndUnavailableDate(doctorDetails.getId(),  appointmentRequest.getAppointmentDate());
        if (isUnavailable) {
            return null;
        }

        boolean exists = appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                doctorDetails.getId(),
                appointmentRequest.getAppointmentDate(),
                appointmentRequest.getAppointmentTime()
        );

        if (exists) {
            throw new RuntimeException("Slot already booked!");
        }

        Appointment appointment = new Appointment();
        if (appointmentRequest.getAppointmentType() == AppointmentType.ONLINE) {
            appointment.setAppointmentId(UUID.randomUUID().toString());
        }

        appointment.setPatient(user);
        appointment.setDoctor(doctorDetails);
        appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setAppointmentType(appointmentRequest.getAppointmentType());
        appointment.setPaymentStatus(PaymentStatus.PENDING);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return new AppointmentResponse(savedAppointment);
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AppointmentResponse> getAppointmentsByDoctorAndDate(String email, LocalDate date) {
        User user = userRepository.findByEmail(email).orElseThrow();
        DoctorDetails doctor = doctorDetailsRepository.findByDoctor_Id(user.getId()).orElseThrow();
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctor.getId(), date)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AppointmentResponse> getAppointmentsByPatient(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return appointmentRepository.findByPatientId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AppointmentResponse> getUpcomingAppointmentsByPatient(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return appointmentRepository.findUpcomingAppointmentsByPatient(user.getId(), LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AppointmentResponse> getPastAppointmentsByPatient(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return appointmentRepository.findPastAppointmentsByPatient(user.getId(), LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AppointmentResponse getAppointmentById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + id));
        return mapToResponse(appointment);
    }


    private AppointmentResponse mapToResponse(Appointment appointment) {
        return new AppointmentResponse(appointment);
    }

    public List<LocalTime> getAvailableSlots(UUID doctorId, LocalDate date) {
        boolean isUnavailable = doctorUnavailableDateRepository.existsByDoctor_IdAndUnavailableDate(doctorId, date);
        if (isUnavailable) {
            return null;
        }
        List<Appointment> bookedAppointments = appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);
        List<LocalTime> bookedTimes = new ArrayList<>();

        for (Appointment appointment : bookedAppointments) {
            bookedTimes.add(appointment.getAppointmentTime());
        }

        List<LocalTime> availableSlots = new ArrayList<>();

        List<LocalTime[]> timeRanges = List.of(
                new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(13, 0)},
                new LocalTime[]{LocalTime.of(14, 0), LocalTime.of(17, 0)}
        );

        for (LocalTime[] range : timeRanges) {
            LocalTime start = range[0];
            LocalTime end = range[1];

            while (start.isBefore(end)) {
                if (!bookedTimes.contains(start)) {
                    availableSlots.add(start);
                }
                start = start.plusMinutes(20);
            }
        }

        return availableSlots;
    }



    public void cancelAppointment(UUID appointmentId, String username) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getPatient().getEmail().equals(username)) {
            throw new RuntimeException("You are not authorized to cancel this appointment.");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Appointment is already cancelled.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);

        appointmentRepository.save(appointment);
    }

}
