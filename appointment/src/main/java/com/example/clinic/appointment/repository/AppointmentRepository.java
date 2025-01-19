/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.appointment.repository;

import com.example.clinic.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author thisaster
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select a from Appointment a " +
            "join a.appointmentType as t " +
            "WHERE t.doctor = :id " +
            "AND a.appointmentDate BETWEEN :start AND :end")
    List<Appointment> findByDoctorIdAndTimeInterval(@Param("id") Long doctorId,
                                                    @Param("start") LocalDateTime start,
                                                    @Param("end") LocalDateTime end);

    List<Appointment> findByPatientIn(List<Long> patients);
}