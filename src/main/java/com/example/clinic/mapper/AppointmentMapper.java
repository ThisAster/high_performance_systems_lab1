package com.example.clinic.mapper;

import org.springframework.stereotype.Component;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;

@Component
public class AppointmentMapper {

    public AppointmentDto entityToAppointmentDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return new AppointmentDto(
            appointment.getId(),
            appointment.getAppointmentDate(), 
            appointment.getDescription()
        );
    }
 
    public Appointment appointmentDtoToEntity(AppointmentDto appointmentDto) {
        if (appointmentDto == null) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.id());
        appointment.setAppointmentDate(appointmentDto.appointmentDate());
        appointment.setDescription(appointmentDto.description());
        return appointment;
    }
}
