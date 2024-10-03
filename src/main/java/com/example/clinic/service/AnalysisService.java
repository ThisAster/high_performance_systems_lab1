/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.service;

/**
 *
 * @author thisaster
 */
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.AnalysisDto;
import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Analysis;
import com.example.clinic.entity.Patient;
import com.example.clinic.mapper.AnalysisMapper;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.repository.AnalysisRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final PatientService patientService;
    private final AnalysisMapper analysisMapper;
    private final PatientMapper patientMapper;

    @Transactional
    public Optional<AnalysisDto> createAnalysis(AnalysisDto analysisDto, Long patientId) {
        Analysis analysis = analysisMapper.analysisDtoToEntity(analysisDto);
        PatientDto patientDto = patientService.getPatientById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found"));


        Patient patient = patientMapper.patientDtoToEntity(patientDto);

        analysis.setPatient(patient);

        Analysis savedAnalysis = analysisRepository.save(analysis);

        return Optional.of(analysisMapper.entityToAnalysisDto(savedAnalysis));
    }   

    @Transactional
    public Optional<AnalysisDto> updateAnalysis(Long id, AnalysisDto analysisDto) {
        return analysisRepository.findById(id)
            .map(analysis -> {
                analysisMapper.updateEntityFromDto(analysis, analysisDto);
                Analysis updatedAnalysis = analysisRepository.save(analysis);
                return analysisMapper.entityToAnalysisDto(updatedAnalysis);
            });
    }

    @Transactional
    public void deleteAnalysis(Long id) {
        analysisRepository.deleteById(id);
    }

    public List<AnalysisDto> getAllAnalyses() {
        return ((List<Analysis>) analysisRepository.findAll()).stream()
                .map(analysisMapper::entityToAnalysisDto)
                .collect(Collectors.toList());
    }

    public Optional<AnalysisDto> getAnalysisById(Long id) {
        return analysisRepository.findById(id)
                .map(analysisMapper::entityToAnalysisDto);
    }

    public List<AnalysisDto> getByPatientId(Long patientId) {
        List<Analysis> analyses = analysisRepository.findByPatientId(patientId);
        return analyses.stream()
                .map(analysisMapper::entityToAnalysisDto)
                .collect(Collectors.toList());
    }

    public List<AnalysisDto> getByType(String type) {
        return analysisRepository.findByType(type).stream()
                .map(analysisMapper::entityToAnalysisDto)
                .collect(Collectors.toList());
    }

    public List<AnalysisDto> getByStatus(String status) {
        return analysisRepository.findByStatus(status).stream()
                .map(analysisMapper::entityToAnalysisDto)
                .collect(Collectors.toList());
    }
}