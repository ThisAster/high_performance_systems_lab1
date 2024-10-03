/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.entity.Document;

/**
 *
 * @author thisaster
 */
@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
    
    List<Document> findByPatientId(Long patientId);
    
    List<Document> findByStatus(String status);
    
    List<Document> findByType(String type);
}
