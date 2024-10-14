package com.example.clinic.app.timetable.controller;

import com.example.clinic.app.timetable.mapper.TimeTableMapper;
import com.example.clinic.app.timetable.service.TimeTableService;
import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.timetable.dto.TimeTableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController("/api/timetable")
@RequiredArgsConstructor
public class TimeTableController {

    private final TimeTableService timeTableService;
    private final TimeTableMapper timeTableMapper;

    @GetMapping("/doctor/{doctorId}/date/{date}")
    public TimeTableDTO getTimeTableForDoctorAndDate(@PathVariable Long doctorId, @PathVariable LocalDate date) {
        List<Appointment> appointmentList = timeTableService.getTimeTableForDoctorAndDate(doctorId, date);
        return timeTableMapper.mapToDto(date, doctorId, appointmentList);
    }

}
