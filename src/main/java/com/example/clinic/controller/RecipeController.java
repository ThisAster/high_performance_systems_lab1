package com.example.clinic.controller;

import java.net.URI;

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

import com.example.clinic.dto.RecipeDto;
import com.example.clinic.entity.Recipe;
import com.example.clinic.mapper.RecipeMapper;
import com.example.clinic.service.RecipeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto,
                                                   @RequestParam Long doctorId,
                                                   @RequestParam Long patientId) {
        Recipe recipe = recipeService.createRecipe(recipeDto, doctorId, patientId);
        RecipeDto createdRecipeDto = recipeMapper.entityToRecipeDto(recipe);
        return ResponseEntity.created(URI.create("/api/recipes/" + createdRecipeDto.id())).body(createdRecipeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
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
}
