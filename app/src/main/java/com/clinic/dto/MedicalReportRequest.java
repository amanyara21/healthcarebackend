package com.clinic.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
public class MedicalReportRequest {
    private UUID patientId;
    private UUID appointmentId;
    private MultipartFile file;
}



