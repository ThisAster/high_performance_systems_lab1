package com.example.clinic.timetable.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TimeTableDTO {
    private LocalDate date;
    private Long doctorId;
    private List<AppointmentTimeTableDTO> appointmentDtoList;
}
