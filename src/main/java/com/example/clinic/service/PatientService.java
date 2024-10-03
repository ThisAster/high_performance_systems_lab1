package com.example.clinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.AnalysisDto;
import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.dto.DocumentDto;
import com.example.clinic.dto.PatientDto;
import com.example.clinic.dto.RecipeDto;
import com.example.clinic.entity.Patient;
import com.example.clinic.mapper.AnalysisMapper;
import com.example.clinic.mapper.AppointmentMapper;
import com.example.clinic.mapper.DocumentMapper;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.mapper.RecipeMapper;
import com.example.clinic.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/* TODO: add other dependensies (appointments, recipes, document, analysis) edit create and update
 * create repositories on which this depends 
*/
@Service
@AllArgsConstructor
public class PatientService {
    
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
   
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    private final AnalysisService analysisService;
    private final AnalysisMapper analysisMapper; 

    @Transactional
    public Optional<PatientDto> createPatient(PatientDto patientDto) {
        Patient patient = patientMapper.patientDtoToEntity(patientDto);

        List<AppointmentDto> appointmentsDto = appointmentService.getByPatientId(patient.getId());
        List<RecipeDto> recipesDto = recipeService.getByPatientId(patient.getId());
        List<DocumentDto> documentsDto = documentService.getByPatientId(patient.getId());
        List<AnalysisDto> analysesDto = analysisService.getByPatientId(patient.getId());
        
        patient.setAppointments(appointmentsDto.stream()
        .map(appointmentMapper::appointmentDtoToEntity)
        .collect(Collectors.toList()));

        patient.setRecipes(recipesDto.stream()
            .map(recipeMapper::recipeDtoToEntity)
            .collect(Collectors.toList()));

        patient.setDocuments(documentsDto.stream()
            .map(documentMapper::documentDtoToEntity)
            .collect(Collectors.toList()));

        patient.setAnalyses(analysesDto.stream()
            .map(analysisMapper::analysisDtoToEntity)
            .collect(Collectors.toList()));

        Patient savedPatient = patientRepository.save(patient);
        
        return Optional.of(patientMapper.entityToPatientDto(savedPatient));
    }

    @Transactional
    public Optional<PatientDto> updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));
    
        patient.setName(patientDto.name());
        patient.setDateOfBirth(patientDto.dateOfBirth());

        Patient savedPatient = patientRepository.save(patient);
    
        return Optional.of(patientMapper.entityToPatientDto(savedPatient));
    }

    @Transactional
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public Optional<PatientDto> getPatientById(Long id) {
        return patientRepository.findById(id)
            .map(patientMapper::entityToPatientDto);
    }

    public List<PatientDto> getPatientByName(String name) {
        return patientRepository.findByName(name).stream()
            .map(patientMapper::entityToPatientDto)
            .collect(Collectors.toList());
    }
    
    public List<PatientDto> getPatientsByDateOfBirth(LocalDate dateOfBirth) {
        return patientRepository.findByDateOfBirth(dateOfBirth).stream()
            .map(patientMapper::entityToPatientDto)
            .collect(Collectors.toList());
    }

    public List<PatientDto> getAllPatients() {
        return ((Collection<Patient>) patientRepository.findAll()).stream()
        .map(patientMapper::entityToPatientDto)
        .collect(Collectors.toList());
    }
}
