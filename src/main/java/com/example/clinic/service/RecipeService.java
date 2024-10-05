package com.example.clinic.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.RecipeDto;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.entity.Recipe;
import com.example.clinic.mapper.RecipeMapper;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final RecipeMapper recipeMapper;

    @Transactional
    public Optional<Recipe> createRecipe(RecipeDto recipeDto, Long doctorId, Long patientId) {
        Recipe recipe = recipeMapper.recipeDtoToEntity(recipeDto);
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment with id " + doctorId + " not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found"));
        
        recipe.setDoctor(doctor);
        recipe.setPatient(patient);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return Optional.of(savedRecipe);
    }

    @Transactional
    public Optional<Recipe> updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + id + " not found"));
        
        recipe.setRecipeDate(recipeDto.recipeDate());
        recipe.setMedication(recipeDto.medication());
        recipe.setDose(recipeDto.dose());
        recipe.setDuration(recipeDto.duration());

        Recipe updatedRecipe = recipeRepository.save(recipe);
        return Optional.of(updatedRecipe);
    }

    @Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
