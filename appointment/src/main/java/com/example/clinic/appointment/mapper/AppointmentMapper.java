package com.example.clinic.appointment.mapper;

import com.example.clinic.appointment.dto.AppointmentCreationDTO;
import com.example.clinic.appointment.dto.AppointmentDto;
import com.example.clinic.appointment.entity.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
                appointment.getPatient() != null ? appointment.getPatient() : null,
                appoinmentsTypeMapper.entityToAppointmentTypeDTO(appointment.getAppointmentType())
        );
    }

    public Appointment appointmentDtoToEntity(AppointmentCreationDTO appointmentDto) {
        if (appointmentDto == null) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        return appointment;
    }
}
