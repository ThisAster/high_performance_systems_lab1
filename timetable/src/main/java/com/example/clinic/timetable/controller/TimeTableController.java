package com.example.clinic.timetable.controller;

import com.example.clinic.timetable.dto.TimeTableDTO;
import com.example.clinic.timetable.mapper.TimeTableMapper;
import com.example.clinic.timetable.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController()
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public class TimeTableController {

    private final TimeTableService timeTableService;
    private final TimeTableMapper timeTableMapper;

    @GetMapping("/doctor/{doctorId}/date/{date}")
    public TimeTableDTO getTimeTableForDoctorAndDate(@PathVariable Long doctorId, @PathVariable String date) {
        return timeTableService.getTimeTableForDoctorAndDate(doctorId, LocalDate.parse(date));
    }

}
