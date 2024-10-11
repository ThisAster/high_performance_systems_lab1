/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.example.clinic.dto;

/**
 *
 * @author thisaster
 */
import java.time.LocalDate;

public record AppointmentDto(
    Long id,
    LocalDate appointmentDate,
    String description,
    Long doctor_id,
    Long patient_id
) {}
