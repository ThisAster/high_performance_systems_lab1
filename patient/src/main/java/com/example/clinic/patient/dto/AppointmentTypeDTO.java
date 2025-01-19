package com.example.clinic.patient.dto;

import java.math.BigDecimal;

public record AppointmentTypeDTO(
        Long id,
        String name,
        String description,
        Integer duration,
        BigDecimal price,
        Long doctorId
) {
}
