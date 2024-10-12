package com.example.clinic.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    List<Patient> findByName(String name);

    List<Patient> findByDateOfBirth(LocalDate dateOfBirth);

    @Query("select p from Patient p " +
            "join fetch p.appointments as a " +
            "join fetch a.appointmentType as t " +
            "join fetch t.doctor as d")
    Set<Patient> findByIdInWithAppointments(Set<Long> ids);

    Optional<Patient> findByEmail(String email);
}

