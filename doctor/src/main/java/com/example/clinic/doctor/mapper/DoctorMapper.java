/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.doctor.mapper;

import com.example.clinic.doctor.dto.DoctorCreationDTO;
import com.example.clinic.doctor.dto.DoctorDto;
import com.example.clinic.doctor.entity.Doctor;
import org.springframework.stereotype.Component;

/**
 * @author thisaster
 */

@Component
public class DoctorMapper {

    public DoctorDto entityToDoctorDto(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        return new DoctorDto(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpeciality()
        );
    }

    public Doctor doctorDtoToEntity(DoctorCreationDTO doctorDto) {
        if (doctorDto == null) {
            return null;
        }
        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.name());
        doctor.setSpeciality(doctorDto.speciality());
        return doctor;
    }
}