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
        if (dto == null) {
            return null;
        }
        Recipe recipe = new Recipe();
        recipe.setId(dto.id());
        recipe.setRecipeDate(dto.recipeDate());
        recipe.setMedication(dto.medication());
        recipe.setDose(dto.dose());
        recipe.setDuration(dto.duration());

        // Обратите внимание: поля doctor, patient и appointment не присутствуют в DTO, поэтому мы их игнорируем
        
        return recipe;
    }
}
