package com.example.clinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public Optional<Patient> createPatient(PatientDto patientDto) {
        Patient patient = patientMapper.patientDtoToEntity(patientDto);

        List<Appointment> appointments = appointmentRepository.findByPatientId(patientDto.id());
        List<Recipe> recipes = recipeRepository.findByPatientId(patientDto.id());
        List<Document> documents = documentRepository.findByPatientId(patientDto.id());
        List<Analysis> analyses = analysisRepository.findByPatientId(patientDto.id());
        
        patient.setAppointments(appointments.stream()
        .collect(Collectors.toList()));

        patient.setRecipes(recipes.stream()
            .collect(Collectors.toList()));

        patient.setDocuments(documents.stream()
            .collect(Collectors.toList()));

        patient.setAnalyses(analyses.stream()
            .collect(Collectors.toList()));

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

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> getPatientByName(String name) {
        return patientRepository.findByName(name).stream()
            .collect(Collectors.toList());
    }
    
    public List<Patient> getPatientsByDateOfBirth(LocalDate dateOfBirth) {
        return patientRepository.findByDateOfBirth(dateOfBirth).stream()
            .collect(Collectors.toList());
    }

    public List<Patient> getAllPatients() {
        return ((Collection<Patient>) patientRepository.findAll()).stream()
        .collect(Collectors.toList());
    }
}
