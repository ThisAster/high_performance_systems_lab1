package com.example.clinic.app.recipe.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RecipeCreationDTO(
        LocalDate recipeDate,
        String medication,
        String dose,
        String duration
) {}