package com.example.clinic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Analysis;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Document;
import com.example.clinic.entity.Patient;
import com.example.clinic.entity.Recipe;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.repository.AnalysisRepository;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DocumentRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.RecipeRepository;

import jakarta.transaction.Transactional;
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

        List<Appointment> appointments = appointmentRepository.findByPatientId(patientDto.id());
        List<Recipe> recipes = recipeRepository.findByPatientId(patientDto.id());
        List<Document> documents = documentRepository.findByPatientId(patientDto.id());
        List<Analysis> analyses = analysisRepository.findByPatientId(patientDto.id());
        
        patient.setAppointments(appointments);
        patient.setRecipes(recipes);
        patient.setDocuments(documents);
        patient.setAnalyses(analyses);
        
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
}
