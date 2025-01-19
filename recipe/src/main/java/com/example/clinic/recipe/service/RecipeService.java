package com.example.clinic.recipe.service;

import com.example.clinic.recipe.dto.RecipeCreationDTO;
import com.example.clinic.recipe.entity.Recipe;
import com.example.clinic.recipe.exception.EntityNotFoundException;
import com.example.clinic.recipe.mapper.RecipeMapper;
import com.example.clinic.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Recipe createRecipe(RecipeCreationDTO recipeDto, Long doctorId, Long patientId) {

        Recipe recipe = recipeMapper.recipeDtoToEntity(recipeDto);

        recipe.setDoctor(doctorId);
        recipe.setPatient(patientId);

        return recipeRepository.save(recipe);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Recipe updateRecipe(Long id, RecipeCreationDTO recipeDto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id " + id + " not found"));

        recipe.setRecipeDate(recipeDto.recipeDate());
        recipe.setMedication(recipeDto.medication());
        recipe.setDose(recipeDto.dose());
        recipe.setDuration(recipeDto.duration());

        return recipeRepository.save(recipe);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new EntityNotFoundException("Recipe with id " + id + " not found");
        }
        recipeRepository.deleteById(id);
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id " + id + " not found"));
    }

    public Page<Recipe> getRecipes(Pageable page) {
        return recipeRepository.findAll(page);
    }
}
