package com.example.clinic.mapper;

import com.example.clinic.dto.AppointmentCreationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    private final AppoinmentsTypeMapper appoinmentsTypeMapper;

    public AppointmentDto entityToAppointmentDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return new AppointmentDto(
            appointment.getId(),
            appointment.getAppointmentDate(),
            appointment.getPatient() != null ? appointment.getPatient().getId() : null,
            appoinmentsTypeMapper.entityToAppointmentTypeDTO(appointment.getAppointmentType())
        );
    }
 
    public Appointment appointmentDtoToEntity(AppointmentCreationDTO appointmentDto) {
        if (appointmentDto == null) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDto.getAppointment_date());
        return appointment;
    }
}
