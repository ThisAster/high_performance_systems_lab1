/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Appointment;

/**
 *
 * @author thisaster
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByPatientIdAndDoctorId(Long patientId, Long doctorId);

    Optional<Appointment> findByAppointmentDateAndPatientIdAndDoctorId(LocalDate appointmentDate, Long patientId, Long doctorId);
}