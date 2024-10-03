package com.example.clinic.mapper;

import org.springframework.stereotype.Component;

import com.example.clinic.dto.RecipeDto;
import com.example.clinic.entity.Recipe;

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

    public Recipe recipeDtoToEntity(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }
        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.id());
        recipe.setRecipeDate(recipeDto.recipeDate());
        recipe.setMedication(recipeDto.medication());
        recipe.setDose(recipeDto.dose());
        recipe.setDuration(recipeDto.duration());
        
        return recipe;
    }
}
