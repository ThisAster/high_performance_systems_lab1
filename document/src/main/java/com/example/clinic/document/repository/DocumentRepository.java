/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.document.repository;

import com.example.clinic.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thisaster
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByPatientId(Long patientId);

    List<Document> findByStatus(String status);

    List<Document> findByType(String type);
}
