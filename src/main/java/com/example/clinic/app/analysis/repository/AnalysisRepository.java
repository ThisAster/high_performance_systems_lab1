/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.app.analysis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.app.analysis.entity.Analysis;

/**
 *
 * @author thisaster
 */
@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    
    List<Analysis> findByPatientId(Long patientId);
    
    List<Analysis> findByType(String type);
    
    List<Analysis> findByStatus(String status);
}