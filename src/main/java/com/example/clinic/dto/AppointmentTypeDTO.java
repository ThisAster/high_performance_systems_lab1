package com.example.clinic.dto;

import com.example.clinic.entity.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AppointmentTypeDTO (
    Long id,
    String name,
    String description,
    Integer duration,
    BigDecimal price,
    DoctorDto doctor
) {}
