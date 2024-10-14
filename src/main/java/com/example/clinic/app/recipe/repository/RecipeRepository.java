/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.app.recipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.app.recipe.entity.Recipe;

/**
 *
 * @author thisaster
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    List<Recipe> findByPatientId(Long patientId);
    
    List<Recipe> findByDoctorId(Long doctorId);
    
    Optional<Recipe> findByAppointmentId(Long appointmentId);
}
