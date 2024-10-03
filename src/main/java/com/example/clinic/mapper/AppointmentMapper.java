package com.example.clinic.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppointmentMapper {

    Appointment appointmentDtoToEntity(AppointmentDto appointmentDto);

    void updateEntityFromDto(@MappingTarget Appointment entity, AppointmentDto dto);

    AppointmentDto entityToAppointmentDto(Appointment entity);
}
