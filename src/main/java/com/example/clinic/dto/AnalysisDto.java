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

public record AnalysisDto(
    Long id,
    String type,
    LocalDate sampleDate,
    String result,
    String status
) {}
