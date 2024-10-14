/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */

package com.example.clinic.app.recipe.dto;

/**
 *
 * @author thisaster
 */

import java.time.LocalDate;

public record RecipeDto(
    Long id,
    LocalDate recipeDate,
    String medication,
    String dose,
    String duration
) {}
