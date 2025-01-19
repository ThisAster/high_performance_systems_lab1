package com.example.clinic.analysis.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AnalysisCreationDto(
        @NotBlank(message = "Type is required")
        @Size(max = 100, message = "Type should not exceed 100 characters")
        String type,

        @NotNull(message = "Sample date is required")
        LocalDate sampleDate,

        @NotBlank(message = "Result is required")
        @Size(max = 500, message = "Result should not exceed 500 characters")
        String result,

        @NotBlank(message = "Status is required")
        @Size(max = 50, message = "Status should not exceed 50 characters")
        String status
) {
}
