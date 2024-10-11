package com.example.clinic.mapper;

import org.springframework.stereotype.Component;

import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Patient;

@Component
public class PatientMapper {

    public PatientDto entityToPatientDto(Patient patient) {
        if (patient == null) {
            return null;
        }
        return new PatientDto(
            patient.getId(),
            patient.getName(),
            patient.getDateOfBirth(),
            patient.getEmail()
        );
    }

    public Patient patientDtoToEntity(PatientDto patientDto) {
        if (patientDto == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(patientDto.id());
        patient.setName(patientDto.name());
        patient.setDateOfBirth(patientDto.dateOfBirth());
        patient.setEmail(patientDto.email());
        
        return patient;
    }
}
