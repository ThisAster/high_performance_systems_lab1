package com.example.clinic.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Optional<Patient> findByName(String name);
    Optional<Patient> findByDateOfBirth(LocalDate dateOfBirth);
}
