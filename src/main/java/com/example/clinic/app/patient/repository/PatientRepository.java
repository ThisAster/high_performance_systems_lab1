package com.example.clinic.app.patient.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clinic.app.patient.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    List<Patient> findByName(String name);

    List<Patient> findByDateOfBirth(LocalDate dateOfBirth);

    @Query("select distinct p from Patient p " +
            "join fetch p.appointments a " +
            "join fetch a.appointmentType t " +
            "join fetch t.doctor d " +
            "where p.id in :ids")
    Set<Patient> findByIdInWithAppointments(@Param("ids") Set<Long> ids);

    Optional<Patient> findByEmail(String email);
}

