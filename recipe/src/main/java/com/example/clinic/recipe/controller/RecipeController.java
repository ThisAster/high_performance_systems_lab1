package com.example.clinic.recipe.controller;

import com.example.clinic.recipe.dto.RecipeCreationDTO;
import com.example.clinic.recipe.dto.RecipeDto;
import com.example.clinic.recipe.entity.Recipe;
import com.example.clinic.recipe.mapper.RecipeMapper;
import com.example.clinic.recipe.model.PageArgument;
import com.example.clinic.recipe.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @PostMapping
    public ResponseEntity<RecipeCreationDTO> createRecipe(@Valid @RequestBody RecipeCreationDTO recipeDto,
                                                          @RequestParam Long doctorId,
                                                          @RequestParam Long patientId) {
        Recipe recipe = recipeService.createRecipe(recipeDto, doctorId, patientId);
        return ResponseEntity.created(URI.create("/api/recipes/" + recipe.getId())).body(recipeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeCreationDTO recipeDto) {
        Recipe updatedRecipe = recipeService.updateRecipe(id, recipeDto);
        RecipeDto updatedRecipeDto = recipeMapper.entityToRecipeDto(updatedRecipe);
        return ResponseEntity.ok(updatedRecipeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok("Recipe with id " + id + " successfully deleted.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        RecipeDto recipeDto = recipeMapper.entityToRecipeDto(recipe);
        return ResponseEntity.ok(recipeDto);
    }

    @GetMapping
    public ResponseEntity<Page<RecipeDto>> getRecipes(
            PageArgument page
    ) {
        Page<Recipe> recipePage = recipeService.getRecipes(page.getPageRequest());
        Page<RecipeDto> response = recipePage.map(recipeMapper::entityToRecipeDto);
        return ResponseEntity.ok(response);
    }
}
