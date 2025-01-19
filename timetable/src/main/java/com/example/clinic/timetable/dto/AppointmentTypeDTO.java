package com.example.clinic.timetable.dto;

import java.math.BigDecimal;

public record AppointmentTypeDTO(
        Long id,
        String name,
        String description,
        Integer duration,
        BigDecimal price,
        DoctorDto doctor
) {
}
