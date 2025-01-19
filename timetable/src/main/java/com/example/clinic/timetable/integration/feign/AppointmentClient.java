package com.example.clinic.timetable.integration.feign;

import com.example.clinic.timetable.dto.AppointmentDto;
import com.example.clinic.timetable.integration.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentClient implements AppointmentService {
    @Override
    public List<AppointmentDto> getAppointmentsByDoctorIdAndTimeInterval(Long doctorId, LocalDateTime start, LocalDateTime end) {
        return List.of();
    }
}
