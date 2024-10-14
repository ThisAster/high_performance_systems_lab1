package com.example.clinic.app.user.entity;

import java.util.Collection;

import com.example.clinic.app.doctor.entity.Doctor;
import com.example.clinic.app.patient.entity.Patient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Collection<Patient> patients;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Collection<Doctor> doctors;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Collection<Admin> admins;
}
