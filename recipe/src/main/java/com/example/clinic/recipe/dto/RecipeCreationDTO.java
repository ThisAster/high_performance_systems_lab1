package com.example.clinic.recipe.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RecipeCreationDTO(

        @NotNull(message = "Recipe date is required")
        LocalDate recipeDate,

        @NotBlank(message = "Medication is required")
        @Size(max = 100, message = "Medication should not exceed 100 characters")
        String medication,

        @NotBlank(message = "Dose is required")
        @Size(max = 50, message = "Dose should not exceed 50 characters")
        String dose,

        @NotBlank(message = "Duration is required")
        @Size(max = 50, message = "Duration should not exceed 50 characters")
        String duration
) {
}
