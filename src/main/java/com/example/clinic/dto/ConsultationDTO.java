package com.example.clinic.dto;

import lombok.Data;

@Data
public class ConsultationDTO {
    private PatientDto patient;
    private DoctorDto doctor;
    private double price;
}
