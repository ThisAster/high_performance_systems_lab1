package com.example.clinic.timetable.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AppointmentTimeTableDTO {
    private Long id;
    private LocalDateTime appointmentDateStart;
    private LocalDateTime appointmentDateEnd;
    private Integer duration;
    private Long patientId;
}
