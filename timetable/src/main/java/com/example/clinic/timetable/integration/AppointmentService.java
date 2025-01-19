package com.example.clinic.timetable.integration;

import com.example.clinic.timetable.dto.AppointmentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<AppointmentDto> getAppointmentsByDoctorIdAndTimeInterval(Long doctorId, LocalDateTime start, LocalDateTime end);
}
