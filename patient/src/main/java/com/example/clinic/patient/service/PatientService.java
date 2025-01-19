package com.example.clinic.patient.service;

import com.example.clinic.patient.dto.PatientCreationDTO;
import com.example.clinic.patient.dto.PatientDto;
import com.example.clinic.patient.entity.Patient;
import com.example.clinic.patient.exception.EntityNotFoundException;
import com.example.clinic.patient.mapper.PatientMapper;
import com.example.clinic.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Patient createPatient(PatientCreationDTO patientDto) {
        Patient patient = patientMapper.patientDtoToEntity(patientDto);

        return patientRepository.save(patient);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Patient updatePatient(Long id, PatientCreationDTO patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));

        patient.setName(patientDto.name());
        patient.setDateOfBirth(patientDto.dateOfBirth());
        patient.setEmail(patientDto.email());

        return patientRepository.save(patient);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient with id " + id + " not found");
        }
        patientRepository.deleteById(id);
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + id + " not found"));
    }

    public Page<Patient> getPatients(Pageable page) {
        return patientRepository.findAll(page);
    }

    public List<PatientDto> getPatientsByIds(List<Long> ids) {
        return patientRepository.findAllById(ids).stream().map(patientMapper::entityToPatientDto).toList();
    }
}
