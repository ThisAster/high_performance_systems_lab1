/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.clinic.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.clinic.dto.DoctorDto;
import com.example.clinic.entity.Doctor;


/**
 *
 * @author thisaster
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorMapper {

    Doctor doctorDtoToEntity(DoctorDto doctorDto);

    void updateEntityFromDto(@MappingTarget Doctor entity, DoctorDto dto);

    DoctorDto entityToDoctorDto(Doctor entity);
}
