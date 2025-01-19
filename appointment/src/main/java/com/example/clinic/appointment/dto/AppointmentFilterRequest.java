package com.example.clinic.appointment.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentFilterRequest(
        List<Long> patients,
        Long doctor,
        LocalDateTime appointmentTimeFrom,
        LocalDateTime appointmentTimeTo
) {
}
