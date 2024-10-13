/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.example.clinic.dto;

/**
 *
 * @author thisaster
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AppointmentDto {
    private Long id;
    private LocalDateTime appointmentDate;
    private Long patient_id;
    private AppointmentTypeDTO appointmentType;
}
