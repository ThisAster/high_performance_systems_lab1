package com.example.clinic.app.timetable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentTimeTableDTO {
    private Long id;
    private LocalDateTime appointmentDateStart;
    private LocalDateTime appointmentDateEnd;
    private Integer duration;
    private Long patientId;
}
