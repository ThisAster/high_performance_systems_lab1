/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.service;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.AnalysisDto;
import com.example.clinic.entity.Analysis;
import com.example.clinic.entity.Patient;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.mapper.AnalysisMapper;
import com.example.clinic.repository.AnalysisRepository;
import com.example.clinic.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final PatientRepository patientRepository;
    private final AnalysisMapper analysisMapper;

    @Transactional
    public Analysis createAnalysis(AnalysisDto analysisDto, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + patientId + " not found"));

        Analysis analysis = analysisMapper.analysisDtoToEntity(analysisDto);

        analysis.setPatient(patient);

        return analysisRepository.save(analysis);
    }   

    @Transactional
    public Analysis updateAnalysis(Long id, AnalysisDto analysisDto) {
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Analysis with id " + id + " not found"));

        analysis.setType(analysisDto.type());
        analysis.setSampleDate(analysisDto.sampleDate());
        analysis.setResult(analysisDto.result());
        analysis.setStatus(analysisDto.status());
    
        return analysisRepository.save(analysis);
    }

    @Transactional
    public void deleteAnalysis(Long id) {
        if (!analysisRepository.existsById(id)) {
            throw new EntityNotFoundException("Analysis with id " + id + " not found");
        }
        analysisRepository.deleteById(id);
    }

    public Analysis getAnalysisById(Long id) {
        return analysisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analysis with id " + id + " not found"));
    } 
}