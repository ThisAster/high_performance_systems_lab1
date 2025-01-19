package com.example.clinic.recipe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {
        "/sql/test.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createRecipe(@Value("classpath:/recipes/create.json") Resource json) throws Exception {

        Long doctorId = 1L;
        Long patientId = 2L;

        mockMvc.perform(post("/api/recipes")
                        .param("doctorId", doctorId.toString())
                        .param("patientId", patientId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void updateRecipe(@Value("classpath:/recipes/update.json") Resource json) throws Exception {
        Long recipeId = 2L;

        mockMvc.perform(put("/api/recipes/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRecipe() throws Exception {
        Long recipeId = 1L;

        mockMvc.perform(delete("/api/recipes/{id}", recipeId))
                .andExpect(status().isOk())
                .andExpect(content().string("Recipe with id " + recipeId + " successfully deleted."));
    }

    @Test
    void getRecipeById() throws Exception {
        Long recipeId = 2L;

        mockMvc.perform(get("/api/recipes/{id}", recipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recipeId));
    }

    @Test
    void getRecipes() throws Exception {
        int page = 0;
        int size = 2;

        mockMvc.perform(get("/api/recipes")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(size));
    }
}
