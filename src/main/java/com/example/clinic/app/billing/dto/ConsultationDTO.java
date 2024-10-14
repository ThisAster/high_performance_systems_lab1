package com.example.clinic.app.billing.dto;

import com.example.clinic.app.doctor.dto.DoctorDto;
import com.example.clinic.app.patient.dto.PatientDto;
import lombok.Data;

@Data
public class ConsultationDTO {
    private PatientDto patient;
    private DoctorDto doctor;
    private double price;
}
