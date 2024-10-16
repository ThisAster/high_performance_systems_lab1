package com.example.clinic.app.analysis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AnalysisCreationDto(
        String type,
        LocalDate sampleDate,
        String result,
        String status
) {}
