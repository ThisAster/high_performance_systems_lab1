package com.example.clinic.app.recipe.dto;

import java.time.LocalDate;

public record RecipeCreationDTO(
        LocalDate recipeDate,
        String medication,
        String dose,
        String duration
) {}