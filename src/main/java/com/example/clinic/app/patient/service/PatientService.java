package com.example.clinic.app.patient.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.clinic.app.patient.dto.PatientDto;
import com.example.clinic.app.analysis.entity.Analysis;
import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.document.entity.Document;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.app.recipe.entity.Recipe;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.app.patient.mapper.PatientMapper;
import com.example.clinic.app.analysis.repository.AnalysisRepository;
import com.example.clinic.app.appointment.repository.AppointmentRepository;
import com.example.clinic.app.document.repository.DocumentRepository;
import com.example.clinic.app.patient.repository.PatientRepository;
import com.example.clinic.app.recipe.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final RecipeRepository recipeRepository;
    private final DocumentRepository documentRepository;
    private final AnalysisRepository analysisRepository;
    private final PatientMapper patientMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Patient createPatient(PatientDto patientDto) {
        Patient patient = patientMapper.patientDtoToEntity(patientDto);
        
        return patientRepository.save(patient);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Patient updatePatient(Long id, PatientDto patientDto) {
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
}
