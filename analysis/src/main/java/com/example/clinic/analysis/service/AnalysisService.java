/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.analysis.service;

import com.example.clinic.analysis.dto.AnalysisCreationDto;
import com.example.clinic.analysis.dto.AnalysisDto;
import com.example.clinic.analysis.entity.Analysis;
import com.example.clinic.analysis.exception.EntityNotFoundException;
import com.example.clinic.analysis.mapper.AnalysisMapper;
import com.example.clinic.analysis.repository.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final AnalysisMapper analysisMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<AnalysisDto> createAnalysis(AnalysisCreationDto analysisDto, Long patientId) {
        Analysis analysis = analysisMapper.analysisDtoToEntity(analysisDto);

        analysis.setPatient(patientId);

        return Mono.just(analysisMapper.entityToAnalysisDto(analysisRepository.save(analysis)));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<AnalysisDto> updateAnalysis(Long id, AnalysisCreationDto analysisDto) {
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analysis with id " + id + " not found"));

        analysis.setType(analysisDto.type());
        analysis.setSampleDate(analysisDto.sampleDate());
        analysis.setResult(analysisDto.result());
        analysis.setStatus(analysisDto.status());

        return Mono.just(analysisMapper.entityToAnalysisDto(analysisRepository.save(analysis)));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteAnalysis(Long id) {
        if (!analysisRepository.existsById(id)) {
            throw new EntityNotFoundException("Analysis with id " + id + " not found");
        }
        analysisRepository.deleteById(id);
    }

    public Mono<AnalysisDto> getAnalysisById(Long id) {
        return Mono.just(analysisRepository.findById(id)
                .map(analysisMapper::entityToAnalysisDto)
                .orElseThrow(() -> new EntityNotFoundException("Analysis with id " + id + " not found")));
    }

    public Mono<Page<AnalysisDto>> getAnalyses(Pageable page) {
        return Mono.just(analysisRepository.findAll(page).map(analysisMapper::entityToAnalysisDto));
    }
}