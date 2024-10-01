package com.example.clinic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    
    List<Patient> findByName(String name);

    List<Patient> findByDateOfBirth(LocalDate dateOfBirth);
}
