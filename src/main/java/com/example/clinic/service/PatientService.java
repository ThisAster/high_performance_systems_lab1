package com.example.clinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Analysis;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Document;
import com.example.clinic.entity.Patient;
import com.example.clinic.entity.Recipe;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.repository.AnalysisRepository;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DocumentRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {
    
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final RecipeRepository recipeRepository;
    private final DocumentRepository documentRepository;
    private final AnalysisRepository analysisRepository;
    private final PatientMapper patientMapper;


    public boolean patientExists(Long userId) {
        return patientRepository.existsById(userId);
    }

    @Transactional
    public Optional<Patient> createPatient(PatientDto patientDto) {
        Patient patient = patientMapper.patientDtoToEntity(patientDto);

        List<Appointment> appointments = appointmentRepository.findByPatientId(patientDto.id());
        List<Recipe> recipes = recipeRepository.findByPatientId(patientDto.id());
        List<Document> documents = documentRepository.findByPatientId(patientDto.id());
        List<Analysis> analyses = analysisRepository.findByPatientId(patientDto.id());
        
        patient.setAppointments(appointments);

        patient.setRecipes(recipes);

        patient.setDocuments(documents);

        patient.setAnalyses(analyses);

        Patient savedPatient = patientRepository.save(patient);
        
        return Optional.of(savedPatient);
    }

    @Transactional
    public Optional<Patient> updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));
    
        patient.setName(patientDto.name());
        patient.setDateOfBirth(patientDto.dateOfBirth());

        Patient updatedPatient = patientRepository.save(patient);
    
        return Optional.of(updatedPatient);
    }

    @Transactional
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
