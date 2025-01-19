package com.example.clinic.appointment.integration;

import com.example.clinic.appointment.dto.PatientDto;

import java.util.Optional;

public interface PatientService {
    Optional<PatientDto> findById(Long id);
}
