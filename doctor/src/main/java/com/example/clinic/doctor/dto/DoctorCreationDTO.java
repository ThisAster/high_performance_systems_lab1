package com.example.clinic.doctor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DoctorCreationDTO(

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name should not exceed 100 characters")
        String name,

        @NotBlank(message = "Speciality is required")
        @Size(max = 50, message = "Speciality should not exceed 50 characters")
        String speciality
) {
}
