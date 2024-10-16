package com.example.clinic.app.recipe.service;

import com.example.clinic.app.doctor.service.DoctorService;
import com.example.clinic.app.patient.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.clinic.app.recipe.dto.RecipeDto;
import com.example.clinic.app.doctor.entity.Doctor;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.app.recipe.entity.Recipe;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.app.recipe.mapper.RecipeMapper;
import com.example.clinic.app.recipe.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Recipe createRecipe(RecipeDto recipeDto, Long doctorId, Long patientId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Patient patient = patientService.getPatientById(patientId);

        Recipe recipe = recipeMapper.recipeDtoToEntity(recipeDto);

        recipe.setDoctor(doctor);
        recipe.setPatient(patient);

        return recipeRepository.save(recipe);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Recipe updateRecipe(Long id, RecipeDto recipeDto) {
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
