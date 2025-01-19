package com.example.clinic.timetable.service;

import com.example.clinic.timetable.dto.TimeTableDTO;
import com.example.clinic.timetable.integration.AppointmentService;
import com.example.clinic.timetable.mapper.TimeTableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TimeTableService {

    private final AppointmentService appointmentService;
    private final TimeTableMapper timeTableMapper;

    public TimeTableDTO getTimeTableForDoctorAndDate(Long doctorId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);
        var appointmentList = appointmentService.getAppointmentsByDoctorIdAndTimeInterval(doctorId, start, end);
        return timeTableMapper.mapToDto(date, doctorId, appointmentList);
    }
}
