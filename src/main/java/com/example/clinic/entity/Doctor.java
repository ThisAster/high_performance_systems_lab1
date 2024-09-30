package com.example.clinic.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "doctors")
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_name", length = 40, nullable = false, unique = false)
    private String name;

    @Column(name = "speciality", length = 50, nullable = false)
    private String speciality;

    @Column(name = "consulation_cost", nullable = false)
    private Double consultationCost;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;

    public Doctor(String name, String speciality, Double consultationCost) {
        this.name = name;
        this.speciality = speciality;
        this.consultationCost = consultationCost;
    }
}