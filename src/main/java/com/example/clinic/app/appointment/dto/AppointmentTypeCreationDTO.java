package com.example.clinic.app.appointment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record AppointmentTypeCreationDTO(
        String name,
        String description,
        Integer duration,
        BigDecimal price,
        @JsonProperty("doctor_id") Long doctorId
) {}
