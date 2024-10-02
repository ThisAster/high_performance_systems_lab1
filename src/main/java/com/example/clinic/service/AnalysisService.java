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
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clinic.dto.AnalysisDto;
import com.example.clinic.dto.DoctorDto;
import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Analysis;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.repository.AnalysisRepository;

import jakarta.transaction.Transactional;

@Service
public class AnalysisService {

    private AnalysisRepository analysisRepository;
    private ModelMapper analysisMapper;

    @Autowired
    public void setAnalysisRepository(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper analysisMapper) {
        this.analysisMapper = analysisMapper;
    }

    @Transactional
    public AnalysisDto createAnalysis(AnalysisDto analysisDto, PatientDto patientDto, DoctorDto doctorDto) {
        Analysis analysis = analysisMapper.map(analysisDto, Analysis.class);
        Patient patient = analysisMapper.map(patientDto, Patient.class);
        Doctor doctor = analysisMapper.map(doctorDto, Doctor.class);
        analysis.setPatient(patient);
        analysis.setDoctor(doctor);
        Analysis savedAnalysis = analysisRepository.save(analysis);
        return analysisMapper.map(savedAnalysis, AnalysisDto.class);
    }

    public List<AnalysisDto> getAllAnalyses() {
        return ((List<Analysis>) analysisRepository.findAll()).stream()
                .map(analysis -> analysisMapper.map(analysis, AnalysisDto.class))  // Explicit mapping
                .collect(Collectors.toList());
    }

    public AnalysisDto getAnalysisById(Long id) {
        return analysisRepository.findById(id)
                .map(analysis -> analysisMapper.map(analysis, AnalysisDto.class))  // Explicit mapping
                .orElseThrow(() -> new IllegalArgumentException("Analysis with id " + id + " not found"));
    }

    @Transactional
    public AnalysisDto updateAnalysis(Long id, AnalysisDto analysisDto) {
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Analysis with id " + id + " not found"));
        
        analysis.setType(analysisDto.type());
        analysis.setSampleDate(analysisDto.sampleDate());
        analysis.setResult(analysisDto.result());
        analysis.setStatus(analysisDto.status());
        
        Analysis updatedAnalysis = analysisRepository.save(analysis);
        return analysisMapper.map(updatedAnalysis, AnalysisDto.class);
    }

    @Transactional
    public void deleteAnalysis(Long id) {
        if (!analysisRepository.existsById(id)) {
            throw new IllegalArgumentException("Analysis with id " + id + " not found");
        }
        analysisRepository.deleteById(id);
    }

    public List<AnalysisDto> findByPatientId(Long patientId) {
        return analysisRepository.findByPatientId(patientId).stream()
                .map(analysis -> analysisMapper.map(analysis, AnalysisDto.class))
                .collect(Collectors.toList());
    }

    public List<AnalysisDto> findByDoctorId(Long doctorId) {
        return analysisRepository.findByDoctorId(doctorId).stream()
                .map(analysis -> analysisMapper.map(analysis, AnalysisDto.class))
                .collect(Collectors.toList());
    }

    public List<AnalysisDto> findByType(String type) {
        return analysisRepository.findByType(type).stream()
                .map(analysis -> analysisMapper.map(analysis, AnalysisDto.class)) 
                .collect(Collectors.toList());
    }

    public List<AnalysisDto> findByStatus(String status) {
        return analysisRepository.findByStatus(status).stream()
                .map(analysis -> analysisMapper.map(analysis, AnalysisDto.class))  
                .collect(Collectors.toList());
    }
}