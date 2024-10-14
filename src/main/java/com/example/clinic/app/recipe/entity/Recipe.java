package com.example.clinic.app.recipe.entity;

import java.time.LocalDate;

import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.app.doctor.entity.Doctor;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @MapsId
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
