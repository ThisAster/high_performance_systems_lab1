/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;

/**
 *
 * @author thisaster
 */
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByPatientAndDoctor(Patient patient, Doctor doctor);

    Optional<Appointment> findByAppointmentDateAndPatientAndDoctor(LocalDate appointmentDate, Patient patient, Doctor doctor);
}