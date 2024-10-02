package com.example.clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.dto.DoctorDto;
import com.example.clinic.dto.PatientDto;
import com.example.clinic.dto.RecipeDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.entity.Recipe;
import com.example.clinic.repository.RecipeRepository;

import jakarta.transaction.Transactional;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private ModelMapper recipeMapper;

    @Autowired
    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Autowired
    public void setRecipeMapper(ModelMapper recipeMapper) {
        this.recipeMapper = recipeMapper;
    }

    @Transactional
    public RecipeDto createRecipe(RecipeDto recipeDto, PatientDto patientDto, DoctorDto doctorDto, AppointmentDto appointmentDto) {
        Recipe recipe = recipeMapper.map(recipeDto, Recipe.class);
        Patient patient = recipeMapper.map(patientDto, Patient.class);
        Doctor doctor = recipeMapper.map(doctorDto, Doctor.class);
        Appointment appointment = recipeMapper.map(appointmentDto, Appointment.class);
        recipe.setPatient(patient);
        recipe.setDoctor(doctor);
        recipe.setAppointment(appointment);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.map(savedRecipe, RecipeDto.class);
    }

    public List<RecipeDto> getAllRecipes() {
        return ((List<Recipe>) recipeRepository.findAll()).stream()
                .map(recipe -> recipeMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }

    public RecipeDto getRecipeById(Long id) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if (recipeOpt.isEmpty()) {
            throw new IllegalArgumentException("Recipe with id " + id + " not found");
        }
        return recipeMapper.map(recipeOpt.get(), RecipeDto.class);
    }

    @Transactional
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if (recipeOpt.isEmpty()) {
            throw new IllegalArgumentException("Recipe with id " + id + " not found");
        }
        Recipe recipe = recipeOpt.get();
        recipe.setRecipeDate(recipeDto.recipeDate());
        recipe.setMedication(recipeDto.medication());
        recipe.setDose(recipeDto.dose());
        recipe.setDuration(recipeDto.duration());
        Recipe updatedRecipe = recipeRepository.save(recipe);
        return recipeMapper.map(updatedRecipe, RecipeDto.class);
    }

    @Transactional
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new IllegalArgumentException("Recipe with id " + id + " not found");
        }
        recipeRepository.deleteById(id);
    }

    public List<RecipeDto> findByPatientId(Long patientId) {
        return recipeRepository.findByPatientId(patientId).stream()
                .map(recipe -> recipeMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }

    public List<RecipeDto> findByDoctorId(Long doctorId) {
        return recipeRepository.findByDoctorId(doctorId).stream()
                .map(recipe -> recipeMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }

    public RecipeDto findByAppointmentId(Long appointmentId) {
        return recipeRepository.findByAppointmentId(appointmentId)
                .map(recipe -> recipeMapper.map(recipe, RecipeDto.class))
                .orElseThrow(() -> new IllegalArgumentException("Recipe with appointment id " + appointmentId + " not found"));
    }
}

