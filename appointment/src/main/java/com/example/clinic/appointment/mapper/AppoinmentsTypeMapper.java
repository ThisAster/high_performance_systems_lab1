package com.example.clinic.appointment.mapper;

import com.example.clinic.appointment.dto.AppointmentTypeCreationDTO;
import com.example.clinic.appointment.dto.AppointmentTypeDTO;
import com.example.clinic.appointment.entity.AppointmentsType;
import com.example.clinic.appointment.integration.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppoinmentsTypeMapper {

    private final DoctorService doctorService;

    public AppointmentTypeDTO entityToAppointmentTypeDTO(AppointmentsType appointmentsType) {
        if (appointmentsType == null) {
            return null;
        }

        return new AppointmentTypeDTO(
                appointmentsType.getId(),
                appointmentsType.getName(),
                appointmentsType.getDescription(),
                appointmentsType.getDuration(),
                appointmentsType.getPrice(),
                doctorService.getDoctorById(appointmentsType.getDoctor())
        );
    }

    public AppointmentsType appointmentTypeDTOToEntity(AppointmentTypeCreationDTO appointmentsTypeDTO) {
        if (appointmentsTypeDTO == null) {
            return null;
        }

        AppointmentsType appointmentsType = new AppointmentsType();
        appointmentsType.setName(appointmentsTypeDTO.name());
        appointmentsType.setDescription(appointmentsTypeDTO.description());
        appointmentsType.setDuration(appointmentsTypeDTO.duration());
        appointmentsType.setPrice(appointmentsTypeDTO.price());
        appointmentsType.setDoctor(appointmentsTypeDTO.doctorId());

        return appointmentsType;
    }
}
