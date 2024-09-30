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
@Table(name = "recipe")
public class Recipe implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="recipe_sequence")
    @SequenceGenerator(name="recipe_sequence", sequenceName="recipe_sequence", allocationSize=100)
    private Long id;
    
}