/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Appointment;

/**
 *
 * @author thisaster
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByAppointmentDate(LocalDateTime appointmentDate);

    @Query("select a from Appointment a " +
            "join a.appointmentType as t " +
            "join t.doctor as d " +
            "WHERE d.id = :id")
    List<Appointment> findByDoctorId(@Param("id") Long doctorId);

    List<Appointment> findByPatientId(Long patientId);
}