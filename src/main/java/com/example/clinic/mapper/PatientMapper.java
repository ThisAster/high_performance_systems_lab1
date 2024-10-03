/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.clinic.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Patient;

/**
 *
 * @author thisaster
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {

    Patient patientDtoToEntity(PatientDto patientDto);

    void updateEntityFromDto(@MappingTarget Patient entity, PatientDto dto);

    PatientDto entityToPatientDto(Patient entity);
}
