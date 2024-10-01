/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Analysis;

/**
 *
 * @author thisaster
 */
@Repository
public interface AnalysisRepository extends CrudRepository<Analysis, Long> {
    
    List<Analysis> findByPatientId(Long patientId);
    
    List<Analysis> findByDoctorId(Long doctorId);
    
    List<Analysis> findByType(String type);
    
    List<Analysis> findByStatus(String status);
}