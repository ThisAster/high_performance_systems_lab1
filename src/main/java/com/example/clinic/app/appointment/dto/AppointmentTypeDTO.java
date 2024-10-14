package com.example.clinic.app.appointment.dto;

import com.example.clinic.app.doctor.dto.DoctorDto;

import java.math.BigDecimal;

public record AppointmentTypeDTO (
    Long id,
    String name,
    String description,
    Integer duration,
    BigDecimal price,
    DoctorDto doctor
) {}
