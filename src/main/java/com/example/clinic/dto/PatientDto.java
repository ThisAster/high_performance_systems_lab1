package com.example.clinic.dto;

import java.time.LocalDate;

public record PatientDto(
    Long id,
    String name,
    LocalDate dateOfBirth
) {}