package com.example.clinic.doctor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    private Long id;
    @Column("doctor_name")
    private String name;
    private String speciality;
}