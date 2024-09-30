package com.example.clinic.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "analysis")
public class Analysis implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="analysis_sequence")
    @SequenceGenerator(name="analysis_sequence", sequenceName="analysis_sequence", allocationSize=100)
    private Long id;
    
}