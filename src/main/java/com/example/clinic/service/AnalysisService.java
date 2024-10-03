/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.AnalysisDto;
import com.example.clinic.entity.Analysis;
import com.example.clinic.entity.Patient;
import com.example.clinic.mapper.AnalysisMapper;
import com.example.clinic.repository.AnalysisRepository;
import com.example.clinic.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final PatientRepository patientRepository;
    private final AnalysisMapper analysisMapper;

    @Transactional
    public Optional<Analysis> createAnalysis(AnalysisDto analysisDto, Long patientId) {
        Analysis analysis = analysisMapper.analysisDtoToEntity(analysisDto);
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found"));

        analysis.setPatient(patient);

        Analysis savedAnalysis = analysisRepository.save(analysis);

        return Optional.of(savedAnalysis);
    }   

    @Transactional
    public Optional<Analysis> updateAnalysis(Long id, AnalysisDto analysisDto) {
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Analysis with id " + id + " not found"));

        analysis.setType(analysisDto.type());
        analysis.setSampleDate(analysisDto.sampleDate());
        analysis.setResult(analysisDto.result());
        analysis.setStatus(analysisDto.status());

        Analysis updatedAnalysis = analysisRepository.save(analysis);
    
        return Optional.of(updatedAnalysis);
    }

    @Transactional
    public void deleteAnalysis(Long id) {
        analysisRepository.deleteById(id);
    }

    public List<Analysis> getAllAnalyses() {
        return ((Collection<Analysis>) analysisRepository.findAll()).stream()
                .collect(Collectors.toList());
    }

    public Optional<Analysis> getAnalysisById(Long id) {
        return analysisRepository.findById(id);
    }

    public List<Analysis> getByPatientId(Long patientId) {
        List<Analysis> analyses = analysisRepository.findByPatientId(patientId);
        return analyses.stream()
                .collect(Collectors.toList());
    }

    public List<Analysis> getByType(String type) {
        return analysisRepository.findByType(type).stream()
                .collect(Collectors.toList());
    }

    public List<Analysis> getByStatus(String status) {
        return analysisRepository.findByStatus(status).stream()
                .collect(Collectors.toList());
    }
}