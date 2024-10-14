package com.example.clinic.app.timetable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class TimeTableDTO {
    private LocalDate date;
    private Long doctorId;
    private List<AppointmentTimeTableDTO> appointmentDtoList;
}
