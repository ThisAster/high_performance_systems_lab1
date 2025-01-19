package com.example.clinic.analysis.entity;

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
@Table(name = "analyses")
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "analysis_type", nullable = false)
    private String type;

    @Column(name = "sample_date", nullable = false)
    private LocalDate sampleDate;

    @Column(name = "result", length = 200)
    private String result;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "patient_id")
    private Long patient;
}
