package com.example.clinic.timetable.mapper;

import com.example.clinic.timetable.dto.AppointmentDto;
import com.example.clinic.timetable.dto.AppointmentTimeTableDTO;
import com.example.clinic.timetable.dto.TimeTableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeTableMapper {

    private AppointmentTimeTableDTO appointmentToDto(AppointmentDto appointment) {
        LocalDateTime start = appointment.getAppointmentDate();
        LocalDateTime end = start.plusMinutes(appointment.getAppointmentType().duration());

        return new AppointmentTimeTableDTO(
                appointment.getId(),
                start,
                end,
                appointment.getAppointmentType().duration(),
                appointment.getPatientId()
        );
    }

    public TimeTableDTO mapToDto(LocalDate date, Long doctorId, List<AppointmentDto> appointmentList) {

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
