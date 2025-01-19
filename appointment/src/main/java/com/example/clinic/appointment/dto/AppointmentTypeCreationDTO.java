package com.example.clinic.appointment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AppointmentTypeCreationDTO(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name should not exceed 100 characters")
        String name,

        @Size(max = 500, message = "Description should not exceed 500 characters")
        String description,

        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be a positive number")
        Integer duration,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be a positive value")
        BigDecimal price,

        @NotNull(message = "Doctor ID is required")
        @Positive(message = "Doctor ID must be a positive number")
        @JsonProperty("doctor_id") Long doctorId
) {
}
