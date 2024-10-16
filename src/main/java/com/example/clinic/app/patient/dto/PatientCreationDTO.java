package com.example.clinic.app.patient.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PatientCreationDTO(
        String name,
        LocalDate dateOfBirth,
        String email
) {}
