package com.example.clinic.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "recipes")
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_date", nullable = false)
    private LocalDate recipeDate;

    @Column(name = "medication", nullable = false)
    private String medication;

    @Column(name = "dose", nullable = false)
    private String dose;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "doctor_id")
    private Long doctor;

    @Column(name = "patient_id")
    private Long patient;

    @Column(name = "appointment_id")
    private Long appointment;
}
