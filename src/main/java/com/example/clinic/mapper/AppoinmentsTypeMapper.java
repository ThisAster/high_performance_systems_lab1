package com.example.clinic.mapper;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.dto.AppointmentTypeDTO;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.AppointmentsType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppoinmentsTypeMapper {

    private final DoctorMapper doctorMapper;

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
                doctorMapper.entityToDoctorDto(appointmentsType.getDoctor())
        );
    }

    public AppointmentsType appointmentTypeDTOToEntity(AppointmentTypeDTO appointmentsTypeDTO) {
        if (appointmentsTypeDTO == null) {
            return null;
        }

        AppointmentsType appointmentsType = new AppointmentsType();
        appointmentsType.setName(appointmentsTypeDTO.name());
        appointmentsType.setDescription(appointmentsTypeDTO.description());
        appointmentsType.setDuration(appointmentsTypeDTO.duration());
        appointmentsType.setPrice(appointmentsTypeDTO.price());

        return appointmentsType;
    }
}
