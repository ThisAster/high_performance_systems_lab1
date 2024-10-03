package com.example.clinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.DoctorDto;
import com.example.clinic.dto.PatientDto;
import com.example.clinic.dto.RecipeDto;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.entity.Recipe;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.mapper.RecipeMapper;
import com.example.clinic.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @Transactional
    public Optional<RecipeDto> createRecipe(RecipeDto recipeDto, Long doctorId, Long patientId) {
        Recipe recipe = recipeMapper.recipeDtoToEntity(recipeDto);
        DoctorDto doctorDto = doctorService.getDoctorById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment with id " + doctorId + " not found"));

        PatientDto patientDto = patientService.getPatientById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found"));

        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);
        Patient patient = patientMapper.patientDtoToEntity(patientDto);
        
        recipe.setDoctor(doctor);
        recipe.setPatient(patient);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return Optional.of(recipeMapper.entityToRecipeDto(savedRecipe));
    }

    @Transactional
    public Optional<RecipeDto> updateRecipe(Long id, RecipeDto recipeDto) {
        return recipeRepository.findById(id)
            .map(recipe -> {
                recipeMapper.updateEntityFromDto(recipe, recipeDto);
                Recipe updatedRecipe = recipeRepository.save(recipe);
                return recipeMapper.entityToRecipeDto(updatedRecipe);
            });
    }

    @Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<RecipeDto> getAllRecipes() {
        return ((Collection<Recipe>) recipeRepository.findAll()).stream()
                .map(recipeMapper::entityToRecipeDto)
                .collect(Collectors.toList());
    }

    public Optional<RecipeDto> getRecipeById(Long id) {
        return recipeRepository.findById(id).map(recipeMapper::entityToRecipeDto);
    }

    public List<RecipeDto> getByPatientId(Long patientId) {
        return recipeRepository.findByPatientId(patientId).stream()
                .map(recipeMapper::entityToRecipeDto)
                .collect(Collectors.toList());
    }

    public List<RecipeDto> getByDoctorId(Long doctorId) {
        return recipeRepository.findByDoctorId(doctorId).stream()
                .map(recipeMapper::entityToRecipeDto)
                .collect(Collectors.toList());
    }

    public Optional<RecipeDto> getByAppointmentId(Long appointmentId) {
        return recipeRepository.findByAppointmentId(appointmentId)
                .map(recipeMapper::entityToRecipeDto);
    }
}
