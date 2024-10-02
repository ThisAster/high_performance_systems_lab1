package com.example.clinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Patient;
import com.example.clinic.repository.PatientRepository;

import jakarta.transaction.Transactional;

/* TODO: add other dependensies (appointments, recipes, document, analysis) edit create and update
 * create repositories on which this depends 
*/
@Service
public class PatientService {
    
    private PatientRepository patientRepository;

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> getPatientByName(String name) {
        return patientRepository.findByName(name);
    }
    
    public List<Patient> getPatientsByDateOfBirth(LocalDate dateOfBirth) {
        return patientRepository.findByDateOfBirth(dateOfBirth);
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patientRepository.findAll().forEach(patients::add);
        return patients;
    }

    @Transactional
    public Patient updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));
    
        patient.setName(patientDto.name());
        patient.setDateOfBirth(patientDto.dateOfBirth());
    
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient createPatient(PatientDto patientDto) {
        Patient newPatient = new Patient();
        
        newPatient.setName(patientDto.name());
        newPatient.setDateOfBirth(patientDto.dateOfBirth());
        
        return patientRepository.save(newPatient);
    }

    @Transactional
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
