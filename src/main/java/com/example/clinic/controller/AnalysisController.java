package com.example.clinic.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.example.clinic.util.HeaderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clinic.dto.AnalysisDto;
import com.example.clinic.entity.Analysis;
import com.example.clinic.mapper.AnalysisMapper;
import com.example.clinic.service.AnalysisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/analyses")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;
    private final AnalysisMapper analysisMapper;

    @PostMapping
    public ResponseEntity<AnalysisDto> createAnalysis(@RequestBody AnalysisDto analysisDto,
                                                       @RequestParam Long patientId) {
        Analysis analysis = analysisService.createAnalysis(analysisDto, patientId);
        
        AnalysisDto createdAnalysisDto = analysisMapper.entityToAnalysisDto(analysis);
        return ResponseEntity.created(URI.create("/api/analyses/" + createdAnalysisDto.id())).body(createdAnalysisDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalysisDto> updateAnalysis(@PathVariable Long id, @RequestBody AnalysisDto analysisDto) {
        Analysis updatedAnalysis = analysisService.updateAnalysis(id, analysisDto);
        
        AnalysisDto updatedAnalysisDto = analysisMapper.entityToAnalysisDto(updatedAnalysis);
        return ResponseEntity.ok(updatedAnalysisDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnalysis(@PathVariable Long id) {
        analysisService.deleteAnalysis(id);
        return ResponseEntity.ok("Analysis with id " + id + " successfully deleted.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalysisDto> getAnalysisById(@PathVariable Long id) {
        Analysis analysis = analysisService.getAnalysisById(id);
        AnalysisDto analysisDto = analysisMapper.entityToAnalysisDto(analysis);
        return ResponseEntity.ok(analysisDto);
    }

    @GetMapping
    public ResponseEntity<List<AnalysisDto>> getAllAnalyses(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Analysis> analysisPage = analysisService.getAnalyses(pageable);
        List<AnalysisDto> analysisDtos = analysisPage.getContent().stream()
                .map(analysisMapper::entityToAnalysisDto)
                .collect(Collectors.toList());

        HttpHeaders headers = HeaderUtils.createPaginationHeaders(analysisPage);

        return ResponseEntity.ok().headers(headers).body(analysisDtos);
    }
}
