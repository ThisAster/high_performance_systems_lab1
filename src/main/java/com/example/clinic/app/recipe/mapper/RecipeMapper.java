package com.example.clinic.app.recipe.mapper;

import com.example.clinic.app.recipe.dto.RecipeCreationDTO;
import org.springframework.stereotype.Component;

import com.example.clinic.app.recipe.dto.RecipeDto;
import com.example.clinic.app.recipe.entity.Recipe;

@Component
public class RecipeMapper {

    public RecipeDto entityToRecipeDto(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        return new RecipeDto(
            recipe.getId(),
            recipe.getRecipeDate(),
            recipe.getMedication(),
            recipe.getDose(),
            recipe.getDuration()
        );
    }

    public Recipe recipeDtoToEntity(RecipeCreationDTO recipeDto) {
        if (recipeDto == null) {
            return null;
        }
        Recipe recipe = new Recipe();
        recipe.setRecipeDate(recipeDto.recipeDate());
        recipe.setMedication(recipeDto.medication());
        recipe.setDose(recipeDto.dose());
        recipe.setDuration(recipeDto.duration());
        
        return recipe;
    }
}
