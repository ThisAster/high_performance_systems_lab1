package com.example.clinic.service.recipe;

import com.example.clinic.app.recipe.dto.RecipeCreationDTO;
import com.example.clinic.app.recipe.entity.Recipe;
import com.example.clinic.app.recipe.service.RecipeService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RecipeServiceTest {

    @Autowired
    private RecipeService recipeService;

    @Test
    void createRecipeTest(){
        RecipeCreationDTO recipeCreationDTO = new RecipeCreationDTO(
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "ketamine",
                "10g",
                "1 week"
        );

        Recipe createdRecipe = recipeService.createRecipe(recipeCreationDTO, 1L, 1L);
        Recipe retrievedRecipe = recipeService.getRecipeById(createdRecipe.getId());

        assertNotNull(retrievedRecipe);
    }

    @Test
    void getRecipeByIdTest(){
        RecipeCreationDTO recipeCreationDTO = new RecipeCreationDTO(
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "ketamine",
                "10g",
                "1 week"
        );

        Recipe createdRecipe = recipeService.createRecipe(recipeCreationDTO, 1L, 1L);
        Recipe retrievedRecipe = recipeService.getRecipeById(createdRecipe.getId());

        assertNotNull(retrievedRecipe);
        assertEquals(retrievedRecipe.getId(), createdRecipe.getId());
        assertEquals(retrievedRecipe.getMedication(), createdRecipe.getMedication());
        assertEquals(retrievedRecipe.getDose(), createdRecipe.getDose());
        assertEquals(retrievedRecipe.getDuration(), createdRecipe.getDuration());
    }

    @Test
    void deleteRecipeTest(){
        RecipeCreationDTO recipeCreationDTO = new RecipeCreationDTO(
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "ketamine",
                "10g",
                "1 week"
        );
        Recipe createdRecipe = recipeService.createRecipe(recipeCreationDTO, 1L, 1L);
        recipeService.deleteRecipe(createdRecipe.getId());
        assertThrows(EntityNotFoundException.class,
                () -> recipeService.getRecipeById(createdRecipe.getId()));
    }

    @Test
    void updateRecipeTest(){
        RecipeCreationDTO oldRecipeCreationDTO = new RecipeCreationDTO(
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "ketamine",
                "10g",
                "1 week"
        );

        RecipeCreationDTO newRecipeCreationDTO = new RecipeCreationDTO(
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "ketamine",
                "10g",
                "3 week"
        );

        Recipe createdRecipe = recipeService.createRecipe(oldRecipeCreationDTO, 1L, 1L);
        Recipe updatedRecipe = recipeService.updateRecipe(createdRecipe.getId(), newRecipeCreationDTO);


        assertNotNull(updatedRecipe);
        assertEquals(newRecipeCreationDTO.recipeDate(), updatedRecipe.getRecipeDate());
        assertEquals(newRecipeCreationDTO.medication(), updatedRecipe.getMedication());
        assertEquals(newRecipeCreationDTO.dose(), updatedRecipe.getDose());
        assertEquals(newRecipeCreationDTO.duration(), updatedRecipe.getDuration());
    }
}