package com.example.clinic.billing.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConsultationDTO {
    private Long patient;
    private DoctorDto doctor;
    private BigDecimal price;
}
