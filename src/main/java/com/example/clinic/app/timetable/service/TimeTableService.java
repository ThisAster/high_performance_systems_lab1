package com.example.clinic.app.timetable.service;


import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTableService {

    private final AppointmentRepository appointmentRepository;
    public List<Appointment> getTimeTableForDoctorAndDate(Long doctorId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        return appointmentRepository.findByDoctorIdAndTimeInterval(doctorId, start, end);
    }
}
