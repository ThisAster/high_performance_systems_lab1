package com.example.clinic.app.billing.dto;

import com.example.clinic.app.doctor.dto.DoctorDto;
import com.example.clinic.app.patient.dto.PatientDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConsultationDTO {

    @NotNull(message = "Patient information is required")
    private PatientDto patient;

    @NotNull(message = "Doctor information is required")
    private DoctorDto doctor;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive value")
    private BigDecimal price;
}
