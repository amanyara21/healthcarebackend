package com.clinic.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ManualReportRequest {
    private UUID patientId;
    private UUID appointmentId;
    private List<TestResultDTO> tests;
}
