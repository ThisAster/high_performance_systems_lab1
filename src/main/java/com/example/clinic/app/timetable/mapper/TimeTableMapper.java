package com.example.clinic.app.timetable.mapper;

import com.example.clinic.app.timetable.dto.AppointmentTimeTableDTO;
import com.example.clinic.app.timetable.dto.TimeTableDTO;
import com.example.clinic.app.appointment.entity.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeTableMapper {

    private AppointmentTimeTableDTO appointmentToDto(Appointment appointment) {
        LocalDateTime start = appointment.getAppointmentDate();
        LocalDateTime end = start.plusMinutes(appointment.getAppointmentType().getDuration());

        return new AppointmentTimeTableDTO(
                appointment.getId(),
                start,
                end,
                appointment.getAppointmentType().getDuration(),
                appointment.getPatient().getId()
        );
    }

    public TimeTableDTO mapToDto(LocalDate date, Long doctorId, List<Appointment> appointmentList) {

        var resultList = appointmentList.stream()
                .map(this::appointmentToDto)
                .toList();

        return new TimeTableDTO(
                date,
                doctorId,
                resultList
        );
    }
}
