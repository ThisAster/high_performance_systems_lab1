package com.example.clinic.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "recipes")
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @MapsId
    private Appointment appointment;

    public Recipe(LocalDate recipeDate, String medication, String dose, String duration) {
        this.recipeDate = recipeDate;
        this.medication = medication;
        this.dose = dose;
        this.duration = duration;
    }
}