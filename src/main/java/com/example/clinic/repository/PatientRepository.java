package com.example.clinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    List<Patient> findByName(String name);

    List<Patient> findByDateOfBirth(LocalDate dateOfBirth);
}

